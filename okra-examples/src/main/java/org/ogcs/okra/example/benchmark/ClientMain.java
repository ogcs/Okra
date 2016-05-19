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

import io.netty.channel.Channel;

/**
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/19
 * @since 1.0
 */
public class ClientMain {

    public static void main(String[] args) throws InterruptedException {
        // client
        BenchmarkClient client = new BenchmarkClient("192.168.2.93", 9005);
        client.start();
        Channel client1 = client.client();

        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            client1.writeAndFlush("" + i);
        }
        while (BenchmarkClient.COUNT.get() <= 1) {

        }
        long timeEnd = System.currentTimeMillis();
        client.stop();

        System.out.println("All Count : " + BenchmarkClient.COUNT.get());
        System.out.println("All Cost Time (ms): " + (timeEnd - timeStart));
        double avg = (timeEnd - timeStart) / BenchmarkClient.COUNT.get();
        System.out.println("Avg Cost Time (ms) : " + avg);
        System.out.println("TPS : " + BenchmarkClient.COUNT.get()/(avg / 1000.0));
    }
}
