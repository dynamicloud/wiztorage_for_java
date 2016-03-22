package org.dynamicloud.wiztorage.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
 * <code>WiztorageUtils</code>
 * <p/>
 * This class provides a set of generic utilities
 *
 * @author Eleazar Gomez
 */
public class WiztorageUtils {
    /**
     * This method generates a checksum from a passed string
     *
     * @param string to generate checksum
     * @return checksum string
     */
    public static String generateCheckSum(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());

            byte byteData[] = md.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aByteData : byteData) {
                String hex = Integer.toHexString(0xff & aByteData);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (java.security.NoSuchAlgorithmException missing) {
            return "";
        }
    }

    /**
     * This methods returns a type according to the file
     *
     * @param file in context
     * @return the file type
     */
    public static String getType(File file) {
        try {
            String contentType = Files.probeContentType(Paths.get(file.getAbsolutePath()));
            if (contentType != null) {
                return contentType;
            }
        } catch (Exception ignore) {

        }

        String fileName = file.getName();

        int lastDot = fileName.lastIndexOf(".");
        if (lastDot < 0) {
            return "Unrecognized";
        }

        return fileName.substring(lastDot + 1) + "/file";
    }
}