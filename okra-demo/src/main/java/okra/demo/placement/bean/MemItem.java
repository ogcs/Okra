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

package okra.demo.placement.bean;

/**
 * 道具
 * @author TinyZ
 * @date 2017-01-11.
 */
public class MemItem {

    private long itemId;    //  道具唯一ID
    private int cfgItemId;  //  配置表ID
    private volatile int amount;     //  数量

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public int getCfgItemId() {
        return cfgItemId;
    }

    public void setCfgItemId(int cfgItemId) {
        this.cfgItemId = cfgItemId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
