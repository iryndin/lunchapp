package net.iryndin.lunchapp.web;


import net.iryndin.lunchapp.error.AuthorizationException;
import net.iryndin.lunchapp.model.UserDTO;
import net.iryndin.lunchapp.service.IUserService;
import org.junit.Test;
import org.mockito.Matchers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthUtilsTest {

    @Test
    public void testValidateAdminUserNormal() {
        AuthUtils au = new AuthUtils();
        UserDTO user = new UserDTO("c", "d", "admin");
        au.userService = mock(IUserService.class);
        when(au.userService.get(Matchers.anyString())).thenReturn(user);
        au.validateAdminUser("a", "69c679e43d855dc9e05d45cfecbc612");
    }

    @Test(expected = AuthorizationException.class)
    public void testValidateAdminUserWrongRole() {
        AuthUtils au = new AuthUtils();
        UserDTO user = new UserDTO("a", "b", "zzzzz");
        au.userService = mock(IUserService.class);
        when(au.userService.get(Matchers.anyString())).thenReturn(user);
        au.validateAdminUser("a", "aaa");
    }

    @Test
    public void testValidateRegularUserNormal() {
        AuthUtils au = new AuthUtils();
        UserDTO user = new UserDTO("a", "b", "plainuser");
        au.userService = mock(IUserService.class);
        when(au.userService.get(Matchers.anyString())).thenReturn(user);
        au.validateRegularUser("a", "bf10dd54d4722766d1080efa879ee8fb");
    }

    @Test(expected = AuthorizationException.class)
    public void testValidateRegularUserWrongRole() {
        AuthUtils au = new AuthUtils();
        UserDTO user = new UserDTO("a", "b", "xxx");
        au.userService = mock(IUserService.class);
        when(au.userService.get(Matchers.anyString())).thenReturn(user);
        au.validateRegularUser("a", "aaa");
    }
}