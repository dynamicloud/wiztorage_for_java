package org.dynamicloud.wiztorage.reader;

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
 * <code>FileReaderCallback</code>
 * <p/>
 * <p/>
 * This class represents a FileReader callback.  This callback interface is useful to implement a trigger
 * for every new built chunk.
 *
 * @author Eleazar Gomez
 */
public interface FileReaderCallback {
    /**
     * This method will be called when a new chunk is build.
     * This API provides a SPI to implement your owm FileReader
     * The result of this method will be validated to guarantee that your implementations of FileReader always send a
     * compatible Base64 String
     *
     * @param encode64Chunk new base64 encoded chunk
     * @param index         this is the sequence in context for this new chunk
     */
    public void newChunk(String encode64Chunk, int index);
}