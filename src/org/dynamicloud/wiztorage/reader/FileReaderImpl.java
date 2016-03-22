package org.dynamicloud.wiztorage.reader;

import org.apache.commons.codec.binary.Base64;
import org.dynamicloud.wiztorage.configurator.StorageConfigurator;
import org.dynamicloud.wiztorage.exception.FileReaderException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
 * <code>FileReaderImpl</code>
 * <p/>
 * This class represents a reader of files
 *
 * @author Eleazar Gomez
 */
public class FileReaderImpl implements FileReader {
    /**
     * This method will read a file and every read chunk will be passed through File Reader Callback newChunk method.
     *
     * @param file               file to read
     * @param fileReaderCallback callback for every built chunk.
     * @throws org.dynamicloud.wiztorage.exception.FileReaderException when any error occurs.
     */
    @Override
    public void readFile(File file, FileReaderCallback fileReaderCallback) throws FileReaderException {
        if (file == null) {
            throw new FileReaderException("File is null, this is an illegal argument.");
        }

        if (fileReaderCallback == null) {
            throw new FileReaderException("Callback is null, this is an illegal argument.");
        }

        StorageConfigurator configurator = StorageConfigurator.StorageConfiguratorInitializer.getConfigurator();

        int maxSizeFile = Integer.parseInt(configurator.getProperty("maxSizeFile"));

        if (file.length() == 0) {
            throw new FileReaderException("This file is empty");
        }

        if (file.length() > (maxSizeFile * 1024 * 1024)/*to bytes*/) {
            throw new FileReaderException("File size is greater than Max file size '" + maxSizeFile + "'.");
        }

        long bytesInBuffer = Integer.parseInt(configurator.getProperty("maxSizePerChunk")) * 1024 * 1024/*to bytes*/;
        bytesInBuffer = file.length() < bytesInBuffer ? file.length() : bytesInBuffer;

        BufferedInputStream reader = null;
        try {
            reader = new BufferedInputStream(new FileInputStream(file));
            int index = 0;

            while (reader.available() != 0) {
                byte[] buffer = new byte[(int) bytesInBuffer];

                int read = reader.read(buffer);

                if (read <= 0) {
                    break;
                }

                /**
                 * Convert this content as based64
                 */
                if (read < bytesInBuffer) {
                    byte [] newBuffer = new byte[read];
                    System.arraycopy(buffer, 0, newBuffer, 0, read);

                    fileReaderCallback.newChunk(new String(Base64.encodeBase64(newBuffer), "UTF-8"), index++);
                } else {
                    fileReaderCallback.newChunk(new String(Base64.encodeBase64(buffer), "UTF-8"), index++);
                }
            }
        } catch (IOException e) {
            throw new FileReaderException(e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ignore) {
            }
        }
    }
}