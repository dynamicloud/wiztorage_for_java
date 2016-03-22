package org.dynamicloud.wiztorage.validator;

import org.dynamicloud.wiztorage.DynamicloudCredentials;
import org.dynamicloud.wiztorage.processor.StorageProcessorImpl;

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
 * <code>StorageValidator</code>
 * <p/>
 * This interface declares the methods to implement a StorageValidator
 *
 * @author Eleazar Gomez
 */
public interface StorageValidator {
    /**
     * This method validates if a filename already exists in Dynamicloud
     *
     * @param fileName to validate
     * @return true if already exists.
     */
    public boolean existsFileName(String fileName);

    public class StorageValidatorBuilder {
        private static StorageValidator instance;

        /**
         * Returns a singleton object of FileReader
         * @param credentials DynamicloudCredentials
         */
        public static StorageValidator getInstance(DynamicloudCredentials credentials) {
            if (instance == null) {
                instance = new StorageValidatorImpl(credentials);
            }

            return instance;
        }
    }
}