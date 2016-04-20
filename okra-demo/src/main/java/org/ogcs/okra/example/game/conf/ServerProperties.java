package org.ogcs.okra.example.game.conf;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 服务器参数配置
 *
 * @author TinyZ
 */
public class ServerProperties {

    private static final Logger LOG = LogManager.getLogger(ServerProperties.class);

    private static String fileName = "server.properties";
    // --------------------------------------------------------------------------------------
    public static int SERVER_ID = 1;
    public static String SECURITY_KEY = "abcdefghabcdefgh";

    // --------------------------------------------------------------------------------------

    static {
        String filePath = new File("").getAbsolutePath() + "/conf/" + File.separator + fileName;
        // Load properties
        Properties props = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
            SERVER_ID = Integer.parseInt(props.getProperty("server.id", String.valueOf(SERVER_ID)));
            SECURITY_KEY = props.getProperty("server.security", String.valueOf(SERVER_ID));

            LOG.info("Server properties load success.");
        } catch (IOException e) {
            LOG.warn("Server properties load failed.");
            e.printStackTrace();
        }
    }

    // --------------------------------------------------------------------------------------

    private static AtomicInteger ids = new AtomicInteger(0);

    /**
     * 生成全服唯一ID
     */
    public static long id() {
        return (((long) (ServerProperties.SERVER_ID & 0xFFFF)) << 48) | (((System.currentTimeMillis() / 1000) & 0x00000000FFFFFFFFL) << 16) | (ids.getAndIncrement() & 0x0000FFFF);
    }
}
