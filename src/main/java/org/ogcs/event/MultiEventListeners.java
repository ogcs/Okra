package org.ogcs.event;

import java.util.Arrays;

public class MultiEventListeners {

    private EventListener[] listeners;
    private int size;

    public MultiEventListeners(EventListener first, EventListener second) {
        listeners = new EventListener[2];
        listeners[0] = first;
        listeners[1] = second;
        size = 2;
    }

    public void add(EventListener l) {
        EventListener[] listeners = this.listeners;
        final int size = this.size;
        if (size == listeners.length) {
            this.listeners = listeners = Arrays.copyOf(listeners, size << 1);
        }
        listeners[size] = l;
        this.size = size + 1;
    }

    public void remove(EventListener l) {
        final EventListener[] listeners = this.listeners;
        int size = this.size;
        for (int i = 0; i < size; i++) {
            if (listeners[i] == l) {
                int listenersToMove = size - i - 1;
                if (listenersToMove > 0) {
                    System.arraycopy(listeners, i + 1, listeners, i, listenersToMove);
                }
                listeners[--size] = null;
                this.size = size;
                return;
            }
        }
    }

    public EventListener[] listeners() {
        return listeners;
    }

    public int size() {
        return size;
    }
}
