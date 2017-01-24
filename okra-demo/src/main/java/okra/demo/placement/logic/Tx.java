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

package okra.demo.placement.logic;

import okra.demo.placement.mybatis.CharMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.ogcs.okra.example.game.persistence.domain.MemChar;

/**
 * 事物管理
 *
 * <pre>
 *     使用Java经典的单例模式。不使用Spring的Service. 提供另外一种方式供选择
 * </pre>
 *
 * @author TinyZ
 * @date 2017-01-15.
 */
public enum Tx {

    INSTANCE;

    private static final Logger LOG = LogManager.getLogger(Tx.class);

    private CharMapper charMapper = AppContext.getBean(CharMapper.class);

    /**
     * 创建角色
     */
    public MemChar createChar(long uid, String name, int figure) {
        try {
            MemChar memChar = new MemChar();
            memChar.setRid(uid);
            memChar.setUid(uid);
            memChar.setName(name);
            memChar.setFigure(figure);
            charMapper.insert(uid, memChar);
        } catch (Exception e) {
            LOG.error("Create Character Error.", e);
        }
        return null;
    }


}
