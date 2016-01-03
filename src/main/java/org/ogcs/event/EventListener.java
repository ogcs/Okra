package org.ogcs.event;

/**
 * @author TinyZ on 2015/9/28.
 */
public interface EventListener<E> {

    /**
     * Fire event
     *
     * @param trigger The event trigger
     * @param event The event context
     * @throws Exception
     */
    void fireEvent(Object trigger, E event) throws Exception;
}
