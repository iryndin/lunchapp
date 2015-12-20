package net.iryndin.lunchapp.model.converters;

import net.iryndin.lunchapp.datastore.entity.AppUserEntity;
import net.iryndin.lunchapp.error.LunchAppBasicException;
import net.iryndin.lunchapp.model.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class AppUserEntityConverter implements Converter<AppUserEntity, UserDTO> {

    @Override
    public UserDTO convert(AppUserEntity e) {
        if (e != null) {
            return new UserDTO(
                    e.getUsername(),
                    getPasswordFromPasswordBytes(e.getPassword()),
                    e.getRole());
        }
        return null;
    }

    public static String getPasswordFromPasswordBytes(byte[] b) {
        try {
            String passwordString = new String(b, "UTF-8");
            String password = passwordString.substring(passwordString.indexOf(":")+1);
            return password;
        } catch (UnsupportedEncodingException e1) {
            throw new LunchAppBasicException("Cannot decode bytes to utf8 string");
        }
    }
}
