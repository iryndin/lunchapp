package net.iryndin.lunchapp.service;

import net.iryndin.lunchapp.model.UserDTO;

/**
 * Service to manage users
 */
public interface IUserService {
    UserDTO get(String username);
}
