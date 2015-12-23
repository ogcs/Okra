package org.ogcs.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * @author TinyZ on 2015/10/22.
 */
public interface Protocol<T extends Channel> {

    ChannelInitializer<T> newChannelInitializer();
}
