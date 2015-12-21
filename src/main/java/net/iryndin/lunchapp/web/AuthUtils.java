package net.iryndin.lunchapp.web;

import com.google.common.base.Strings;
import net.iryndin.lunchapp.AppConstants;
import net.iryndin.lunchapp.error.AuthenticationException;
import net.iryndin.lunchapp.error.AuthorizationException;
import net.iryndin.lunchapp.error.LunchAppBasicException;
import net.iryndin.lunchapp.model.UserDTO;
import net.iryndin.lunchapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Authentication utility
 */
@Component
public class AuthUtils {

    @Autowired
    IUserService userService;

    public void validateAdminUser(String username, String authKey) {
        validateUser(username, authKey, AppConstants.UserRoleEnum.admin.name());
    }

    public void validateRegularUser(String username, String authKey) {
        validateUser(username, authKey, AppConstants.UserRoleEnum.plainuser.name());
    }

    public void validateUser(String username, String authKey, String userRole) {
        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(authKey) || Strings.isNullOrEmpty(userRole)) {
            throw new AuthenticationException(username);
        }
        try {
            UserDTO user = userService.get(username);
            if (!user.getRole().equalsIgnoreCase(userRole)) {
                throw new AuthorizationException(username);
            }
            checkUserAuth(user, username, authKey);
        } catch (EntityNotFoundException enfe) {
            throw new AuthenticationException(username);
        }
    }

    private void checkUserAuth(UserDTO user, String username, String authKey) {
        try {
            String hashtext = getHashtext(user.getUsername(), user.getPassword());
            if (!hashtext.equalsIgnoreCase(authKey.trim())) {
                throw new AuthenticationException(username);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new LunchAppBasicException("Should not happen");
        }
    }

    public static String getHashtext(String username, String password) throws NoSuchAlgorithmException {
        String a = username.trim() + ":::" + password.trim();
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(a.getBytes());
        byte[] digest = m.digest();
        return new BigInteger(1,digest).toString(16);
    }

    /*
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(getHashtext("admin1", "apassword1"));
        System.out.println(getHashtext("admin2", "apassword2"));
        System.out.println(getHashtext("user1", "password"));
        System.out.println(getHashtext("a", "b"));
        System.out.println(getHashtext("c", "d"));
    }*/
}
