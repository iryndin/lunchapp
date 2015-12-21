package net.iryndin.lunchapp.service.impl;

import net.iryndin.lunchapp.datastore.dao.AppUserRepository;
import net.iryndin.lunchapp.datastore.dao.RestaurantRepository;
import net.iryndin.lunchapp.datastore.dao.VoteRepository;
import net.iryndin.lunchapp.datastore.entity.AppUserEntity;
import net.iryndin.lunchapp.datastore.entity.RestaurantEntity;
import net.iryndin.lunchapp.datastore.entity.VoteEntity;
import net.iryndin.lunchapp.error.AuthenticationException;
import net.iryndin.lunchapp.error.EntityDeletedException;
import net.iryndin.lunchapp.model.RestaurantVotesDTO;
import net.iryndin.lunchapp.model.converters.RestaurantEntityConverter;
import net.iryndin.lunchapp.service.IUserActionService;
import org.junit.Test;
import org.mockito.Matchers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Unit tests for VoteServiceImpl
 */
public class VoteServiceImplTest {

    @Test(expected = AuthenticationException.class)
    public void testVoteRestaurantByDeletedUser() {
        VoteServiceImpl voteService = new VoteServiceImpl();
        voteService.appUserRepository = mock(AppUserRepository.class);
        when(voteService.appUserRepository.getByUsername(Matchers.anyString())).thenReturn(null);
        voteService.voteRestaurant("user1", 1L);
    }

    @Test(expected = EntityDeletedException.class)
    public void testVoteRestaurantForDeletedRestaurant() {
        VoteServiceImpl voteService = new VoteServiceImpl();
        voteService.appUserRepository = mock(AppUserRepository.class);
        when(voteService.appUserRepository.getByUsername(Matchers.anyString())).thenReturn(new AppUserEntity());
        voteService.restaurantRepository = mock(RestaurantRepository.class);
        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setDeleted(true);
        when(voteService.restaurantRepository.getOne(Matchers.anyLong())).thenReturn(restaurant);
        voteService.voteRestaurant("user1", 1L);
    }

    @Test
    public void testVoteRestaurantWhenNoTodayVotes() {
        VoteServiceImpl voteService = new VoteServiceImpl();
        voteService.appUserRepository = mock(AppUserRepository.class);
        AppUserEntity user = new AppUserEntity();
        user.setUsername("user1");
        when(voteService.appUserRepository.getByUsername(Matchers.anyString())).thenReturn(user);
        voteService.restaurantRepository = mock(RestaurantRepository.class);
        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(2222L);
        when(voteService.restaurantRepository.getOne(Matchers.anyLong())).thenReturn(restaurant);
        voteService.voteRepository = mock(VoteRepository.class);
        when(voteService.voteRepository.getTodayVoteByUser(Matchers.anyLong())).thenReturn(null);
        VoteEntity ve = new VoteEntity();
        ve.setId(333L);
        when(voteService.voteRepository.saveAndFlush(Matchers.any(VoteEntity.class))).thenReturn(ve);
        voteService.userActionService = mock(IUserActionService.class);
        boolean voteAccepted = voteService.voteRestaurant("user1", 1L);
        assertTrue(voteAccepted);
        verify(voteService.userActionService, times(1)).log(
                IUserActionService.EntityType.VOTE,
                user.getUsername(),
                IUserActionService.UserAction.CREATE,
                Long.toString(ve.getId())
        );
    }

    @Test
    public void testVoteRestaurantWhenTodayVotesExistVoteAccepted() {
        VoteServiceImpl voteService = new VoteServiceImpl() {
            @Override
            boolean canAcceptUserVote(VoteEntity userVote) {
                return true;
            }
        };
        voteService.appUserRepository = mock(AppUserRepository.class);
        AppUserEntity user = new AppUserEntity();
        user.setUsername("user1");
        when(voteService.appUserRepository.getByUsername(Matchers.anyString())).thenReturn(user);
        voteService.restaurantRepository = mock(RestaurantRepository.class);
        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(2222L);
        when(voteService.restaurantRepository.getOne(Matchers.anyLong())).thenReturn(restaurant);
        voteService.voteRepository = mock(VoteRepository.class);
        VoteEntity ve = new VoteEntity();
        ve.setId(333L);
        ve.setRestaurant(restaurant);
        when(voteService.voteRepository.getTodayVoteByUser(Matchers.anyLong())).thenReturn(ve);
        when(voteService.voteRepository.saveAndFlush(Matchers.any(VoteEntity.class))).thenReturn(ve);
        voteService.userActionService = mock(IUserActionService.class);
        boolean voteAccepted = voteService.voteRestaurant("user1", 1L);
        assertTrue(voteAccepted);
        verify(voteService.userActionService, times(1)).log(
                IUserActionService.EntityType.VOTE,
                user.getUsername(),
                IUserActionService.UserAction.UPDATE,
                Long.toString(ve.getId())
        );
    }

    @Test
    public void testVoteRestaurantWhenTodayVotesExistVoteNotAccepted() {
        VoteServiceImpl voteService = new VoteServiceImpl() {
            @Override
            boolean canAcceptUserVote(VoteEntity userVote) {
                return false;
            }
        };
        voteService.appUserRepository = mock(AppUserRepository.class);
        AppUserEntity user = new AppUserEntity();
        user.setUsername("user1");
        when(voteService.appUserRepository.getByUsername(Matchers.anyString())).thenReturn(user);
        voteService.restaurantRepository = mock(RestaurantRepository.class);
        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(2222L);
        when(voteService.restaurantRepository.getOne(Matchers.anyLong())).thenReturn(restaurant);
        voteService.voteRepository = mock(VoteRepository.class);
        VoteEntity ve = new VoteEntity();
        ve.setId(333L);
        ve.setRestaurant(restaurant);
        when(voteService.voteRepository.getTodayVoteByUser(Matchers.anyLong())).thenReturn(ve);
        when(voteService.voteRepository.saveAndFlush(Matchers.any(VoteEntity.class))).thenReturn(ve);
        voteService.userActionService = mock(IUserActionService.class);
        boolean voteAccepted = voteService.voteRestaurant("user1", 1L);
        assertFalse(voteAccepted);
        verify(voteService.userActionService, times(0)).log(
                IUserActionService.EntityType.VOTE,
                user.getUsername(),
                IUserActionService.UserAction.UPDATE,
                Long.toString(ve.getId())
        );
    }

    @Test
    public void testCreateRestaurantVotesDTO() {
        VoteServiceImpl voteService = new VoteServiceImpl();
        voteService.restaurantConverter = new RestaurantEntityConverter();
        RestaurantEntity r = new RestaurantEntity(222L, "kfc", false, new Date(), new Date());

        List<VoteEntity> list = Arrays.asList(
                new VoteEntity(1L, r, new AppUserEntity(2L, "user1", null, null), new Date()),
                new VoteEntity(2L, r, new AppUserEntity(22L, "user2", null, null), new Date()),
                new VoteEntity(3L, r, new AppUserEntity(2222L, "user22", null, null), new Date()),
                new VoteEntity(4L, r, new AppUserEntity(22222L, "user222", null, null), new Date()),
                new VoteEntity(5L, r, new AppUserEntity(222222L, "user2222", null, null), new Date())
        );
        RestaurantVotesDTO dto = voteService.createRestaurantVotesDTO(r, list);
        assertEquals(r.getId(),dto.getRestaurant().getId());
        assertEquals(r.getName(),dto.getRestaurant().getName());
        for (int i=0; i<list.size(); i++) {
            assertEquals(list.get(i).getUser().getUsername(), dto.getUsernames().get(i));
        }
    }

    @Test(expected = EntityDeletedException.class)
    public void testGetTodayVotesForDeletedRestaurant() {
        VoteServiceImpl voteService = new VoteServiceImpl();
        voteService.restaurantRepository = mock(RestaurantRepository.class);
        RestaurantEntity r = new RestaurantEntity(11L, "kfc", true, new Date(), new Date());
        when(voteService.restaurantRepository.getOne(Matchers.anyLong())).thenReturn(r);
        voteService.getTodayVotes(11L);
    }

    @Test
    public void testGetTodayVotesNormal() {
        VoteServiceImpl voteService = new VoteServiceImpl();
        voteService.restaurantConverter = new RestaurantEntityConverter();
        voteService.restaurantRepository = mock(RestaurantRepository.class);
        RestaurantEntity r = new RestaurantEntity(11L, "kfc", false, new Date(), new Date());
        when(voteService.restaurantRepository.getOne(Matchers.anyLong())).thenReturn(r);
        List<VoteEntity> list = Arrays.asList(
                new VoteEntity(1L, r, new AppUserEntity(2L, "user1", null, null), new Date()),
                new VoteEntity(2L, r, new AppUserEntity(22L, "user2", null, null), new Date()),
                new VoteEntity(3L, r, new AppUserEntity(2222L, "user22", null, null), new Date()),
                new VoteEntity(4L, r, new AppUserEntity(22222L, "user222", null, null), new Date()),
                new VoteEntity(5L, r, new AppUserEntity(222222L, "user2222", null, null), new Date())
        );
        voteService.voteRepository = mock(VoteRepository.class);
        when(voteService.voteRepository.getTodayVotes(Matchers.anyLong())).thenReturn(list);
        RestaurantVotesDTO dto = voteService.getTodayVotes(11L);
        assertEquals(r.getId(), dto.getRestaurant().getId());
        assertEquals(r.getName(), dto.getRestaurant().getName());
        for (int i=0; i<list.size(); i++) {
            assertEquals(list.get(i).getUser().getUsername(), dto.getUsernames().get(i));
        }
    }

    @Test
    public void testAllTodayVotesNormal() {
        RestaurantEntity r1 = new RestaurantEntity(11L, "KFC", false, new Date(), new Date());
        RestaurantEntity r2 = new RestaurantEntity(22L, "MacDonalds", false, new Date(), new Date());
        RestaurantEntity r3 = new RestaurantEntity(33L, "TacoBell", false, new Date(), new Date());
        List<VoteEntity> list = Arrays.asList(
                new VoteEntity(1L, r1, new AppUserEntity(2L, "user1", null, null), new Date()),
                new VoteEntity(2L, r1, new AppUserEntity(22L, "user2", null, null), new Date()),
                new VoteEntity(3L, r2, new AppUserEntity(2222L, "user22", null, null), new Date()),
                new VoteEntity(4L, r2, new AppUserEntity(22222L, "user222", null, null), new Date()),
                new VoteEntity(5L, r2, new AppUserEntity(222222L, "user2222", null, null), new Date()),
                new VoteEntity(6L, r3, new AppUserEntity(3L, "user3", null, null), new Date()),
                new VoteEntity(7L, r3, new AppUserEntity(33L, "user33", null, null), new Date()),
                new VoteEntity(8L, r3, new AppUserEntity(333L, "user333", null, null), new Date())
        );
        VoteServiceImpl voteService = new VoteServiceImpl();
        voteService.restaurantConverter = new RestaurantEntityConverter();
        voteService.voteRepository = mock(VoteRepository.class);
        when(voteService.voteRepository.getTodayVotes()).thenReturn(list);
        List<RestaurantVotesDTO> dtolist = voteService.getAllTodayVotes();
        assertEquals(3, dtolist.size());
    }

    @Test
    public void testGetTodayWinnersNormal() {
        RestaurantEntity r1 = new RestaurantEntity(11L, "KFC", false, new Date(), new Date());
        RestaurantEntity r2 = new RestaurantEntity(22L, "MacDonalds", false, new Date(), new Date());
        RestaurantEntity r3 = new RestaurantEntity(33L, "TacoBell", false, new Date(), new Date());
        List<VoteEntity> list = Arrays.asList(
                new VoteEntity(1L, r1, new AppUserEntity(2L, "user1", null, null), new Date()),
                new VoteEntity(2L, r1, new AppUserEntity(22L, "user2", null, null), new Date()),
                new VoteEntity(3L, r2, new AppUserEntity(2222L, "user22", null, null), new Date()),
                new VoteEntity(4L, r2, new AppUserEntity(22222L, "user222", null, null), new Date()),
                new VoteEntity(5L, r2, new AppUserEntity(222222L, "user2222", null, null), new Date()),
                new VoteEntity(6L, r3, new AppUserEntity(3L, "user3", null, null), new Date()),
                new VoteEntity(7L, r3, new AppUserEntity(33L, "user33", null, null), new Date()),
                new VoteEntity(8L, r3, new AppUserEntity(333L, "user333", null, null), new Date()),
                new VoteEntity(9L, r3, new AppUserEntity(3333L, "user3333", null, null), new Date())
        );
        VoteServiceImpl voteService = new VoteServiceImpl();
        voteService.restaurantConverter = new RestaurantEntityConverter();
        voteService.voteRepository = mock(VoteRepository.class);
        when(voteService.voteRepository.getTodayVotes()).thenReturn(list);
        List<RestaurantVotesDTO> dtolist = voteService.getTodayWinners();
        assertEquals(1, dtolist.size());
        RestaurantVotesDTO dto = dtolist.iterator().next();
        assertEquals(r3.getId(), dto.getRestaurant().getId());
        assertEquals(r3.getName(), dto.getRestaurant().getName());
        List<VoteEntity> list2 = list.stream()
                .filter(x -> x.getRestaurant().getId().longValue() == r3.getId()).
                collect(Collectors.toList());
        for (int i=0; i<list2.size(); i++) {
            assertEquals(list2.get(i).getUser().getUsername(), dto.getUsernames().get(i));
        }
    }
}
