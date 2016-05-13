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

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author TinyZ.
 * @since 1.0
 */
public class EventListenerSet {

    private Set<EventListener> listeners;

    public EventListenerSet(EventListener first, EventListener second) {
        listeners = new HashSet<>();
        listeners.add(first);
        listeners.add(second);
    }

    public void add(EventListener l) {
        listeners.add(l);
    }

    public void remove(EventListener l) {
        listeners.remove(l);
    }

    public boolean contains(EventListener l) {
        return listeners.contains(l);
    }

    public EventListener[] listeners() {
        return listeners.toArray(new EventListener[listeners.size()]);
    }

    public int size() {
        return listeners.size();
    }
}
