package org.dynamicloud.wiztorage.processor;

import org.dynamicloud.wiztorage.exception.StorageProcessorException;

import java.io.File;

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
 * <code>StorageProcessor</code>
 * <p/>
 * <p/>
 * This interface declares the necessary methods to implement a StorageProcessorImpl.
 *
 * @author Eleazar Gomez
 */
public interface StorageProcessor {
    /**
     * This method uploads an array of files
     *
     * @param files    to be uploaded
     * @param callback will be called for each uploaded file
     * @throws StorageProcessorException if an error occurs
     */
    public void uploadFiles(File[] files, String description, UploadCallback callback) throws StorageProcessorException;

    /**
     * This method uploads a file
     *
     * @param file     to be uploaded
     * @param callback will be called for the uploaded file
     * @throws StorageProcessorException if an error occurs
     */
    public void uploadFile(File file, String description, UploadCallback callback) throws StorageProcessorException;

    /**
     * This method deletes a file
     *
     * @param fileName to be deleted
     * @throws org.dynamicloud.wiztorage.exception.StorageProcessorException if an error occurs
     */
    public void deleteFile(String fileName) throws StorageProcessorException;

    /**
     * This method deletes an array of file
     *
     * @param fileNames to be deleted
     * @throws StorageProcessorException if an error occurs
     */
    public void deleteFiles(String[] fileNames) throws StorageProcessorException;

    /**
     * This method validates if a file already exists
     * Returns true if a chunk has the name fileName
     *
     * @param fileName to be verified
     * @param checked  indicates if this verification is for file checked or not.
     * @throws StorageProcessorException if an error occurs
     */
    public boolean existsFile(String fileName, boolean checked) throws StorageProcessorException;

    /**
     * This method validates if a file already exists
     * Returns true if a chunk has the name fileName, regardless if this chunk is not already checked
     * If you want to make sure that a file exists and is checked, you need to call existsFile(fileName, true)
     *
     * @param fileName to be verified
     * @throws StorageProcessorException if an error occurs
     */
    public boolean existsFile(String fileName) throws StorageProcessorException;

    /**
     * This method downloads a file from Dynamicloud using a fileName
     *
     * @param fileName    file name target to download file
     * @param destination to save the file from Dynamicloud
     * @param callback    for a downloaded file.
     */
    public void downloadFile(String fileName, File destination, DownloadCallback callback) throws StorageProcessorException;

    /**
     * This method cleans those files with an inconsistent state.
     * If a file has checked = 0, that file will be deleted from Dynamicloud.
     */
    public void cleanUp() throws StorageProcessorException;

    public class StorageProcessorBuilder {
        private static StorageProcessor instance;

        /**
         * Returns a singleton object of FileReader
         *
         * @param propertiesFilePath this property file provides the Dynamicloud credentials:
         *                           csk, aci and modelIdentifier
         *                           These information is required to instantiate the Storage processor,
         *                           if one of this information is missing a RuntimeWiztorageException will be thrown.
         */
        public static StorageProcessor getInstance(String propertiesFilePath) {
            if (instance == null) {
                instance = new StorageProcessorImpl(propertiesFilePath);
            }

            return instance;
        }
    }
}