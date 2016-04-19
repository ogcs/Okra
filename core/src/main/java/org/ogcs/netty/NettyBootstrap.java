package org.ogcs.netty;

/**
 * Network server interface
 */
public interface NettyBootstrap<T> {

    /**
     * Create application bootstrap
     * @return Application bootstrap
     */
    T createBootstrap();

    /**
     * Run application
     */
    void start();

    /**
     * Shutdown application
     */
    void stop();

    /**
     * Return the application bootstrap object
     * @return Application bootstrap object
     */
    T bootstrap();

}
