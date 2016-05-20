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

package org.ogcs.okra.example.c10k;

import org.ogcs.app.ExecutorFactory;
import org.ogcs.app.Session;
import org.ogcs.okra.example.benchmark.BenchmarkExecutor;

/**
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/20
 * @since 1.0
 */
public class NewExecutorFactory implements ExecutorFactory<BenchmarkExecutor, String> {

    @Override
    public BenchmarkExecutor newInstance(Session session, String msg) {
        return new BenchmarkExecutor(session, msg);
    }

    public static  final NewExecutorFactory DEFAULT = new NewExecutorFactory();
}
