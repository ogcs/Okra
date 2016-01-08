package org.ogcs.event;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Default event dispatcher
 * <p>
 * User can use {addEventListener} method to register event and related event listener<br/>
 * Invoke dispatchEvent method to dispatch event context to registered event listeners after user's action trigger registered event
 * <p>
 * Created by TinyZ on 2015/4/23.
 */
public class MultiListenerEventDispatcher implements EventDispatcher {

    protected final ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();

    @Override
    public void addEventListener(Object type, EventListener listener) {
        if (listener == null) throw new NullPointerException("listener");
        Object listeners = map.get(type);
        if (listeners == null) {
            map.put(type, listener);
        } else {
            if (listeners instanceof MultiEventListeners) {
                ((MultiEventListeners) listeners).add(listener);
            } else {
                final EventListener first = (EventListener) listeners;
                map.put(type, new MultiEventListeners(first, listener));
            }
        }
    }

    @Override
    public void removeEventListener(Object type) {
        map.remove(type);
    }

    @Override
    public boolean hasEventListener(Object type) {
        return map.containsKey(type) && map.get(type) != null;
    }

    @Override
    public void dispatchEvent(Object type, Object trigger, Object source) {
        Object listeners = map.get(type);
        if (listeners == null) {
            return;
        }
        if (listeners instanceof MultiEventListeners) {
            EventListener[] list = ((MultiEventListeners) listeners).listeners();
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
}
