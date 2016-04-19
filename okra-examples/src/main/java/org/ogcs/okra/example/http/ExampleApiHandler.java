package org.ogcs.okra.example.http;

import io.netty.handler.codec.http.FullHttpRequest;
import org.ogcs.app.Executor;
import org.ogcs.app.Session;
import org.ogcs.netty.handler.DisruptorAdapterBy41xHandler;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/18
 */
public class ExampleApiHandler extends DisruptorAdapterBy41xHandler<FullHttpRequest> {
    @Override
    protected Executor newExecutor(Session session, FullHttpRequest msg) {
        return new HttpRequestExecutor(session, msg);
    }
}
