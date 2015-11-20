package org.ogcs.app;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author TinyZ on 2015/10/22.
 */
public class AppContext implements ApplicationContextAware {

//    public static final String

    private static ApplicationContext context;

    public static Object getBean(String beanName) {
        if (null == beanName) {
            return null;
        }
        return context.getBean(beanName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
