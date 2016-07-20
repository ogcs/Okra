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

package org.ogcs.app;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;

/**
 * @author TinyZ
 * @since 1.0
 */
@Service("AppContext")
public class AppContext implements ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * Get bean
     *
     * @param beanName The bean name.
     * @param clz      The bean class.
     * @param <T>      The bean {@link Type}
     * @return Return the bean
     */
    public static <T> T getBean(String beanName, Class<T> clz) {
        if (null == beanName)
            return null;
        try {
            return context.getBean(beanName, clz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get bean
     *
     * @param beanName The bean name.
     * @return Return the bean
     */
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
