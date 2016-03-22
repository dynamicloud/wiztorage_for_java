package org.dynamicloud.wiztorage.configurator;

import junit.framework.TestCase;
import org.dynamicloud.wiztorage.exception.WiztorageException;

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
 * <code>TestStorageConfiguratorImpl</code>
 * <p/>
 * <p/>
 * This class implements the unit tests for StorageConfiguratorImpl
 *
 * @author Eleazar Gomez
 */
public class TestStorageConfiguratorImpl extends TestCase {

    public void test_A_MissingFileValidation() {
        try {
            StorageConfigurator.StorageConfiguratorInitializer.setup(null);
            fail("setup method should validate file object");
        } catch (Exception ignore) {

        }
    }

    public void test_B_MissingInstanceValidation() {
        try {
            StorageConfigurator.StorageConfiguratorInitializer.getConfigurator();
            fail("getConfigurator method should validate singleton instance");
        } catch (Exception ignore) {
        }
    }

    public void test_C_ConfiguratorInstance() {
        StorageConfigurator configurator = StorageConfigurator.StorageConfiguratorInitializer.getConfigurator();

        assertNotNull(configurator);
    }

    public void test_D_MandatoryProperties() {
        StorageConfigurator configurator = StorageConfigurator.StorageConfiguratorInitializer.getConfigurator();

        String maxSizeFile = configurator.getProperty("maxSizeFile");

        assertNotNull(maxSizeFile);
    }
}