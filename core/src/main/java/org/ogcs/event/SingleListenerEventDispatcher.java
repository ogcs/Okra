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
