package org.dynamicloud.wiztorage.writer;

import org.dynamicloud.wiztorage.exception.FileWriterException;
import org.dynamicloud.wiztorage.processor.DownloadCallback;

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
 * <code>FileWriter</code>
 * <p/>
 * <p/>
 * This interface provides the methods to process the content from Dynamicloud.
 * The content is a based64 and will be decoded to bytes and then saved within the target file.
 *
 * @author Eleazar Gomez
 */
public interface FileWriter {

    /**
     * This method writes an encode64Chunk into the destination.
     *
     * @param encode64Chunk to write into the file
     */
    public void writeFile(String encode64Chunk) throws FileWriterException;

    /**
     * This method finishes the writing process.
     */
    public void finish();

    public class FileWriterBuilder {
        /**
         * Returns a new instance of FileWriter
         *
         * @param file destination
         */
        public static FileWriter getInstance(File file) throws FileWriterException {
            return new FileWriterImpl(file);
        }
    }
}