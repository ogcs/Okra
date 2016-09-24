/*
 *     Copyright 2016-2026 TinyZ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
