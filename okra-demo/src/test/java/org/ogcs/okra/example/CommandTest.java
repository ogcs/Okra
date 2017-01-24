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

package org.ogcs.okra.example;

import org.junit.Before;
import org.junit.Test;
import org.ogcs.app.AppContext;
import okra.demo.placement.bean.MemItem;
import okra.demo.common.mybatis.ItemMapper;
import org.ogcs.okra.example.game.command.impl.DO_ACTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author TinyZ
 * @date 2017-01-03.
 */
public class CommandTest {

    @Autowired
    DO_ACTION doAction;

    @Before
    public void setUp() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/beans.xml");
        context.registerShutdownHook();
    }

    @Test
    public void test() throws Exception {
        MemItem memItem = new MemItem();
        memItem.setCfgItemId(10099);
        memItem.setAmount(100);

        ItemMapper itemMapper = AppContext.getBean("itemMapper", ItemMapper.class);
        itemMapper.insert(1, memItem);

        System.out.println();

//        doAction.execute(null, null);

    }

}
