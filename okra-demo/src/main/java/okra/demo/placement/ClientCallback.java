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

package okra.demo.placement;

import okra.demo.placement.bean.MemItem;
import org.ogcs.okra.example.game.persistence.domain.MemAccount;
import org.ogcs.okra.example.game.persistence.domain.MemChar;

/**
 * @author TinyZ
 * @date 2017-01-24.
 */
public interface ClientCallback {

    void callbackLogin(MemAccount memAccount);

    void callbackCreateRole(MemChar memChar);

    void callbackShowBag(MemItem[] items);

    void callbackUseItem(int ret, long itemId, int count);


}
