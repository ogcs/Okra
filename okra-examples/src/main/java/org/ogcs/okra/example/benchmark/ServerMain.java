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

package org.ogcs.okra.example.benchmark;

import org.junit.Test;

/**
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/19
 * @since 1.0
 */
public class ServerMain {

    @Test
    public void test() throws InterruptedException {
        final int port = 9005;
        final int batchClientCount = 10;
        final int maxMessageCount = 1000000;
        //  server
        BenchmarkServer server = new BenchmarkServer(port);
        server.start();
        //  client
//        for (int i = 0; i < batchClientCount; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    BenchmarkClient client = new BenchmarkClient("127.0.0.1", port);
//                    client.start();
////                    client.client().writeAndFlush("ping-pong");
//                    //  数据长度1k
//                    client.client().writeAndFlush(
//                            "(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong"
//                                    + "(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong"
//                                    + "(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong"
//                                    + "(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong"
//                                    + "(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong"
//                                    + "(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong"
//                                    + "(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong"
//                                    + "(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong"
//                                    + "(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong"
//                                    + "(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong(ping-pong"
//                    );
//                }
//            }).start();
//        }
        //
        long timeStart = System.currentTimeMillis();
        while (BenchmarkClient.COUNT.get() < maxMessageCount) {
            Thread.sleep(100);
        }
        long timeEnd = System.currentTimeMillis();

        System.out.println("All Count : " + BenchmarkClient.COUNT.get());
        System.out.println("All Cost Time (ms): " + (timeEnd - timeStart));
        double avg = (timeEnd - timeStart) / (double) (BenchmarkClient.COUNT.get());
        System.out.println("Avg Cost Time (ms) : " + avg);
        System.out.println("TPS : " + 1000.0 / avg);
        System.out.println();
    }
}
