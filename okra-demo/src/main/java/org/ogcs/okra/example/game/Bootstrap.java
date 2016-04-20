package org.ogcs.okra.example.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bootstrap game server
 */
public class Bootstrap {

    private static final Logger LOG = LogManager.getLogger(Bootstrap.class);

    public static void main(String[] args) {
        LOG.info("PreBootstrap server.");
        ClassPathXmlApplicationContext cpac = new ClassPathXmlApplicationContext("classpath:spring/beans.xml");
        cpac.registerShutdownHook();
        LOG.info("Server bootstrap successful.");
    }
}
