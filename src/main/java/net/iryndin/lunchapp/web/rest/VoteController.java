package net.iryndin.lunchapp.web.rest;

import net.iryndin.lunchapp.AppConstants;
import net.iryndin.lunchapp.model.RestaurantVotesDTO;
import net.iryndin.lunchapp.service.IVoteService;
import net.iryndin.lunchapp.web.AuthUtils;
import net.iryndin.lunchapp.web.model.ApiResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Vote for the restaurant
 * List all votes for this restaurant
 * List all votes for all restaurants
 */
@RestController
public class VoteController {

    @Autowired
    AuthUtils authUtils;
    @Autowired
    IVoteService voteService;

    @RequestMapping(value = "/vote/{restaurantId}", method = RequestMethod.POST)
    public ApiResponseDTO<Boolean> voteRestaurant(
            @RequestHeader(AppConstants.HEADER_USERNAME) String username,
            @RequestHeader(AppConstants.HEADER_KEY) String authKey,
            @PathVariable("restaurantId") Long restaurantId
    ) {
        authUtils.validateRegularUser(username, authKey);
        boolean voteAccepted = voteService.voteRestaurant(username, restaurantId);
        return new ApiResponseDTO<>(voteAccepted);
    }

    @RequestMapping(value = "/vote/{restaurantId}", method = RequestMethod.GET)
    public ApiResponseDTO<RestaurantVotesDTO> getRestaurantVotes(
            @PathVariable("restaurantId") Long restaurantId
    ) {
        RestaurantVotesDTO restAndVotes = voteService.getTodayVotes(restaurantId);
        return new ApiResponseDTO<>(restAndVotes);
    }

    @RequestMapping(value = "/vote", method = RequestMethod.GET)
    public ApiResponseDTO<List<RestaurantVotesDTO>> getAllVotes() {
        List<RestaurantVotesDTO> allRestsAndVotes = voteService.getAllTodayVotes();
        return new ApiResponseDTO<>(allRestsAndVotes);
    }

    @RequestMapping(value = "/vote/result", method = RequestMethod.GET)
    public ApiResponseDTO<List<RestaurantVotesDTO>> getVoteResult() {
        List<RestaurantVotesDTO> winners = voteService.getTodayWinners();
        return new ApiResponseDTO<>(winners);
    }
}
