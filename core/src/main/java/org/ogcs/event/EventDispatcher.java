package org.ogcs.event;

/**
 * Generic Event Dispatcher Interface.
 * The EventDispatcher Interface is the base class for all classes that dispatch events.
 *
 * @author TinyZ
 * @since 1.0
 */
public interface EventDispatcher {

    /**
     * Register a {@link EventListener} to dispatcher
     *
     * @param type     The event type
     * @param listener An object extend {@link EventListener} and that method will be called when the event is dispatched.
     */
    void addEventListener(Object type, EventListener listener);

    /**
     * Dispatches an event to all objects that have registered listeners for its type.
     *
     * @param event The dispatched event
     */
    void dispatchEvent(Event event);

    /**
     * Dispatches an event with the given parameters to all objects that have registered listeners for the given type.
     *
     * @param type    The event type
     * @param trigger The event trigger
     * @param source  The event source
     */
    void dispatchEvent(Object type, Object trigger, Object source);

    /**
     * Removes all listeners of all types.
     */
    void removeEventListeners();

    /**
     * Removes all listeners for the specified type
     *
     * @param type The event type
     */
    void removeEventListeners(Object type);

    /**
     * Remove the {@link EventListener} by unique listener id.
     *
     * @param type The event type
     */
    void removeEventListener(Object type, EventListener listener);

    /**
     * If the listener list exist special type event listener.
     *
     * @param type The event type
     * @return Return true if any listener with special type is exist. otherwise false.
     */
    boolean hasEventListener(Object type);

    /**
     * If the listener list exist special type event listener.
     *
     * @param type The event type
     * @param listener The special listener
     * @return Return true if the listener with special type is exist. otherwise false.
     */
    boolean hasEventListener(Object type, EventListener listener);
}
