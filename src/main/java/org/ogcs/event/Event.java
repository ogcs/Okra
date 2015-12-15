package org.ogcs.event;

/**
 * @author TinyZ on 2015/9/29.
 */
public interface Event<T> {

    /**
     * Get the event type
     * @return The event type
     */
    Object type();

    /**
     * Get the event trigger
     * @return event trigger
     */
    Object trigger();

    /**
     * Get dispatcher event data
     * @return event data
     */
    T source();
}
