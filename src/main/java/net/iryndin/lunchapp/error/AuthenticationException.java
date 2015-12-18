package net.iryndin.lunchapp.error;

/**
 * Raised when no such user exist, or password is incorrect
 */
public class AuthenticationException extends LunchAppBasicException {

    private final String username;

    public AuthenticationException(String username) {
        super("Cannot authenticate user: " + username);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
