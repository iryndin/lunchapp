package net.iryndin.lunchapp.datastore.dao;

import net.iryndin.lunchapp.datastore.entity.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO for MenuItemEntity
 */
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Long> {

    /**
     * Get all not deleted menu items for this particular restaurant
     * @param restaurantId restaurant ID
     * @return list of menu items for this particular restaurant
     */
    @Query("select m from MenuItemEntity m where m.deleted = false and m.restaurant.id = ?1 order by name")
    List<MenuItemEntity> findAllNotDeleted(long restaurantId);
}
