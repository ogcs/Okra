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

package okra.demo.common.module.impl;

import okra.demo.placement.bean.MemItem;
import okra.demo.common.module.AbstractModule;
import okra.demo.common.mybatis.ItemMapper;
import okra.demo.common.Consts;
import okra.demo.common.Role;
import org.ogcs.app.AppContext;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 玩家的道具模块
 * 道具可叠加和不可叠加两种.
 * 服务器虽然记录物品的配置表ID， 但实际逻辑中不应该通过配置表ID操作。 除非道具系统的配置表ID可以作为主键存在
 * <pre>
 *     推荐逻辑：
 *     1. 有客户端判断物品数量是否足够，发送给服务端批量使用某些指定ID的物品
 *     2. 服务端校验指定列表的道具是否满足使用需求（种类和数量）。
 *     3. 返回客户端操作结果。
 * </pre>
 * @author TinyZ
 * @date 2017-01-11.
 */
public class ItemModule extends AbstractModule {

    private ItemMapper itemMapper = AppContext.getBean(ItemMapper.class);
    private volatile Map<Long/* itemId */, MemItem> map = new ConcurrentHashMap<>();

    public ItemModule(Role role) {
        super(role);
    }

    @Override
    public int id() {
        return Consts.MODULE_ITEM;
    }

    @Override
    public void loadFromDB() {
        List<MemItem> memItems = itemMapper.selectByUid(role.id());
        for (MemItem memItem : memItems) {
            map.put(memItem.getItemId(), memItem);
        }
    }

    public List<MemItem> getAll() {
        return new ArrayList<>(map.values());
    }

    public MemItem getItem(long itemId) {
        return map.get(itemId);
    }

    public void add(MemItem memItem) {
        map.put(memItem.getItemId(), memItem);
    }

    public boolean delete(long itemId, int amount) {
        MemItem memItem = map.get(itemId);
        if (memItem == null) return false;
        if (memItem.getAmount() > amount) {
            memItem.setAmount(memItem.getAmount() - amount);
        } else if (memItem.getAmount() == amount) {
            map.remove(itemId);
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        map.clear();
    }
}
