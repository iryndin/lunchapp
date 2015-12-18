package net.iryndin.lunchapp.service.impl;

import net.iryndin.lunchapp.datastore.dao.AppUserRepository;
import net.iryndin.lunchapp.datastore.entity.AppUserEntity;
import net.iryndin.lunchapp.error.AuthenticationException;
import net.iryndin.lunchapp.model.UserDTO;
import net.iryndin.lunchapp.model.converters.AppUserEntityConverter;
import net.iryndin.lunchapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

/**
 * IUserService implementation
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    AppUserEntityConverter converter;

    @Transactional
    @Override
    public UserDTO get(String username) {
        AppUserEntity user = appUserRepository.getByUsername(username);
        if (user == null) {
            throw new AuthenticationException(username);
        }
        return converter.convert(user);
    }
}
