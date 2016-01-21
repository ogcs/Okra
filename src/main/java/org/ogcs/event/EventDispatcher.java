package org.ogcs.event;

/**
 *  Generic Event Dispatcher Interface.
 * @author TinyZ on 2016/1/3.
 * @param <E>
 */
public interface EventDispatcher<E> {

    /**
     * Dispatch special event
     * @param type The event type
     * @param trigger The event trigger
     * @param source The event source
     */
    void dispatchEvent(Object type, Object trigger, E source);
}
