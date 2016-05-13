package org.ogcs.event;

/**
 * Event listener interface.
 *
 * {@link #fireEvent(Object, Object)} method will be called when the event is dispatched.
 *
 * @author TinyZ
 * @since 1.0
 */
public interface EventListener {

    /**
     * Fire event
     *
     * @param trigger The event trigger
     * @param source The event data
     * @throws Exception
     */
    void fireEvent(Object trigger, Object source) throws Exception;
}
