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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/19
 * @since 1.0
 */
public class ClientMain {

    public static void main(String[] args) throws InterruptedException {
        int clientCount = 1000;
        int maxCount = 1000000;
        ExecutorService service = Executors.newCachedThreadPool();
        Set<BenchmarkClient> sets = Collections.synchronizedSet(new HashSet<>());
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            for (BenchmarkClient set : sets) {
                set.stop();
            }
        }));

        long timeStart = System.currentTimeMillis();
        // client
        for (int i = 0; i < clientCount; i++) {
            service.execute(()->{
                BenchmarkClient client = new BenchmarkClient("192.168.2.29", 9005);
                client.start();
                Channel client1 = client.client();
//                client1.writeAndFlush("1");
                for (int j = 0; j <maxCount / clientCount; j++) {
                    client1.writeAndFlush("" + j);
                }
                sets.add(client);
            });
        }
        while (true) {
            if (BenchmarkClient.COUNT.get() >= maxCount){
                break;
            }
        }
        long timeEnd = System.currentTimeMillis();

        System.out.println("All Count : " + BenchmarkClient.COUNT.get());
        System.out.println("All Cost Time (ms): " + (timeEnd - timeStart));
        double avg = (timeEnd - timeStart) / (double)(BenchmarkClient.COUNT.get());
        System.out.println("Avg Cost Time (ms) : " + avg);
        System.out.println("TPS : " + BenchmarkClient.COUNT.get()/(avg / 1000.0));

        for (BenchmarkClient set : sets) {
            set.stop();
        }
    }
}
