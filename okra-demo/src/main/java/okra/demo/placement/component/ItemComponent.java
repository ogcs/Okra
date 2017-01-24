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

package okra.demo.placement.component;

import okra.demo.placement.Consts;
import okra.demo.common.annotation.PublicApi;
import okra.demo.common.component.Component;
import okra.demo.placement.role.module.ItemModule;
import okra.demo.placement.bean.MemItem;
import okra.demo.placement.json.JsonSession;
import okra.demo.placement.role.PmRole;
import org.ogcs.app.Session;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 道具组件
 * @author TinyZ
 * @date 2017-01-13.
 */
@Service
public class ItemComponent implements Component {

    @Override
    public String id() {
        return String.valueOf(Consts.COMPONENT_ITEM);
    }

    /**
     * 显示背包信息
     */
    @PublicApi
    public void showAllItem(JsonSession session) {
        PmRole role = (PmRole) session.getConnector();
        ItemModule module = role.getModule(Consts.MODULE_ITEM);
        if (module != null) {
            List<MemItem> all = module.getAll();
            session.callback().callbackShowBag(all.toArray(new MemItem[all.size()]));
        }
    }

    /**
     * 使用道具
     * @param itemId    道具唯一ID
     * @param count     道具数量
     */
    @PublicApi
    public void useItem(Session session, long itemId, int count) {
        if (itemId <= 0 ||
                count <= 0 || count > 1000) {
            return;
        }
        PmRole role = (PmRole) session.getConnector();
        ItemModule module = role.getModule(Consts.MODULE_ITEM);
        if (module == null || !module.delete(itemId, count)) {
            ((JsonSession)session).callback().callbackUseItem(-1, itemId, count);
        }
        ((JsonSession)session).callback().callbackUseItem(0, itemId, count);
    }

    /**
     * 购买道具
     * @param cfgShopId 配置表ID
     * @param amount    购买数量
     */
    @PublicApi
    public void buyItem(Session session, int cfgShopId,int amount) {
        if (cfgShopId <= 0 ||
                amount <= 0 || amount > 1000) {
            return;
        }
        PmRole role = (PmRole) session.getConnector();
        ItemModule module = role.getModule(Consts.MODULE_ITEM);

        //  get from config data.
//        MemItem item = module.getItem(itemId);
//        if (!module.delete(itemId, amount)) {
//            ((JsonSession)session).callback().callbackUseItem(-1, itemId, amount);
//        }
//        ((JsonSession)session).callback().callbackUseItem(0, itemId, amount);
    }
}
