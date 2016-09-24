/*
 *     Copyright 2016-2026 TinyZ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ogcs.utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * File read utilities.
 *
 * @author tinyZ
 * @version 1.0
 */
public class SmallFileReader {

    /**
     * 读取文件
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String readSmallFile(String filePath) throws IOException {
        if ((filePath == null) || (filePath.equals(""))) {
            return null;
        }
        File smallFile = new File(filePath);
        return readSmallFile(smallFile);
    }

    /**
     * 读取文件
     *
     * @param smallFile
     * @return
     * @throws IOException
     */
    public static String readSmallFile(File smallFile) throws IOException {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(smallFile), "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buf = new StringBuffer();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            buf.append(line);
        }
        bufferedReader.close();
        reader.close();
        return buf.toString();
    }

    /**
     * 读取文件
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] readFileBytes(String filePath) throws IOException {
        if ((filePath == null) || (filePath.equals(""))) {
            return null;
        }
        File smallFile = new File(filePath);
        return readFileBytes(smallFile);
    }

    /**
     * 读取文件
     *
     * @param smallFile
     * @return
     * @throws IOException
     */
    public static byte[] readFileBytes(File smallFile) throws IOException {
        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(smallFile);
            int read = 0;
            while ((read = ios.read(buffer)) != -1)
                ous.write(buffer, 0, read);
        } finally {
            try {
                if (ous != null) {
                    ous.close();
                }
                if (ios != null) {
                    ios.close();
                }
            } catch (IOException localIOException) {
            }
        }
        return ous.toByteArray();
    }

    /**
     * 获取文件夹路径下的全部后缀名为suffix的文件的路径
     *
     * @param path
     * @param suffix
     * @return
     */
    public static List<String> listFile(String path, String suffix) {
        List<String> list = new ArrayList<String>();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File file1 : files) {
                    String filePath = file1.getName();
                    if (suffix != null) {
                        int begIndex = filePath.lastIndexOf(".");
                        String tempSuffix = "";
                        if (begIndex != -1) {
                            tempSuffix = filePath.substring(begIndex + 1, filePath.length());
                        }
                        if (tempSuffix.equals(suffix)) {
                            list.add(filePath);
                        }
                    } else {
                        list.add(filePath);
                    }
                }
            }
        }
        return list;
    }
}