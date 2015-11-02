package org.ogcs;

import java.util.Map;

/**
 * @author TinyZ on 2015/10/22.
 */
public interface Request {

    Command command();

    int getRequestId();

    void setRequestId(int requestId);

    Map<String, Object> getParams();

    void setParams(Map<String, Object> params);

    void release();
}
