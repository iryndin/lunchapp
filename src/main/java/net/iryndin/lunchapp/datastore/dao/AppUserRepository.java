package net.iryndin.lunchapp.datastore.dao;

import net.iryndin.lunchapp.datastore.entity.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * DAO for AppUserEntity
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {

    String MAGICKEY = "123456";

    @Query(nativeQuery=true, value = "select id, username, decrypt('AES', '"+MAGICKEY+"',password) as password, role from appuser where username = ?1")
    AppUserEntity getByUsername(String username);
}
