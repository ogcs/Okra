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

package org.ogcs.okra.example.socket;

import org.ogcs.app.Executor;
import org.ogcs.app.Session;

/**
 *
 */
public class ObjectExecutor implements Executor {

    protected Session session;
    protected Object request;

    public ObjectExecutor(Session session, Object request) {
        this.session = session;
        this.request = request;
    }

    @Override
    public void onExecute() {
        if (null == request) {
            throw new NullPointerException("request");
        }
        // TODO: Just send message back, do some logic on real
        session.writeAndFlush(String.valueOf(request));
    }

    @Override
    public void release() {
        this.session = null;
        this.request = null;
    }
}
