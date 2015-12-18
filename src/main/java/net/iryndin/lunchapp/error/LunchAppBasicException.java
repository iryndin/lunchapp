package net.iryndin.lunchapp.error;

/**
 * Basic exception type for the Lunch application
 */
public class LunchAppBasicException extends RuntimeException {
    public LunchAppBasicException(String msg) {
        super(msg);
    }
}
