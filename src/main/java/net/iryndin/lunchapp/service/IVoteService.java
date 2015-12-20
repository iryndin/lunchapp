package net.iryndin.lunchapp.service;

import net.iryndin.lunchapp.model.RestaurantVotesDTO;

import java.util.List;

/**
 * Service to handle voting
 */
public interface IVoteService {

    /**
     * User votes for the restaurant.
     * If vote is accepted, return true, otherwise return false.
     * @param username user that votes for the restaurant
     * @param restaurantId restaurant ID
     * @return true if vote is accepted, false otherwise
     */
    boolean voteRestaurant(String username, long restaurantId);

    /**
     * Get today accepted votes for the restaurant
     * @param restaurantId restaurant ID
     * @return vote data
     */
    RestaurantVotesDTO getTodayVotes(long restaurantId);

    /**
     * Get today accepted votes for all the restaurants
     * @return vote data
     */
    List<RestaurantVotesDTO> getAllTodayVotes();

    /**
     * Get winner restaurants for today.
     * If some restaurants have the same number of votes thay all are returned.
     * That is why we return list, not the single entity.
     * @return winners for today voting
     */
    List<RestaurantVotesDTO> getTodayWinners();
}
