/* @author zzh 2013-6-18
 * 文件数据服务
 * 用于读取文件数据, 数据类型为Json
 * 使用谷歌的Gson类库, 读取项目路径下的data文件夹中的后缀名为${suffix}的全部文件, 并实例化相应的数据,用于之后的配置表数据初始化做准备
 * */
package org.ogcs.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tinyZ 2014-5-15
 * 文件配置表数据读取攻击
 */
public class FileDataUtil {

	private static final Logger LOG = LoggerFactory.getLogger(FileDataUtil.class);

    public static final String DEFAULT_PATH = new File("").getAbsolutePath() + "/data/";    // 缺省的路径
    public static final String DEFAULT_SUFFIX = "data"; // 缺省的后缀名

    /**
     * 加载配置表 Default
     * @return
     */
    public static Map<String, byte[]> loadData() {
        return loadData(DEFAULT_PATH, DEFAULT_SUFFIX);
    }

    /**
     * 加载配置表
     * @return
     */
    public static Map<String, byte[]> loadData(String path, String suffix) {
        Map<String, byte[]> fileBytesMap = new HashMap<String, byte[]>();
        List<String> fileList = SmallFileReader.listFile(path, suffix);
        for(String fileName : fileList){
            File file = new File(path + fileName);
            // Try reading the content bytes
            try {
                byte[] bytes = SmallFileReader.readFileBytes(file);
                // Put into the map cache
                fileName = fileName.toLowerCase();
                // 获取文件名除去.data后缀
                String cacheName = fileName.substring(0, fileName.length() - suffix.length() - 1);
                fileBytesMap.put(cacheName, bytes);
                //lastModifiedMap.put(cacheName, file.lastModified());
            } catch (IOException e) {
                LOG.error("File {} read error!", fileName);
            }
        }
        return fileBytesMap;
    }
}
