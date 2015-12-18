package net.iryndin.lunchapp.web;

import com.google.common.base.Strings;
import net.iryndin.lunchapp.AppConstants;
import net.iryndin.lunchapp.error.AuthenticationException;
import net.iryndin.lunchapp.error.AuthorizationException;
import net.iryndin.lunchapp.model.UserDTO;
import net.iryndin.lunchapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

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
        //
    }
}
