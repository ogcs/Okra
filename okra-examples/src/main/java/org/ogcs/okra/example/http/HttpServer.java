package org.ogcs.okra.example.http;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.ogcs.netty.impl.TcpProtocolServer;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/18
 */
public class HttpServer extends TcpProtocolServer {

    public HttpServer(int port) {
        setPort(port);
    }

    @Override
    protected ChannelHandler newChannelInitializer() {
        return new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline cp = ch.pipeline();
                cp.addLast("decoder", new HttpRequestDecoder());
                cp.addLast("encoder", new HttpResponseEncoder());
                cp.addLast("aggregator", new HttpObjectAggregator(1048576));
                cp.addLast("handler", new ExampleApiHandler());
            }
        };
    }

    @Override
    public void stop() {
        super.stop();
    }
}
