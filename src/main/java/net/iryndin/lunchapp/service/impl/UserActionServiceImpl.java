package net.iryndin.lunchapp.service.impl;

import net.iryndin.lunchapp.datastore.dao.AppUserRepository;
import net.iryndin.lunchapp.datastore.dao.UserActionRepository;
import net.iryndin.lunchapp.datastore.entity.AppUserEntity;
import net.iryndin.lunchapp.datastore.entity.UserActionEntity;
import net.iryndin.lunchapp.service.IUserActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Log user actions into database.
 * Not all, but only those that change data.
 */
@Service
public class UserActionServiceImpl implements IUserActionService {

    @Autowired
    UserActionRepository userActionRepository;
    @Autowired
    AppUserRepository appUserRepository;

    @Transactional
    @Override
    public void log(String entityName, String username, String action, String entityId) {
        AppUserEntity user = appUserRepository.getByUsername(username);
        userActionRepository.save(new UserActionEntity(entityName, user, action, entityId));
    }

    @Transactional
    @Override
    public void log(EntityType etype, String username, UserAction action, String entityId) {
        log(etype.name(), username, action.name(), entityId);
    }
}
