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

package okra.demo.placement.module;

import okra.demo.placement.bean.MemItem;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TinyZ
 * @date 2017-01-11.
 */
public class ItemModule extends AbstractModule {

    private Map<Long, MemItem> map = new HashMap<>();

    @Override
    public int id() {
        return 0;
    }

    @Override
    public void loadFromDB() {

    }

    public MemItem getItem(long itemId) {
        return null;
    }

}
