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

import com.lmax.disruptor.EventFactory;

/**
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2015/12/27
 */
public final class ConcurrentEventFactory implements EventFactory<ConcurrentEvent> {

    @Override
    public ConcurrentEvent newInstance() {
        return new ConcurrentEvent();
    }

    public static final ConcurrentEventFactory DEFAULT = new ConcurrentEventFactory();
}
