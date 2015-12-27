package org.ogcs.concurrent;

import com.lmax.disruptor.EventFactory;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2015/12/27
 */
public final class ConcurrentEventFactory implements EventFactory<ConcurrentEvent> {

    @Override
    public ConcurrentEvent newInstance() {
        return new ConcurrentEvent();
    }
}
