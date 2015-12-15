package org.ogcs.event;

/**
 * @author TinyZ on 2015/9/28.
 */
public interface EventListener<E extends Event> extends java.util.EventListener {

    /**
     * Get the event type
     * @return The event type
     */
    Object type();

    /**
     * Fire event
     * @param event The event context
     * @throws Exception
     */
    void fireEvent(E event) throws Exception;

    /**
     * Get event listener id
     * @return Return event listener id.
     */
    long id();

    void setId(long id);
}
