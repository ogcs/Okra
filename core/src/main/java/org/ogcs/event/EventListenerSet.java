package org.ogcs.event;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author TinyZ.
 * @since 1.0
 */
public class EventListenerSet {

    private Set<EventListener> listeners;

    public EventListenerSet(EventListener first, EventListener second) {
        listeners = new HashSet<>();
        listeners.add(first);
        listeners.add(second);
    }

    public void add(EventListener l) {
        listeners.add(l);
    }

    public void remove(EventListener l) {
        listeners.remove(l);
    }

    public boolean contains(EventListener l) {
        return listeners.contains(l);
    }

    public EventListener[] listeners() {
        return listeners.toArray(new EventListener[listeners.size()]);
    }

    public int size() {
        return listeners.size();
    }
}
