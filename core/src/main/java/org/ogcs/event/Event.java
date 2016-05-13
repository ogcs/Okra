package org.ogcs.event;

/**
 * Event object.
 * Event objects are passed as parameters to event listeners when an event occurs.
 *
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @since 1.0
 */
public class Event<T> {

    private Object type;
    private Object trigger;
    private T source;

    public Event(Object type, Object trigger, T source) {
        this.type = type;
        this.trigger = trigger;
        this.source = source;
    }

    /**
     * The object type of the event.
     *
     * @return The event type
     */
    public Object type() {
        return type;
    }

    /**
     * Get the event trigger
     *
     * @return event trigger
     */
    public Object trigger(){
        return trigger;
    }

    /**
     * Get dispatcher event data
     *
     * @return event data
     */
    public T source(){
        return source;
    }
}
