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

import io.netty.util.internal.PlatformDependent;
import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 对比几种线程安全的队列。判断哪种队列做更合适[多生产者-单消费者]的消息队列。<br/>
 *
 * => netty(multiple child thread) - disruptor(single disruptor producer)<br/>
 *<br/>
 * JDK : 1.8.0_66
 *
 * Compare:<br/>
 * {@link io.netty.util.internal.MpscLinkedQueue} vs {@link ConcurrentLinkedQueue} vs {@link LinkedBlockingQueue}<br/>
 * {@link io.netty.util.internal.MpscLinkedQueue} used by Netty's {@link io.netty.channel.nio.NioEventLoop} and {@link io.netty.channel.epoll.EpollEventLoop}.<br/>
 *
 * Result:<br/>
 *
 * ConcurrentLinkedQueue > LinkedBlockingQueue > MpscLinkedQueue.
 *
 * TODO: 暂时不知道什么原因 - [待查]
 *
 *
 * @author TinyZ on 2016/6/21.
 */
public class MessageQueueTest {

    /**
     * {@link ConcurrentLinkedQueue}
     */
    @Test
    public void textConcurrentLinkedQueue() {
        testQueue(new ConcurrentLinkedQueue<>());
    }

    /**
     * {@link io.netty.util.internal.MpscLinkedQueue}
     */
    @Test
    public void textMpscLinkedQueue() {
//        testQueue(PlatformDependent.<Integer>newMpscQueue());
    }

    /**
     * {@link LinkedBlockingQueue}
     */
    @Test
    public void textBlockedLinkedQueue() {
//        testQueue(new LinkedBlockingQueue<>());
    }

    private static final int length = 30;
    private static final int threadCount = 16;
    private static final int count = 30;

    private void testQueue(Queue<Integer> mq) {
        int count = (length / threadCount);
        // message msgQueue
        // multiple producer
        for (int i = 0; i < threadCount; i++) {
            int index = i * count;
            new Thread(() -> {
                for (int j = 0; j < count; j++) {
                    mq.add(index + j);
                }
            }).start();
        }
        testSingleConsumer(mq);
    }

    private void testQueue1(Queue<Integer> mq) {
        // message msgQueue
        // multiple producer
        for (int i = 0; i < threadCount; i++) {
            int index = i * count;
            new Thread(() -> {
                for (int j = 0; j < count; j++) {
                    mq.add(index + j);
                }
            }).start();
        }
        testSingleConsumer(mq);
    }

    private void testSingleConsumer(Queue<Integer> mq) {
        // single consumer
        AtomicInteger end = new AtomicInteger();
        new Thread(() -> {
            Integer num = null;
            while (end.get() < length) {
                if ((num = mq.poll()) != null) {
                    end.getAndIncrement();
//                    System.out.println("...Num: " + num);
                }
            }
            System.out.println("...Num: " + end.get());
        }).start();
        while (end.get() >= length) {

        }
    }
}