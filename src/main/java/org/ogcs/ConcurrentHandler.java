package org.ogcs;

import com.lmax.disruptor.EventHandler;

/**
 * @author TinyZ on 2015/10/22.
 */
public class ConcurrentHandler implements EventHandler<ConcurrentEvent> {
    @Override
    public void onEvent(ConcurrentEvent event, long sequence, boolean endOfBatch) throws Exception {
        try {
            Executor executor = event.getExecutor();
            if (null != executor) {
                executor.onExecute();
            }
        } finally {
            event.clearValues();
        }
    }
}
