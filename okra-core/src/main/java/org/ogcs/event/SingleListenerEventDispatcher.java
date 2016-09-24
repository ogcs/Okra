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

package org.ogcs.event;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Single listener event dispatcher.
 * <p>
 * per event - per listener.
 *
 * @author TinyZ.
 * @since 1.0
 */
public class SingleListenerEventDispatcher<T> implements EventDispatcher {

    private final ConcurrentHashMap<Object, EventListener> listeners = new ConcurrentHashMap<>();

    @Override
    public void addEventListener(Object type, EventListener listener) {
        listeners.put(type, listener);
    }

    @Override
    public void dispatchEvent(Event event) {
        dispatchEvent(event.type(), event.trigger(), event.source());
    }

    @Override
    public void dispatchEvent(Object type, Object trigger, Object source) {
        EventListener listener = listeners.get(type);
        try {
            listener.fireEvent(trigger, source);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeEventListeners() {
        listeners.clear();
    }

    @Override
    public void removeEventListeners(Object type) {
        listeners.remove(type);
    }

    @Override
    public void removeEventListener(Object type, EventListener listener) {

    }

    public boolean hasEventListener(Object type) {
        return listeners.containsKey(type);
    }

    @Override
    public boolean hasEventListener(Object type, EventListener listener) {
        EventListener eventListener = listeners.get(type);
        return eventListener != null && eventListener.equals(listener);
    }
}
