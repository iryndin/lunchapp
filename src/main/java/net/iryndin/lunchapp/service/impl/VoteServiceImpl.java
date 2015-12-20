package net.iryndin.lunchapp.service.impl;

import net.iryndin.lunchapp.datastore.dao.AppUserRepository;
import net.iryndin.lunchapp.datastore.dao.RestaurantRepository;
import net.iryndin.lunchapp.datastore.dao.VoteRepository;
import net.iryndin.lunchapp.datastore.entity.AppUserEntity;
import net.iryndin.lunchapp.datastore.entity.RestaurantEntity;
import net.iryndin.lunchapp.datastore.entity.VoteEntity;
import net.iryndin.lunchapp.error.AuthenticationException;
import net.iryndin.lunchapp.error.EntityDeletedException;
import net.iryndin.lunchapp.model.RestaurantDTO;
import net.iryndin.lunchapp.model.RestaurantVotesDTO;
import net.iryndin.lunchapp.service.IUserActionService;
import net.iryndin.lunchapp.service.IVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * IVoteService implementation
 */
@Service
public class VoteServiceImpl implements IVoteService {

    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    IUserActionService userActionService;
    @Autowired
    Converter<RestaurantEntity, RestaurantDTO> restaurantConverter;

    @Transactional
    @Override
    public boolean voteRestaurant(String username, long restaurantId) {
        AppUserEntity user = appUserRepository.getByUsername(username);
        if (user == null) {
            throw new AuthenticationException(username);
        }
        RestaurantEntity restaurant = restaurantRepository.getOne(restaurantId);
        if (restaurant.isDeleted()) {
            throw new EntityDeletedException("Restaurant is deleted, id=" + restaurantId);
        }
        VoteEntity userVote = voteRepository.getTodayVoteByUser(user.getId());
        if (userVote == null) {
            createUserVote(restaurant, user);
            return true;
        } else if (canAcceptUserVote(userVote)) {
            if (userVote.getRestaurant().getId() != restaurantId) {
                userVote.setRestaurant(restaurant);
                userVote.setCreateDate(new Date());
                voteRepository.saveAndFlush(userVote);
                userActionService.log(
                        IUserActionService.EntityType.VOTE,
                        username,
                        IUserActionService.UserAction.UPDATE,
                        Long.toString(userVote.getId())
                );
            }
            return true;
        }
        return false;
    }

    /**
     * Check if can update given user vote.
     * Here we check against local (server) time.
     * @param userVote user vote w
     * @return
     */
    private boolean canAcceptUserVote(VoteEntity userVote) {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < 11;
    }

    /**
     * Create new user vote and write user action item
     * @param restaurant restaurant for which user votes
     * @param user user which votes
     */
    private void createUserVote(RestaurantEntity restaurant, AppUserEntity user) {
        VoteEntity v = new VoteEntity();
        v.setRestaurant(restaurant);
        v.setUser(user);
        v.setCreateDate(new Date());
        VoteEntity res = voteRepository.saveAndFlush(v);
        userActionService.log(
                IUserActionService.EntityType.VOTE,
                user.getUsername(),
                IUserActionService.UserAction.CREATE,
                Long.toString(res.getId())
        );
    }

    @Transactional
    @Override
    public RestaurantVotesDTO getTodayVotes(long restaurantId) {
        RestaurantEntity restaurant = restaurantRepository.getOne(restaurantId);
        if (restaurant.isDeleted()) {
            throw new EntityDeletedException("Restaurant is deleted, id=" + restaurantId);
        }
        List<VoteEntity> votes = voteRepository.getTodayVotes(restaurantId);
        return createRestaurantVotesDTO(restaurant, votes);
    }

    @Transactional
    @Override
    public List<RestaurantVotesDTO> getAllTodayVotes() {
        List<VoteEntity> votes = voteRepository.getTodayVotes();
        Map<RestaurantEntity, List<VoteEntity>> mapByRestaurantId = votes.stream()
                .collect(Collectors.groupingBy(x -> x.getRestaurant()));
        List<RestaurantVotesDTO> list = mapByRestaurantId.entrySet()
                .stream()
                .map(x -> createRestaurantVotesDTO(x.getKey(), x.getValue()))
                .collect(Collectors.toList());
        return list;
    }

    @Transactional
    @Override
    public List<RestaurantVotesDTO> getTodayWinners() {
        List<RestaurantVotesDTO> todayVotes = getAllTodayVotes();
        return getFirstTopValues(todayVotes);
    }

    /**
     * Take restaurant(s) with most votes
     * @param todayVotes
     * @return
     */
    List<RestaurantVotesDTO> getFirstTopValues(List<RestaurantVotesDTO> todayVotes) {
        List<RestaurantVotesDTO> result = new ArrayList<>();
        // we are using AtomicReference because we need to use it inside lambda in forEach below
        // simple int will not match because it is requred to be final,
        // while we need to change its value
        final AtomicReference<Integer> maxVotes = new AtomicReference<>(Integer.MIN_VALUE);
        todayVotes.forEach(x -> {
            if (x.getUsernames() != null) {
                if (x.getUsernames().size() == maxVotes.get()) {
                    result.add(x);
                } else if (x.getUsernames().size() > maxVotes.get()) {
                    result.clear();
                    result.add(x);
                    maxVotes.set(x.getUsernames().size());
                }
            }
        });
        return result;
    }

    RestaurantVotesDTO createRestaurantVotesDTO(RestaurantEntity restaurant, List<VoteEntity> votes) {
        List<String> usernames = votes.stream()
                .map(x -> x.getUser().getUsername())
                .collect(Collectors.toList());
        RestaurantVotesDTO a = new RestaurantVotesDTO();
        a.setRestaurant(restaurantConverter.convert(restaurant));
        a.setUsernames(usernames);
        return a;
    }
}
