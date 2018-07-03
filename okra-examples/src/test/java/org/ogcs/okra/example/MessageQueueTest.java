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

import org.junit.Test;
import org.ogcs.app.Executor;
import org.ogcs.netty.handler.mq.LogicProcessor;

/**
 * @author TinyZ
 * @date 2016-07-04.
 */
public class MessageQueueTest {

    public class TestExecutor implements Executor {

        private int index;

        public TestExecutor(int index) {
            this.index = index;
        }

        @Override
        public void onExecute() {
            System.out.println("index : " + index);
        }

        @Override
        public void release() {

        }
    }

    @Test
    public void messageQueueTest() {

        LogicProcessor processor = new LogicProcessor();


        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                processor.addRequest(new TestExecutor(i));
            }
        }).start();
        new Thread(() -> {
            for (int i = 30; i < 40; i++) {
                processor.addRequest(new TestExecutor(i));
            }
        }).start();

        Thread thread = new Thread(processor, "logic-thread");
        thread.start();

    }
}
