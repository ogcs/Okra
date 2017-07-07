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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Event dispatcher
 * <p>
 * User can use {@link #addEventListener(Object, EventListener)} method to register event
 * and related event listener
 * Invoke {@link #dispatchEvent} method to dispatch event context to registered event
 * listeners after user's action trigger registered event
 * <p>
 *
 * @author TinyZ.
 * @since 1.0
 */
public class MultiListenerEventDispatcher implements EventDispatcher {

    private Map<Object, Object> map = new ConcurrentHashMap<>();

    @Override
    public void addEventListener(Object type, EventListener listener) {
        if (listener == null) throw new NullPointerException("listener");
        Object listeners = map.get(type);
        if (listeners == null) {
            map.put(type, listener);
        } else {
            if (listeners instanceof EventListenerSet) {
                ((EventListenerSet) listeners).add(listener);
            } else {
                final EventListener first = (EventListener) listeners;
                map.put(type, new EventListenerSet(first, listener));
            }
        }
    }

    @Override
    public void dispatchEvent(Event event) {
        dispatchEvent(event.type(), event.trigger(), event.source());
    }

    @Override
    public void dispatchEvent(Object type, Object trigger, Object source) {
        Object listeners = map.get(type);
        if (listeners == null) {
            return;
        }
        if (listeners instanceof EventListenerSet) {
            EventListener[] list = ((EventListenerSet) listeners).listeners();
            if (list != null && list.length > 0) {
                for (EventListener eventListener : list) {
                    try {
                        eventListener.fireEvent(trigger, source);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (listeners instanceof EventListener) {
            try {
                ((EventListener) listeners).fireEvent(trigger, source);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeEventListeners() {
        map.clear();
    }

    @Override
    public void removeEventListeners(Object type) {
        map.remove(type);
    }

    @Override
    public void removeEventListener(Object type, EventListener listener) {
        Object listeners = map.get(type);
        if (listeners == null) {
            return;
        }
        if (listeners instanceof EventListenerSet) {
            EventListenerSet set = (EventListenerSet) listeners;
            set.remove(listener);
            if (set.isEmpty()) {
                map.remove(type);
            }
        } else if (listeners.equals(listener)) {
            map.remove(type);
        }
    }

    @Override
    public boolean hasEventListener(Object type) {
        return map.get(type) != null;
    }

    @Override
    public boolean hasEventListener(Object type, EventListener listener) {
        Object listeners = map.get(type);
        if (listeners == null) {
            return false;
        }
        if (listeners instanceof EventListenerSet) {
            return ((EventListenerSet) listeners).contains(listener);
        } else {
            return listeners.equals(listener);
        }
    }
}
