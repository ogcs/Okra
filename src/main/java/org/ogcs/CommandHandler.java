package org.ogcs;

import com.lmax.disruptor.EventHandler;

/**
 * @author TinyZ on 2015/10/22.
 */
public class CommandHandler implements EventHandler<CommandEvent> {
    @Override
    public void onEvent(CommandEvent event, long sequence, boolean endOfBatch) throws Exception {
        try {
            Request request = event.getRequest();
            if (null != request) {
                try {
                    Command command = request.command();
                    if (command == null) {
                        throw new NullPointerException("command"); // TODO:
                    }
                    command.execute(event.getSession(), request);
                } finally {
                    request.release();
                }
            }
        } finally {
            event.clearValues();
        }
    }
}
