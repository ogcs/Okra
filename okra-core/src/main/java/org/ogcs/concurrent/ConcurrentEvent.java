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
package org.ogcs.concurrent;

import org.ogcs.app.Executor;

/**
 *
 * Disruptor's concurrent event.
 *
 * @author : TinyZ.
 * @version  : 2017.07.07
 * @since 1.0
 * @see ConcurrentEventFactory
 * @see ConcurrentHandler
 */
public class ConcurrentEvent {

    private Executor executor;

    public Executor getExecutor() {
        return executor;
    }

    public void setValues(Executor executor) {
        this.executor = executor;
    }

    public void clearValues() {
        setValues(null);
    }
}
