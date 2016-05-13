package org.ogcs.event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Event dispatcher
 * <p>
 * User can use {@link #addEventListener} method to register event and related event listener<br/>
 * Invoke {@link #dispatchEvent} method to dispatch event context to registered event listeners after user's action trigger registered event
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
            ((EventListenerSet) listeners).remove(listener);
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
