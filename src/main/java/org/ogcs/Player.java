package org.ogcs;

/**
 * @author TinyZ on 2015/10/22.
 */
public interface Player {

    boolean isOnline();

    Session session();

    void setSession(Session session);

    void logout();

}
