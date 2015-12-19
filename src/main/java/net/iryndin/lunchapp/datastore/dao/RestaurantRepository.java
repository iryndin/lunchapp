package net.iryndin.lunchapp.datastore.dao;

import net.iryndin.lunchapp.datastore.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO for RestaurantEntity
 */
@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    /**
     * Get all not deleted restaurants
     * @return list of all not deleted restaurants
     */
    @Query("select r from RestaurantEntity r where r.deleted = false order by name")
    List<RestaurantEntity> findAllNotDeleted();
}
