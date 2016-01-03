package org.ogcs.event;

/**
 * @author TinyZ on 2016/1/2.
 */
public interface ObjectEventListener<E extends Event> extends EventListener<E>  {

    /**
     * Get the event type
     * @return The event type
     */
    Object type();

    /**
     * Get event listener id
     * @return Return event listener id.
     */
    long id();

    void setId(long id);

}
