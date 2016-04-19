package org.ogcs.okra.example.socket;

import org.ogcs.app.Executor;
import org.ogcs.app.Session;
import org.ogcs.netty.handler.DisruptorAdapterBy41xHandler;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/18
 */
public class ExampleSocketHandler extends DisruptorAdapterBy41xHandler<Object> {
    @Override
    protected Executor newExecutor(Session session, Object msg) {
        return new ObjectExecutor(session, msg);
    }
}
