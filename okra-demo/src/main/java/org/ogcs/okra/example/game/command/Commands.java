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

package org.ogcs.okra.example.game.command;

import org.ogcs.app.Command;
import org.ogcs.okra.example.game.command.impl.GAME_CREATE;
import org.ogcs.okra.example.game.command.impl.GAME_LOGIN;
import org.ogcs.okra.example.game.command.impl.PING_PONG;

import java.util.HashMap;
import java.util.Map;

/**
 * Command manager
 */
public enum Commands {

    INSTANCE;

    private final HashMap<Integer, Command> map;

    private static final int[] NON_AUTH_COMMAND = new int[]{1};

    Commands() {
        map = new HashMap<>();

        // ping pong test
        map.put(1, new PING_PONG());
        map.put(2, new GAME_LOGIN());
        map.put(3, new GAME_CREATE());
        // TODO: defined any other command
    }

    /**
     * Get the command instance.
     */
    public Command interpret(int cmd) throws Exception {
        if (map.containsKey(cmd)) {
            return map.get(cmd);
        } else {
            throw new Exception("Unknown command id: " + cmd);
        }
    }

    public boolean isCmdWithoutAuth(int cmd) {
        for (Integer allowed : NON_AUTH_COMMAND) {
            if (allowed == cmd) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the installed commands
     */
    public Map<Integer, Command> getCommandMap() {
        return map;
    }
}
