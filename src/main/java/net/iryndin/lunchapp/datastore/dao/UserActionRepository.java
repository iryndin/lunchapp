package net.iryndin.lunchapp.datastore.dao;

import net.iryndin.lunchapp.datastore.entity.UserActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO for UserActionEntity
 */
@Repository
public interface UserActionRepository extends JpaRepository<UserActionEntity, Long> {
}
