package net.iryndin.lunchapp.datastore.dao;

import net.iryndin.lunchapp.AppConstants;
import net.iryndin.lunchapp.datastore.entity.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * DAO for AppUserEntity
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {

    /**
     * Get user by username, decrypt encrypted passwords on the database level
     */
    @Query(
            nativeQuery=true,
            value = "select id, username, decrypt('AES', '"+
                    AppConstants.PASSWORD_ENCRYPTION_KEY+
                    "',password) as password, role from appuser where username = ?1")
    AppUserEntity getByUsername(String username);
}
