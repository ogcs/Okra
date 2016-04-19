package org.ogcs.concurrent;

import com.lmax.disruptor.EventHandler;
import org.ogcs.app.Executor;

/**
 * The disruptor concurrent handler
 *
 */
public class ConcurrentHandler implements EventHandler<ConcurrentEvent> {
    @Override
    public void onEvent(ConcurrentEvent event, long sequence, boolean endOfBatch) throws Exception {
        try {
            Executor executor = event.getExecutor();
            if (null != executor) {
                try {
                    executor.onExecute();
                } finally {
                    executor.release();
                }
            }
        } finally {
            event.clearValues();
        }
    }
}
