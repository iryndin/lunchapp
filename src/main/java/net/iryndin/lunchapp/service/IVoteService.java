package net.iryndin.lunchapp.service;

import net.iryndin.lunchapp.model.RestaurantVotesDTO;

import java.util.List;

/**
 * Service to handle voting
 */
public interface IVoteService {
    boolean voteRestaurant(String username, long restaurantId);

    RestaurantVotesDTO getTodayVotes(long restaurantId);

    List<RestaurantVotesDTO> getAllTodayVotes();

    List<RestaurantVotesDTO> getTodayWinners();
}
