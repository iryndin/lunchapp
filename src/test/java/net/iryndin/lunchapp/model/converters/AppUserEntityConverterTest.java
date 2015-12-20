package net.iryndin.lunchapp.model.converters;

import net.iryndin.lunchapp.datastore.entity.AppUserEntity;
import net.iryndin.lunchapp.model.UserDTO;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit tests for AppUserEntityConverter
 */
public class AppUserEntityConverterTest {

    @Test
    public void testConvertNull() {
        AppUserEntityConverter c = new AppUserEntityConverter();
        assertNull(c.convert(null));
    }

    @Test
    public void testConvertNormal() throws UnsupportedEncodingException {
        String password = "bbb";
        AppUserEntityConverter c = new AppUserEntityConverter();
        AppUserEntity e = new AppUserEntity();
        e.setRole("aaa");
        e.setUsername("bbb");
        e.setPassword((e.getUsername() + ":" + password).getBytes("UTF-8"));
        UserDTO u = c.convert(e);
        assertEquals(e.getRole(), u.getRole());
        assertEquals(e.getUsername(), u.getUsername());
        assertEquals(password, u.getPassword());
    }

    @Test
    public void testGetPasswordFromPasswordBytes() throws UnsupportedEncodingException {
        String username = "aaa";
        String password = "bbb";
        byte[] bytes = (username+":"+password).getBytes("UTF-8");
        String result = AppUserEntityConverter.getPasswordFromPasswordBytes(bytes);
        assertEquals(password, result);
    }
}
