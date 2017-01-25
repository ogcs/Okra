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
package org.ogcs.app;

/**
 * Proxy Callback Session Interface.
 * <p>Use to warp the session callback interface.</p>
 *
 * @param <T> The Api interface.
 */
public interface ProxySession<T> extends Session {

    /**
     * Get the api callback proxy.
     *
     * @return the api callback proxy.
     */
    T callback();
}
