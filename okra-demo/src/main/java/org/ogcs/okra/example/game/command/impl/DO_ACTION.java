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

package org.ogcs.okra.example.game.command.impl;

import org.ogcs.app.Session;
import org.ogcs.okra.example.game.command.AbstractCommand;
import org.ogcs.okra.example.game.command.OkraCmd;
import org.ogcs.okra.example.game.generated.Gpb;
import org.springframework.stereotype.Component;

/**
 * execute request.
 * @author TinyZ
 * @date 2017-01-03.
 */
@OkraCmd
@Component
public class DO_ACTION extends AbstractCommand {

    @Override
    public void execute(Session session, Gpb.Request request) throws Exception {





    }
}
