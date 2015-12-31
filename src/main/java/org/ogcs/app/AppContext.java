package org.ogcs.app;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author TinyZ on 2015/10/22.
 */
@Service("AppContext")
public class AppContext implements ApplicationContextAware {

//    public static final String

    private static ApplicationContext context;

    public static Object getBean(String beanName) {
        if (null == beanName) {
            return null;
        }
        Object bean = null;
        try {
            bean = context.getBean(beanName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
