package net.iryndin.lunchapp.service.impl;

import net.iryndin.lunchapp.model.RestaurantVotesDTO;
import net.iryndin.lunchapp.service.IVoteService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * IVoteService implementation
 */
@Service
public class VoteServiceImpl implements IVoteService {
    @Override
    public boolean voteRestaurant(String username, long restaurantId) {
        return false;
    }

    @Override
    public RestaurantVotesDTO getTodayVotes(long restaurantId) {
        return null;
    }

    @Override
    public List<RestaurantVotesDTO> getAllTodayVotes() {
        return null;
    }

    @Override
    public List<RestaurantVotesDTO> getTodayWinners() {
        return null;
    }
}
