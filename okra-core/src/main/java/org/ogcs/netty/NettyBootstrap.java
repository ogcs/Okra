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
package org.ogcs.netty;

/**
 * Network server interface.
 *
 * @since 1.0
 */
public interface NettyBootstrap<T> {

    /**
     * Create application bootstrap
     *
     * @return Application bootstrap
     */
    T createBootstrap();

    /**
     * Run application
     */
    void start();

    /**
     * Shutdown application
     */
    void stop();

    /**
     * Return the application bootstrap object
     *
     * @return Application bootstrap object
     */
    T bootstrap();

}
