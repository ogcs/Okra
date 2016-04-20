package org.ogcs.okra.game.command;

import org.ogcs.app.Command;
import org.ogcs.okra.game.command.impl.GAME_CREATE;
import org.ogcs.okra.game.command.impl.GAME_LOGIN;
import org.ogcs.okra.game.command.impl.PING_PONG;

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
