package org.ogcs.app;

import io.netty.channel.ChannelId;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TinyZ on 2015/10/27.
 */
public enum Sessions {

    INSTANCE;

    Sessions() {
        sessions = new ConcurrentHashMap<>();
    }

    private final ConcurrentHashMap<ChannelId, Session> sessions;

    public Session get(ChannelId id) {
        return sessions.get(id);
    }

    public void register(ChannelId id, Session session) {
        sessions.put(id, session);
    }

    public Session unregister(ChannelId id) {
        return sessions.remove(id);
    }
}
