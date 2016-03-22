package org.dynamicloud.wiztorage.configurator;

import org.dynamicloud.wiztorage.exception.RuntimeWiztorageException;
import org.dynamicloud.wiztorage.exception.WiztorageException;

import java.io.File;
import java.util.Map;

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
 * <code>StorageConfigurator</code>
 * <p/>
 * <p/>
 * <p/>
 * This interface declares the necessary methods to implement a StorageConfigurator.
 *
 * @author Eleazar Gomez
 */
public interface StorageConfigurator {

    /**
     * Return the value according to key
     *
     * @param key target
     * @return a string value
     */
    public String getProperty(String key);

    /**
     * This method setups the storage instance with the settings from the passed file.
     *
     * @throws org.dynamicloud.wiztorage.exception.WiztorageException if an error occurs.
     */
    public void setupStorage() throws WiztorageException;

    /**
     * This is a class to initialize the settings for this configurator
     * This is a singleton instance
     */
    public class StorageConfiguratorInitializer {
        private static StorageConfigurator instance;

        /**
         * Returns a singleton object of FileReader
         *
         * @param settings file with the settings for this instance.  This file must be a compatible properties file.
         */
        public static void setup(File settings) throws WiztorageException {
            if (instance == null) {
                instance = new StorageConfiguratorImpl(settings);
                instance.setupStorage();
            }
        }

        /**
         * Return the unique configurator instance
         *
         * @return configurator instance
         */
        public static StorageConfigurator getConfigurator() {
            if (instance == null) {
                instance = new StorageConfiguratorImpl();

                try {
                    instance.setupStorage();
                } catch (WiztorageException e) {
                    throw new RuntimeWiztorageException(e.getMessage());
                }
            }

            return instance;
        }
    }
}