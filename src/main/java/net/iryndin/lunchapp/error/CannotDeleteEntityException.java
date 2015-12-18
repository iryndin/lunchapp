package net.iryndin.lunchapp.error;

/**
 * This exception is thrown when cannot delete entity
 */
public class CannotDeleteEntityException extends LunchAppBasicException {
    public CannotDeleteEntityException(String msg) {
        super(msg);
    }
}
