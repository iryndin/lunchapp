package net.iryndin.lunchapp;

/**
 * Application constants
 */
public class AppConstants {

    public static final String PASSWORD_ENCRYPTION_KEY = "123456";

    public static final String HEADER_USERNAME = "X-LunchApp-Username";

    public static final String HEADER_KEY = "X-LunchApp-AuthKey";

    public enum UserRoleEnum {
        admin, plainuser
    }
}
