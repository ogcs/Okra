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

package org.ogcs.okra.example.jsonRequest;

import com.alibaba.fastjson.JSON;
import okra.demo.common.annotation.PublicApi;
import okra.demo.common.component.Component;
import okra.demo.placement.json.JsonRequest;
import org.junit.Before;
import org.junit.Test;
import org.ogcs.app.AppContext;
import org.ogcs.app.Command;
import org.ogcs.app.Session;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import okra.demo.placement.manager.ServiceManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TinyZ
 * @date 2017-01-22.
 */
public class JsonRequestTest {

    static {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/beans.xml");
        context.registerShutdownHook();
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    @SuppressWarnings("unchecked")
    public void test() throws Exception {
        JsonRequest request = new JsonRequest();
        request.setRid(1);
        request.setApi("showAllItem");
        request.setData("[]");

        ServiceManager manager = AppContext.getBean(ServiceManager.class);
        manager.registerService(new DemoComponent());

        Command command = manager.getCommand(request.getApi());
        command.execute(null, request);

        request.setData("[]");
        manager.getCommand("method1").execute(null, request);


    }

    @Test
    @SuppressWarnings("unchecked")
    public void testMethod1() throws Exception {
        JsonRequest request = new JsonRequest();
        request.setRid(1);
        request.setApi("method1");
        request.setData("[]");
        ServiceManager manager = AppContext.getBean(ServiceManager.class);
        manager.registerService(new DemoComponent());
        request.setData("[]");
        manager.getCommand("method1").execute(null, request);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void method() throws Exception {
        JsonRequest request = new JsonRequest();
        request.setRid(1);
        request.setApi("method1");
        request.setData("[]");
        ServiceManager manager = AppContext.getBean(ServiceManager.class);
        manager.registerService(new DemoComponent());
        //  int
        request.setData("[\"1\"]");
        manager.getCommand("methodInt").execute(null, request);
        manager.getCommand("methodInteger").execute(null, request);
        //  double
        request.setData("[\"1.0\"]");
        manager.getCommand("methodDou").execute(null, request);
        manager.getCommand("methodDouble").execute(null, request);
        //  boolean
        request.setData("[\"true\"]");
        manager.getCommand("methodBool").execute(null, request);
        manager.getCommand("methodBoolean").execute(null, request);
        //  string
        request.setData("[\"str\"]");
        manager.getCommand("methodString").execute(null, request);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void methodAry() throws Exception {
        JsonRequest request = new JsonRequest();
        request.setRid(1);
        ServiceManager manager = AppContext.getBean(ServiceManager.class);
        manager.registerService(new DemoComponent());
        //  int
        request.setData(JSON.toJSONString(new Object[]{
                new int[]{1, 2}
        }));
        manager.getCommand("methodIntAry").execute(null, request);
        manager.getCommand("methodIntegerAry").execute(null, request);
        //  double
        request.setData(JSON.toJSONString(new Object[]{
                new double[]{1.1, 2.6}
        }));
        manager.getCommand("methodDouAry").execute(null, request);
        manager.getCommand("methodDoubleAry").execute(null, request);
        //  boolean
        request.setData(JSON.toJSONString(new Object[]{
                new String[]{"true", "false"}
        }));
        manager.getCommand("methodStringAry").execute(null, request);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void methodList() throws Exception {
        JsonRequest request = new JsonRequest();
        request.setRid(1);
        ServiceManager manager = AppContext.getBean(ServiceManager.class);
        manager.registerService(new DemoComponent());
        //  list
        request.setData("[\"[\"1\", \"2\"]\"]");
        manager.getCommand("methodListBase").execute(null, request);
        //  list
        request.setData(JSON.toJSONString(Arrays.asList(new VoObj[]{
                new VoObj(100),
                new VoObj(101)
        })));
        manager.getCommand("methodListObject").execute(null, request);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void methodMap() throws Exception {
        JsonRequest request = new JsonRequest();
        request.setRid(1);
        ServiceManager manager = AppContext.getBean(ServiceManager.class);
        manager.registerService(new DemoComponent());
        //  list
        request.setData(JSON.toJSONString(new HashMap<Integer, Integer>() {{
            put(1, 1);
            put(2, 2);
        }}));
        manager.getCommand("methodMapBase").execute(null, request);
        //  list
        request.setData(JSON.toJSONString(new HashMap<Integer, VoObj>() {{
            put(1, new VoObj(100));
            put(2, new VoObj(101));
        }}));
        manager.getCommand("methodMapObject").execute(null, request);
    }

    public class DemoComponent implements Component {

        @Override
        public String id() {
            return null;
        }

        @PublicApi
        public void method1(Session session) {
            System.out.println("param Null");
        }

        @PublicApi
        public void methodInt(Session session, int id) {
            System.out.println("param int");
        }

        @PublicApi
        public void methodInteger(Session session, Integer data) {
            System.out.println("param Integer");
        }

        @PublicApi
        public void methodDou(Session session, double data) {
            System.out.println("param double");
        }

        @PublicApi
        public void methodDouble(Session session, Double data) {
            System.out.println("param Double");
        }

        @PublicApi
        public void methodString(Session session, String data) {
            System.out.println("param String");
        }

        @PublicApi
        public void methodIntAry(Session session, int[] data) {
            System.out.println("param int[]");
        }

        @PublicApi
        public void methodIntegerAry(Session session, Integer[] data) {
            System.out.println("param Integer[]");
        }

        @PublicApi
        public void methodDouAry(Session session, double[] data) {
            System.out.println("param double[]");
        }

        @PublicApi
        public void methodDoubleAry(Session session, Double[] data) {
            System.out.println("param Double[]");
        }

        @PublicApi
        public void methodStringAry(Session session, String[] data) {
            System.out.println("param String[]");
        }

        @PublicApi
        public void methodBool(Session session, boolean data) {
            System.out.println("param boolean");
        }

        @PublicApi
        public void methodBoolean(Session session, Boolean data) {
            System.out.println("param Boolean");
        }

        @PublicApi
        public void methodListBase(Session session, List<Integer> list) {
            System.out.println("param List<Integer>");
        }

        @PublicApi
        public void methodListObject(Session session, List<VoObj> list) {
            System.out.println("param List<VoObj>");
        }

        @PublicApi
        public void methodMapBase(Session session, Map<Integer, Integer> map) {
            System.out.println("param Map<Integer, Integer>");
        }

        @PublicApi
        public void methodMapObject(Session session, Map<Integer, VoObj> map) {
            System.out.println("param Map<Integer, VoObj>");
        }

    }

    public class VoObj {
        private int id;

        public VoObj(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

}
