package net.iryndin.lunchapp.error;

/**
 * This exception is thrown when entity is deleted
 */
public class EntityDeletedException extends LunchAppBasicException {
    public EntityDeletedException(String msg) {
        super(msg);
    }
}
