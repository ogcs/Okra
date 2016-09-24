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

package org.ogcs.netty.handler.mq;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.ogcs.app.Executor;
import org.ogcs.concurrent.ConcurrentEvent;
import org.ogcs.concurrent.ConcurrentHandler;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import static com.lmax.disruptor.dsl.ProducerType.SINGLE;
import static org.ogcs.concurrent.ConcurrentEventFactory.DEFAULT;

/**
 * Logic processor.
 * @author TinyZ.
 * @since 1.0
 */
public class LogicProcessor implements Runnable {

    private static final int DEFAULT_SIZE = 8 * 1024;
    private static final ExecutorService DEFAULT_POOL = Executors.newCachedThreadPool();

    protected AtomicLong waitSize = new AtomicLong(0);
    protected Queue<Executor> msgQueue;
    protected Disruptor<ConcurrentEvent> disruptor;

    public LogicProcessor() {
        this(DEFAULT, new ConcurrentHandler(), DEFAULT_SIZE, DEFAULT_POOL, SINGLE, new BlockingWaitStrategy());
    }

    public LogicProcessor(EventFactory<ConcurrentEvent> factory, EventHandler<ConcurrentEvent> handler, int rbSize, ExecutorService es, ProducerType pt, WaitStrategy ws) {
        this.disruptor = new Disruptor<>(factory, rbSize, es, pt, ws);
        this.disruptor.handleEventsWith(handler);
        //  disruptor.handleExceptionsWith();
        this.disruptor.start();
        this.msgQueue = newQueue();
    }

    protected Queue<Executor> newQueue() {
        return new ConcurrentLinkedQueue<>();
    }

    public void addRequest(Executor executor) {
        msgQueue.add(executor);
        waitSize.incrementAndGet();
    }

    public long mqSize() {
        return waitSize.get();
    }

    @Override
    public void run() {
        while (true) {
            Executor executor;
            while ((executor = msgQueue.poll()) != null) {
                RingBuffer<ConcurrentEvent> rb = disruptor.getRingBuffer();
                long next = rb.next();
                try {
                    ConcurrentEvent event = rb.get(next);
                    event.setValues(executor);
                } finally {
                    rb.publish(next);
                    waitSize.decrementAndGet();
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}