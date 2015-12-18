package net.iryndin.lunchapp.error;

/**
 * Raised when user has no rights to do some actions
 */
public class AuthorizationException extends LunchAppBasicException {

    private final String username;

    public AuthorizationException(String username) {
        super("Cannot authorize user: " + username);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
