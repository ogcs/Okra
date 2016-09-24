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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * File read utilities.
 *
 * @author TinyZ
 * @since 1.0
 */
public final class TxtReader {

    private static final Logger LOG = LogManager.getLogger(TxtReader.class);

    private TxtReader() {
        // no-op
    }

    /**
     * 读取文件
     */
    public static String readFile(String path) throws IOException {
        if ((path == null) || (path.equals(""))) {
            return null;
        }
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        return readFile(file, Charset.forName("UTF-8"));
    }

    /**
     * 读取文件
     */
    public static String readFile(File file, Charset charset) {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), charset);
             BufferedReader br = new BufferedReader(reader)) {
            StringBuilder buf = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                buf.append(line);
            }
            return buf.toString();
        } catch (IOException e) {
            LOG.error("TxtReader read file error. Path : " + file.getPath(), e);
        }
        return null;
    }

    /**
     * 读取文件
     */
    public static byte[] readFileBytes(String path) throws IOException {
        if ((path == null) || (path.equals(""))) {
            return null;
        }
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        return readFileBytes(file);
    }

    /**
     * 读取文件
     */
    public static byte[] readFileBytes(File file) throws IOException {
        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
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
     * @param path   The folder path.
     * @param suffix The file suffix.
     * @return Return the path of all files under the folder.
     */
    public static List<String> listFile(String path, String suffix) {
        List<String> list = new ArrayList<>();
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