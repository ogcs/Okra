package org.ogcs;

/**
 *
 * LMAX Disruptor's event.
 *
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
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
