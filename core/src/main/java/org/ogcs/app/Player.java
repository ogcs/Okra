package org.ogcs.app;

/**
 * The Player interface.
 * User should be implement this interface.
 * @author TinyZ
 */
public interface Player {

    /**
     * Is the player online
     * @return Return true if the session is connected and channel is active. otherwise false.
     */
    boolean isOnline();

    /**
     * Get the player's session
     * @return Return player's session
     */
    Session session();

    /**
     * Set player's session. After player's login operation, should set call this function to set session.
     * @param session The session for player
     */
    void setSession(Session session);

    /**
     * When the player lost connection to server. System will call this function.
     */
    void logout();
}
