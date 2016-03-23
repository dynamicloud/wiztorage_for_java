package org.dynamicloud.wiztorage.processor;

import org.apache.commons.codec.binary.Base64;
import org.dynamicloud.api.DynamicProvider;
import org.dynamicloud.api.DynamicProviderImpl;
import org.dynamicloud.api.Query;
import org.dynamicloud.api.RecordResults;
import org.dynamicloud.api.criteria.Conditions;
import org.dynamicloud.api.model.RecordModel;
import org.dynamicloud.exception.DynamicloudProviderException;
import org.dynamicloud.wiztorage.DynamicloudCredentials;
import org.dynamicloud.wiztorage.exception.FileWriterException;
import org.dynamicloud.wiztorage.exception.RuntimeWiztorageException;
import org.dynamicloud.wiztorage.exception.StorageProcessorException;
import org.dynamicloud.wiztorage.reader.FileReader;
import org.dynamicloud.wiztorage.reader.FileReaderCallback;
import org.dynamicloud.wiztorage.util.WiztorageUtils;
import org.dynamicloud.wiztorage.validator.StorageValidator;
import org.dynamicloud.wiztorage.writer.FileWriter;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Copyright (c) 2016 Dynamicloud
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p/>
 * <code>StorageProcessorImpl</code>
 * <p/>
 * This class is the processor to upload files to Dynamicloud
 *
 * @author Eleazar Gomez
 */
public class StorageProcessorImpl implements StorageProcessor {
    private DynamicloudCredentials credentials;

    /**
     * Creates a StorageProcessor
     *
     * @param propertiesFilePath path to load Dynamicloud credentials
     */
    public StorageProcessorImpl(String propertiesFilePath) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(propertiesFilePath));

            this.credentials = new DynamicloudCredentials(props.getProperty("csk"), props.getProperty("aci"));

            if (this.credentials.getCsk() == null) {
                throw new RuntimeWiztorageException("csk property is required.");
            }

            this.credentials.setAci(props.getProperty("aci"));
            if (this.credentials.getAci() == null) {
                throw new RuntimeWiztorageException("aci property is required.");
            }

            if (props.getProperty("modelIdentifier") == null || props.getProperty("modelIdentifier").trim().equals("")) {
                throw new RuntimeWiztorageException("modelIdentifier property is required.");
            }

            this.credentials.setModelIdentifier(Long.parseLong(props.getProperty("modelIdentifier")));
        } catch (Exception e) {
            throw new RuntimeWiztorageException(e.getMessage());
        }
    }

    /**
     * This method uploads an array of files
     *
     * @param files       to be uploaded
     * @param callback    will be called for each uploaded file
     * @param description optional description for this set of files
     * @throws org.dynamicloud.wiztorage.exception.StorageProcessorException if an error occurs
     */
    @Override
    public void uploadFiles(File[] files, String description, UploadCallback callback) throws StorageProcessorException {
        for (File file : files) {
            uploadFile(file, description, callback);
        }
    }

    /**
     * This method uploads a file
     *
     * @param file        to be uploaded
     * @param description optional description
     * @param callback    will be called for the uploaded file
     * @throws org.dynamicloud.wiztorage.exception.StorageProcessorException if an error occurs
     */
    @Override
    public void uploadFile(final File file, final String description, UploadCallback callback) throws StorageProcessorException {
        try {
            StorageValidator validator = StorageValidator.StorageValidatorBuilder.getInstance(this.credentials);
            if (validator.existsFileName(file.getName())) {
                throw new StorageProcessorException("Filename already exists in Dynamicloud servers.  Probably this filename has an invalid state, try to execute cleanUp.");
            }
        } catch (RuntimeWiztorageException e) {
            throw new StorageProcessorException(e.getMessage());
        }

        final DynamicProvider<UploadBean> provider = new DynamicProviderImpl<UploadBean>(StorageProcessorImpl.this.credentials);
        final StringBuilder checkSums = new StringBuilder("");
        try {
            FileReader.FileReaderBuilder.getInstance().readFile(file, new FileReaderCallback() {
                @Override
                public void newChunk(String encode64Chunk, int index) {
                    /**
                     * This API provides a SPI to implement your owm FileReader
                     * This validation guarantees that your implementations of FileReader always send a
                     * compatible Base64 String
                     */
                    if (!Base64.isBase64(encode64Chunk)) {
                        throw new RuntimeWiztorageException("Invalid base64 encode.");
                    }

                    String cs = WiztorageUtils.generateCheckSum(encode64Chunk);

                    UploadBean bean = new UploadBean();
                    bean.setCheckSum(cs);
                    bean.setChecked(0);
                    bean.setSequence(index);
                    bean.setSize(file.length());
                    bean.setName(file.getName());
                    bean.setDescription(description == null ? "" : description);
                    bean.setType(WiztorageUtils.getType(file));
                    bean.setChunk(encode64Chunk);

                    try {
                        provider.saveRecord(new RecordModel(StorageProcessorImpl.this.credentials.getModelIdentifier()), bean);
                    } catch (Exception e) {
                        throw new RuntimeWiztorageException(e.getMessage());
                    }

                    checkSums.append(cs);
                }
            });

            /**
             * "Commit"
             */
            Query<UploadBean> query = provider.createQuery(new RecordModel(this.credentials.getModelIdentifier()));
            query.add(Conditions.equals("name", file.getName()));

            UploadBean bean = new UploadBean();
            bean.setChecked(1);

            provider.setBoundInstance(bean);
            provider.update(query);
            /**
             * End "Commit"
             */

            UploadInfo info = new UploadInfo();
            info.setSize(file.length());
            info.setName(file.getName());
            info.setDescription(description == null ? "" : description);
            info.setType(WiztorageUtils.getType(file));
            info.setUploadedFile(file);
            info.setCheckSum(WiztorageUtils.generateCheckSum(checkSums.toString()));

            callback.finishWork(info);
        } catch (Exception e) {
            /**
             * "Rollback"
             */
            try {
                DynamicProvider rollbackProvider = new DynamicProviderImpl(StorageProcessorImpl.this.credentials);
                Query query = rollbackProvider.createQuery(new RecordModel(this.credentials.getModelIdentifier()));
                query.add(Conditions.equals("name", file.getName()));

                rollbackProvider.delete(query);
            } catch (Exception ex) {
                throw new StorageProcessorException(ex.getMessage());
            }

            throw new StorageProcessorException(e.getMessage());
        }
    }

    /**
     * This method deletes a file
     *
     * @param fileName to be deleted
     * @throws org.dynamicloud.wiztorage.exception.StorageProcessorException if an error occurs
     */
    @Override
    public void deleteFile(String fileName) throws StorageProcessorException {
        deleteFiles(new String[]{fileName});
    }

    /**
     * This method deletes an array of file
     *
     * @param fileNames to be deleted
     * @throws StorageProcessorException if an error occurs
     */
    @Override
    public void deleteFiles(String[] fileNames) throws StorageProcessorException {
        DynamicProvider<UploadBean> provider = new DynamicProviderImpl<UploadBean>(StorageProcessorImpl.this.credentials);

        Query<UploadBean> query = provider.createQuery(new RecordModel(this.credentials.getModelIdentifier()));
        query.add(Conditions.in("name", fileNames));

        try {
            provider.delete(query);
        } catch (Exception e) {
            throw new StorageProcessorException(e.getMessage());
        }
    }

    /**
     * This method validates if a file already exists
     * Returns true if a chunk has the name fileName
     *
     * @param fileName to be verified
     * @param checked  indicates if this verification is for file checked or not.
     * @throws StorageProcessorException if an error occurs
     */
    @Override
    public boolean existsFile(String fileName, boolean checked) throws StorageProcessorException {
        StorageValidator validator = StorageValidator.StorageValidatorBuilder.getInstance(this.credentials);
        return validator.existsFileName(fileName);
    }

    /**
     * This method validates if a file already exists
     * Returns true if a chunk has the name fileName, regardless if this chunk is not already checked
     * If you want to make sure that a file exists and is checked, you need to call existsFile(fileName, true)
     *
     * @param fileName to be verified
     * @throws StorageProcessorException if an error occurs
     */
    @Override
    public boolean existsFile(String fileName) throws StorageProcessorException {
        return existsFile(fileName, false);
    }

    /**
     * This method downloads a file from Dynamicloud using a fileName as target
     *
     * @param fileName    file name target to download file
     * @param destination to save the file from Dynamicloud
     * @param callback    for a downloaded file.
     * @throws org.dynamicloud.wiztorage.exception.StorageProcessorException if an error occurs
     */
    @Override
    public void downloadFile(String fileName, File destination, DownloadCallback callback) throws StorageProcessorException {
        if (fileName == null) {
            throw new StorageProcessorException("fileName is null.");
        }

        if (destination == null) {
            throw new StorageProcessorException("destination file is null.");
        }

        if (destination.exists()) {
            throw new StorageProcessorException("destination file already exits.");
        }

        executeWritingProcess(fileName, destination, callback);
    }

    /**
     * This method cleans those files with an inconsistent state.
     * If a file has checked = 0, that record will be deleted from Dynamicloud.
     */
    @Override
    public void cleanUp() throws StorageProcessorException {
        DynamicProvider<UploadBean> provider = new DynamicProviderImpl<UploadBean>(StorageProcessorImpl.this.credentials);

        Query<UploadBean> query = provider.createQuery(new RecordModel(this.credentials.getModelIdentifier()));
        query.add(Conditions.equals("checked", 0));

        try {
            provider.delete(query);
        } catch (Exception e) {
            throw new StorageProcessorException(e.getMessage());
        }
    }

    /**
     * Executes the writing process.
     *
     * @param fileName    target to fetch from Dynamicloud
     * @param destination file target
     * @param callback    where the process finishes, this callback will be called.
     * @throws StorageProcessorException if an error occurs.
     */
    private void executeWritingProcess(String fileName, File destination, DownloadCallback callback) throws StorageProcessorException {
        FileWriter fileWriter = null;
        try {
            fileWriter = FileWriter.FileWriterBuilder.getInstance(destination);

            DynamicProvider<UploadBean> provider = new DynamicProviderImpl<UploadBean>(StorageProcessorImpl.this.credentials);

            RecordModel recordModel = new RecordModel(this.credentials.getModelIdentifier());
            recordModel.setBoundClass(UploadBean.class);

            Query<UploadBean> query = provider.createQuery(recordModel);
            query.setProjection("id as rid").add(Conditions.equals("name", fileName)).orderBy("sequence").asc();

            StringBuilder checkSums = new StringBuilder();
            StringBuilder description = new StringBuilder();

            try {
                RecordResults<UploadBean> list = query.list();

                if (list.getFastReturnedSize() == 0) {
                    return;
                }

                loopRecords(fileWriter, provider, query, checkSums, description, list);
            } catch (Exception e) {
                fileWriter.rollback();

                throw new StorageProcessorException(e.getMessage());
            } finally {
                fileWriter.finish();
            }

            DownloadInfo info = new DownloadInfo();
            info.setCheckSum(WiztorageUtils.generateCheckSum(checkSums.toString()));
            info.setFileName(fileName);
            info.setDescription(description.toString());
            info.setSize(new File(destination.getAbsolutePath()).length());

            callback.finishWork(info);
        } catch (FileWriterException e) {
            if (fileWriter != null) {
                fileWriter.rollback();
            }
            throw new StorageProcessorException(e.getMessage());
        }
    }

    /**
     * This method loops the records from Dynamicloud til next method returns 0 records.
     *
     * @param fileWriter to save the file into the destination
     * @param provider   Dynamicloud provider
     * @param query      query to execute next method
     * @param checkSums  checkSums so far
     * @param list       current list of records
     * @throws FileWriterException if an error occurs
     */
    private void loopRecords(FileWriter fileWriter, DynamicProvider<UploadBean> provider, Query<UploadBean> query,
                             StringBuilder checkSums, StringBuilder description, RecordResults<UploadBean> list) throws FileWriterException {
        for (UploadBean beanWithId : list.getRecords()) {
            UploadBean uploadBean;
            try {
                uploadBean = provider.loadRecord(beanWithId.getRecordId().longValue(),
                        new RecordModel(this.credentials.getModelIdentifier()), UploadBean.class);
            } catch (Exception e) {
                throw new FileWriterException(e.getMessage());
            }

            if (description.length() == 0) {
                description.append(uploadBean.getDescription());
            }

            fileWriter.writeFile(uploadBean.getChunk());

            checkSums.append(WiztorageUtils.generateCheckSum(uploadBean.getCheckSum()));
        }

        try {
            list = query.next();
        } catch (Exception e) {
            throw new FileWriterException(e.getMessage());
        }

        if (list.getFastReturnedSize() == 0) {
            return;
        }

        loopRecords(fileWriter, provider, query, checkSums, description, list);
    }
}