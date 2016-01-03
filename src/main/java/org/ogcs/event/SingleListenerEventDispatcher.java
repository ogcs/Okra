package org.ogcs.event;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Single listener event dispatcher.
 *
 * per event - per listener.
 *
 * @author TinyZ on 2016/1/2.
 */
public class SingleListenerEventDispatcher implements OldEventDispatcher<Event> {

    private final ConcurrentHashMap<Object, EventListener<Event>> listeners = new ConcurrentHashMap<>();

    @Override
    public void addEventListener(Object type, EventListener<Event> listener) {
        listeners.put(type, listener);
    }

    @Override
    public void removeEventListener(Object type) {
        listeners.remove(type);
    }

    @Override
    public boolean hasEventListener(Object type) {
        return listeners.containsKey(type);
    }

    @Override
    public void dispatchEvent(Event event) {
        if (null == event) {
            return;
        }
        EventListener<Event> listener = listeners.get(event.type());
        if (null == listener) {
            return;
        }
        try {
            listener.fireEvent(event.trigger(), event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
