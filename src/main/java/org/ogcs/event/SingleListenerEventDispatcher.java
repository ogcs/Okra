package org.ogcs.event;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Single listener event dispatcher.
 *
 * per event - per listener.
 *
 * @author TinyZ on 2016/1/2.
 */
public class SingleListenerEventDispatcher<T> implements EventDispatcher<T> {

    private final ConcurrentHashMap<Object, EventListener<T>> listeners = new ConcurrentHashMap<>();

    public void addEventListener(Object type, EventListener<T> listener) {
        listeners.put(type, listener);
    }

    public void removeEventListener(Object type) {
        listeners.remove(type);
    }

    public boolean hasEventListener(Object type) {
        return listeners.containsKey(type);
    }

    @Override
    public void dispatchEvent(Object type, Object trigger, T source) {
        EventListener<T> listener = listeners.get(type);
        try {
            listener.fireEvent(trigger, source);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
