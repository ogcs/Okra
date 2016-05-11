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
 *
 * Command
 *
 * @author TinyZ on 2015/10/22.
 */
public interface Command<S, R> {

    /**
     * Execute cmd logic
     *
     * @param session The player session
     * @param request The cmd request
     * @throws Exception
     */
    void execute(S session, R request) throws Exception;
}
