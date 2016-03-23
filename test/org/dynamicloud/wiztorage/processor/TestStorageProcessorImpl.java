package org.dynamicloud.wiztorage.processor;

import junit.framework.TestCase;
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
 * <code>TestStorageProcessorImpl</code>
 * <p/>
 * <p/>
 * This class implements the unit tests for StorageProcessorImpl
 *
 * @author Eleazar Gomez
 */
public class TestStorageProcessorImpl extends TestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testStorageProcessorExistsDeleteFile() {
        StorageProcessor processor = StorageProcessor.StorageProcessorBuilder.getInstance("test/wiztorage.properties");
        try {
            processor.uploadFile(new File("test/test_file_read_big.txt"), "", new UploadCallback() {
                @Override
                public void finishWork(UploadInfo info) {
                    System.out.println("info.getCheckSum() = " + info.getCheckSum());
                }
            });

            assertTrue(processor.existsFile("test_file_read_big.txt"));

            processor.deleteFile("test_file_read_big.txt");
            assertFalse(processor.existsFile("test_file_read_big.txt"));
        } catch (StorageProcessorException e) {
            fail(e.getMessage());
        }
    }

    public void testStorageProcessorUploadBigFile() {
        StorageProcessor processor = StorageProcessor.StorageProcessorBuilder.getInstance("test/wiztorage.properties");
        try {
            processor.uploadFile(new File("test/test_file_read_big.txt"), "", new UploadCallback() {
                @Override
                public void finishWork(UploadInfo info) {
                    System.out.println("info.getCheckSum() = " + info.getCheckSum());
                }
            });
        } catch (StorageProcessorException e) {
            fail(e.getMessage());
        }
    }

    public void testStorageProcessorUploadFile() {
        StorageProcessor processor = StorageProcessor.StorageProcessorBuilder.getInstance("test/wiztorage.properties");
        try {
            processor.uploadFile(new File("test/test_file_read.txt"), "", new UploadCallback() {
                @Override
                public void finishWork(UploadInfo info) {
                    System.out.println("info.getCheckSum() = " + info.getCheckSum());
                }
            });
        } catch (StorageProcessorException e) {
            fail(e.getMessage());
        }
    }

    public void testStorageProcessorDownloadFile() {
        StorageProcessor processor = StorageProcessor.StorageProcessorBuilder.getInstance("test/wiztorage.properties");
        try {
            processor.downloadFile("test_file_read.txt", new File("c:/wiztorage/from_dymamicloud_" + System.currentTimeMillis() + ".txt"), new DownloadCallback() {
                @Override
                public void finishWork(DownloadInfo info) {
                    System.out.println("info = " + info);
                }
            });
        } catch (StorageProcessorException e) {
            fail(e.getMessage());
        }
    }

    public void testStorageProcessorDownloadBigFile() {
        StorageProcessor processor = StorageProcessor.StorageProcessorBuilder.getInstance("test/wiztorage.properties");
        try {
            processor.downloadFile("test_file_read_big.txt", new File("c:/wiztorage/from_dymamicloud_big_" + System.currentTimeMillis() + ".txt"), new DownloadCallback() {
                @Override
                public void finishWork(DownloadInfo info) {
                    System.out.println("info = " + info);
                }
            });
        } catch (StorageProcessorException e) {
            fail(e.getMessage());
        }
    }

    public void testStorageProcessorCleanUp() {
        StorageProcessor processor = StorageProcessor.StorageProcessorBuilder.getInstance("test/wiztorage.properties");
        try {
            processor.cleanUp();
        } catch (StorageProcessorException e) {
            fail(e.getMessage());
        }
    }
}