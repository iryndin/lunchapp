package net.iryndin.lunchapp.service;

/**
 * Service to handle voting
 */
public interface IUserActionService {
    enum EntityType {
        RESTAURANT,
        MENUITEM,
        VOTE
    }

    enum UserAction {
        CREATE,
        DELETE,
        UPDATE
    }

    void log(String entityName, String username, String action, String entityId);

    void log(EntityType etype, String username, UserAction action, String entityId);
}
