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

package org.ogcs.service;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * @author TinyZ on 2016/5/12.
 */
public class SimpleTaskService extends ScheduledThreadPoolExecutor {


    public SimpleTaskService() {
        super(4);
    }

    public SimpleTaskService(int corePoolSize) {
        super(corePoolSize);
    }

    public SimpleTaskService(int corePoolSize, ThreadFactory threadFactory) {
        super(corePoolSize, threadFactory);
    }


}
