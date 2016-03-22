package org.dynamicloud.wiztorage.writer;

import junit.framework.TestCase;
import org.dynamicloud.wiztorage.configurator.StorageConfigurator;
import org.dynamicloud.wiztorage.exception.FileWriterException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
 * <code>TestFileWriterImpl</code>
 * <p/>
 * <p/>
 * These are the set of test cases to guarantee the health of FileWriter implementations.
 *
 * @author Eleazar Gomez
 */
public class TestFileWriterImpl extends TestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        StorageConfigurator.StorageConfiguratorInitializer.setup(new File("test/settings.properties"));
    }

    public void testMissingBase64Validation() {
        File destination = new File("test/destination_" + System.currentTimeMillis() + ".txt");
        FileWriter writer = null;
        try {
            writer = FileWriter.FileWriterBuilder.getInstance(destination);

            writer.writeFile("/**//-/*/*/*/");

            fail("FileWriter must validate base64 encode.");
        } catch (FileWriterException ignore) {

        } finally {
            if (writer != null) {
                writer.finish();
            }
        }
    }

    public void testWriteBase64() {
        File destination = new File("test/destination_" + System.currentTimeMillis() + ".txt");
        FileWriter writer = null;
        try {
            writer = FileWriter.FileWriterBuilder.getInstance(destination);

            Properties props = new Properties();
            props.load(new FileInputStream("test/settings.properties"));

            writer.writeFile(props.getProperty("set"));
        } catch (FileWriterException e) {
            fail(e.getMessage());
        } catch (FileNotFoundException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        } finally {
            if (writer != null) {
                writer.finish();
            }
        }
    }
}