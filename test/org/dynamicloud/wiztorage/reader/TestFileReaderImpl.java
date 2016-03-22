package org.dynamicloud.wiztorage.reader;

import junit.framework.TestCase;
import org.dynamicloud.wiztorage.configurator.StorageConfigurator;
import org.dynamicloud.wiztorage.exception.FileReaderException;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;
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
 * <code>TestFileReaderImpl</code>
 * <p/>
 * <p/>
 * These are the set of test cases to guarantee the health of FileReader implementations.
 *
 * @author Eleazar Gomez
 */
public class TestFileReaderImpl extends TestCase {
    private static int chunksSoFar;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        chunksSoFar = 0;

        StorageConfigurator.StorageConfiguratorInitializer.setup(new File("test/settings.properties"));
    }

    public void testMissingFileValidation() {
        FileReader reader = FileReader.FileReaderBuilder.getInstance();

        try {
            reader.readFile(null, null);

            fail("ReadFile method should validate file object");
        } catch (FileReaderException ignore) {

        }
    }

    public void testMissingCallbackValidation() {
        FileReader reader = FileReader.FileReaderBuilder.getInstance();

        try {
            reader.readFile(new File(""), null);
            fail("ReadFile method should validate callback object");
        } catch (FileReaderException ignore) {
        }
    }

    public void testFileReaderMaxFile() {
        FileReader reader = FileReader.FileReaderBuilder.getInstance();

        File file = new File("test/test_file.pdf");

        try {
            reader.readFile(file, new FileReaderCallback() {
                @Override
                public void newChunk(String encode64Chunk, int index) {

                }
            });

            fail("ReadFile method should validate max file's size");
        } catch (FileReaderException ignore) {

        }
    }

    public void testFileReaderChunkCounts() {
        FileReader reader = FileReader.FileReaderBuilder.getInstance();

        File file = new File("test/test_file_read.txt");

        try {
            reader.readFile(file, new FileReaderCallback() {
                @Override
                public void newChunk(String encode64Chunk, int index) {
                    chunksSoFar++;
                }
            });

            assertEquals(chunksSoFar, 1);
        } catch (FileReaderException ignore) {
            fail(ignore.getMessage());
        }
    }

    public void testFileReaderChunkContent() {
        FileReader reader = FileReader.FileReaderBuilder.getInstance();

        final List<String> chunks = new LinkedList<String>();
        List<String> expectedChunks = new LinkedList<String>();

        try {
            File file = new File("test/test_file_read.txt");

            Properties props = new Properties();
            props.load(new FileInputStream("test/settings.properties"));

            expectedChunks.add(props.getProperty("set") + props.getProperty("set1"));

            reader.readFile(file, new FileReaderCallback() {
                @Override
                public void newChunk(String encode64Chunk, int index) {
                    chunks.add(encode64Chunk);
                }
            });
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertEquals(chunks.size(), 1);

        int index = 0;
        for (String expected : expectedChunks) {
            assertEquals(expected, chunks.get(index++));
        }
    }
}