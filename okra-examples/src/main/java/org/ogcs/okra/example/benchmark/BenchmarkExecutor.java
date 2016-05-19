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

package org.ogcs.okra.example.benchmark;

import org.ogcs.app.Executor;
import org.ogcs.app.Session;

public class BenchmarkExecutor implements Executor {

    protected Session session;
    protected String str;

    public BenchmarkExecutor(Session session, String str) {
        this.session = session;
        this.str = str;
    }

    @Override
    public void onExecute() {
        if (null == str) {
            throw new NullPointerException("str");
        }
        session.writeAndFlush(str);
    }

    @Override
    public void release() {
        this.session = null;
        this.str = null;
    }
}
