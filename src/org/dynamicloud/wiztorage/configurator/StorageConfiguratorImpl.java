package org.dynamicloud.wiztorage.configurator;

import org.dynamicloud.wiztorage.exception.RuntimeWiztorageException;
import org.dynamicloud.wiztorage.exception.WiztorageException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
 * <code>StorageConfiguratorImpl</code>
 * <p/>
 * This class is the configurator implementation to load the settings from a file
 *
 * @author Eleazar Gomez
 */
public class StorageConfiguratorImpl extends Properties implements StorageConfigurator {
    private File settings;

    /**
     * Creates an default storage configurator
     */
    public StorageConfiguratorImpl() {
    }

    /**
     * This constructor builds an instance of StorageConfigurator
     * @param settings to load
     */
    public StorageConfiguratorImpl(File settings) {
        if (settings == null) {
            throw new RuntimeWiztorageException("Settings file is null, this is an illegal state.");
        }

        this.settings = settings;
    }

    /**
     * This method setups the storage instance with the settings from the passed file.
     *
     * @throws org.dynamicloud.wiztorage.exception.WiztorageException if an error occurs.
     */
    @Override
    public void setupStorage() throws WiztorageException {
        try {
            if (settings == null) {
                this.load(ClassLoader.getSystemClassLoader().getResourceAsStream("settings.properties"));
            } else {
                this.load(new FileInputStream(settings));
            }
        } catch (IOException e) {
            throw new WiztorageException(e.getMessage());
        }
    }
}