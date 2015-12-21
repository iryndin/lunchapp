package net.iryndin.lunchapp.service.impl;

import net.iryndin.lunchapp.datastore.dao.AppUserRepository;
import net.iryndin.lunchapp.datastore.entity.AppUserEntity;
import net.iryndin.lunchapp.error.AuthenticationException;
import net.iryndin.lunchapp.model.UserDTO;
import net.iryndin.lunchapp.model.converters.AppUserEntityConverter;
import org.junit.Test;
import org.mockito.Matchers;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for UserServiceImpl
 */
public class UserServiceImplTest {

    @Test(expected = AuthenticationException.class)
    public void testGetForNull() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.appUserRepository = mock(AppUserRepository.class);
        when(userService.appUserRepository.getByUsername(Matchers.anyString())).thenReturn(null);
        userService.get("test");
    }

    @Test
    public void testGetNormal() throws UnsupportedEncodingException {
        String password = "aaaatest";
        AppUserEntity e = new AppUserEntity();
        e.setId(1L);
        e.setRole("admin");
        e.setUsername("admin1");
        e.setPassword((e.getUsername() + ":" + password).getBytes("UTF-8"));
        UserServiceImpl userService = new UserServiceImpl();
        userService.converter = new AppUserEntityConverter();
        userService.appUserRepository = mock(AppUserRepository.class);
        when(userService.appUserRepository.getByUsername(Matchers.anyString())).thenReturn(e);
        UserDTO dto = userService.get("test");
        assertEquals(e.getRole(), dto.getRole());
        assertEquals(e.getUsername(), dto.getUsername());
        assertEquals(password, dto.getPassword());
    }
}
