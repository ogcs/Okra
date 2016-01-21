package org.ogcs.event;

/**
 *  Generic Event Dispatcher Interface.
 * @author TinyZ on 2016/1/3.
 * @param <E>
 */
public interface EventDispatcher2<E> {

    /**
     * Add new {@link EventListener} to dispatcher
     * @param type The event type
     * @param listener The EventListener
     */
    void addEventListener(Object type, EventListener<E> listener);

    /**
     * Remove the {@link EventListener} by unique listener id.
     * @param type The event type
     */
    void removeEventListener(Object type);

    /**
     * If the listener list exist special type event listener.
     * @param type The event type
     * @return Return true if the listener list exist special type event listener. otherwise false.
     */
    boolean hasEventListener(Object type);

    /**
     * Dispatch special event
     * @param type The event type
     * @param trigger The event trigger
     * @param source The event source
     */
    void dispatchEvent(Object type, Object trigger, E source);
}
