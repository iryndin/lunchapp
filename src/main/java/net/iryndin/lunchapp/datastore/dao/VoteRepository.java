package net.iryndin.lunchapp.datastore.dao;

import net.iryndin.lunchapp.datastore.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO for UserActionEntity
 */
@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, Long> {

    /**
     * Get today vote by user
     * @return list of all not deleted restaurants
     */
    @Query("select v from VoteEntity v where v.user.id = ?1 and day(v.createDate) = day(current_date()) and month(v.createDate) = month(current_date()) and year(v.createDate) = year(current_date())")
    VoteEntity getTodayVoteByUser(Long userId);

    /**
     * Get today votes for a restaurant
     * @return list of all votes (created today) for a given restaurant
     */
    @Query("select v from VoteEntity v where v.restaurant.id = ?1 and day(v.createDate) = day(current_date()) and month(v.createDate) = month(current_date()) and year(v.createDate) = year(current_date())")
    List<VoteEntity> getTodayVotes(long restaurantId);

    /**
     * Get today votes for all restaurants
     * @return list of all votes (created today) for all restaurants
     */
    @Query("select v from VoteEntity v where day(v.createDate) = day(current_date()) and month(v.createDate) = month(current_date()) and year(v.createDate) = year(current_date())")
    List<VoteEntity> getTodayVotes();

}
