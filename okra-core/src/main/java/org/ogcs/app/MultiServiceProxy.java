package org.ogcs.app;

/**
 * Multiple Service Interface Proxy.
 * <p>Use to warp the session callback interface.</p>
 *
 * @author TinyZ.
 * @version 2017.07.07
 * @since 2.0
 * @see ServiceProxy
 */
public interface MultiServiceProxy {

    <T> T proxy(Class<T> clz);

    void registerService(Class<?> clz, Session session);
}
