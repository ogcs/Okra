package org.ogcs.concurrent;

import com.lmax.disruptor.EventFactory;

/**
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2015/12/27
 */
public final class ConcurrentEventFactory implements EventFactory<ConcurrentEvent> {

    @Override
    public ConcurrentEvent newInstance() {
        return new ConcurrentEvent();
    }
}
