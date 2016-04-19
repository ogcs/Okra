package org.ogcs.concurrent;

import org.ogcs.app.Executor;

/**
 *
 * Disruptor's concurrent event.
 *
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2015/11/20
 */
public class ConcurrentEvent {

    private Executor executor;

    public Executor getExecutor() {
        return executor;
    }

    public void setValues(Executor executor) {
        this.executor = executor;
    }

    public void clearValues() {
        setValues(null);
    }
}
