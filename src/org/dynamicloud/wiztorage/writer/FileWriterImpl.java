package org.dynamicloud.wiztorage.writer;

import org.apache.commons.codec.binary.Base64;
import org.dynamicloud.wiztorage.exception.FileWriterException;

import java.io.*;

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
 * <code>FileWriterImpl</code>
 * <p/>
 * <p/>
 * This class implements FileWriter to process content from Dynamicloud
 *
 * @author Eleazar Gomez
 */
public class FileWriterImpl implements FileWriter {
    private FileOutputStream outputStream;
    private File file;

    public FileWriterImpl(File file) throws FileWriterException {
        try {
            this.outputStream = new FileOutputStream(file);
            this.file = file;
        } catch (FileNotFoundException e) {
            throw new FileWriterException(e.getMessage());
        }
    }

    /**
     * This method writes an encode64Chunk into the destination.
     *
     * @param encode64Chunk to write into the file
     */
    @Override
    public void writeFile(String encode64Chunk) throws FileWriterException {
        try {
            if (Base64.isBase64(encode64Chunk)) {
                byte[] bytes = Base64.decodeBase64(encode64Chunk.getBytes("UTF-8"));
                outputStream.write(bytes, 0, bytes.length);
            } else {
                throw new FileWriterException("Invalid base64 encode.");
            }
        } catch (UnsupportedEncodingException e) {
            throw new FileWriterException(e.getMessage());
        } catch (IOException e) {
            throw new FileWriterException(e.getMessage());
        }
    }

    /**
     * This method rollbacks the writing process.
     */
    @Override
    public void rollback() {
        this.finish();
        this.file.delete();
    }

    /**
     * This method finishes the writing process.
     */
    @Override
    public void finish() {
        try {
            outputStream.close();
        } catch (Exception ignore) {
        }
    }
}