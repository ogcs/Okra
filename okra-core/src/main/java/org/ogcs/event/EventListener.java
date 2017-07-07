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
 * Event listener interface.
 *
 * {@link #fireEvent(Object, Object)} method will be called when the event is dispatched.
 *
 * @author TinyZ
 * @since 1.0
 * @see EventDispatcher
 */
public interface EventListener {

    /**
     * Fire event
     *
     * @param trigger The event trigger
     * @param source The event data
     * @throws Exception any exception.
     */
    void fireEvent(Object trigger, Object source) throws Exception;
}
