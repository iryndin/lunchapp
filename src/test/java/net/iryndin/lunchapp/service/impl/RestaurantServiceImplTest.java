package net.iryndin.lunchapp.service.impl;

import net.iryndin.lunchapp.datastore.dao.RestaurantRepository;
import net.iryndin.lunchapp.datastore.entity.RestaurantEntity;
import net.iryndin.lunchapp.error.EntityDeletedException;
import net.iryndin.lunchapp.model.RestaurantDTO;
import net.iryndin.lunchapp.model.converters.RestaurantEntityConverter;
import net.iryndin.lunchapp.service.IUserActionService;
import org.junit.Test;
import org.mockito.Matchers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for RestaurantServiceImpl
 */
public class RestaurantServiceImplTest {

    @Test
    public void testListAllNormal() {
        RestaurantServiceImpl restaurantService = new RestaurantServiceImpl();
        restaurantService.restaurantRepository = mock(RestaurantRepository.class);
        restaurantService.converter = new RestaurantEntityConverter();
        List<RestaurantEntity> list = Arrays.asList(
                new RestaurantEntity(1L, "a", false, new Date(), new Date()),
                new RestaurantEntity(2L, "b", false, new Date(), new Date()),
                new RestaurantEntity(3L, "c", false, new Date(), new Date()),
                new RestaurantEntity(4L, "d", false, new Date(), new Date())
        );
        when(restaurantService.restaurantRepository.findAllNotDeleted()).thenReturn(list);
        List<RestaurantDTO> result = restaurantService.listAll();
        assertEquals(list.size(), result.size());
        for (int i=0; i<list.size(); i++) {
            RestaurantEntity e = list.get(i);
            RestaurantDTO dto = result.get(i);
            assertEquals(e.getId(), dto.getId());
            assertEquals(e.getName(), dto.getName());
        }
    }

    @Test(expected = EntityDeletedException.class)
    public void testGetForDeletedRestaurant() {
        final RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setDeleted(true);
        RestaurantServiceImpl restaurantService = new RestaurantServiceImpl();
        restaurantService.restaurantRepository = mock(RestaurantRepository.class);
        when(restaurantService.restaurantRepository.getOne(Matchers.anyLong())).thenReturn(restaurant);
        restaurantService.get(11L);
    }

    @Test
    public void testGetNormal() {
        final RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(22L);
        restaurant.setName("aaa");
        RestaurantServiceImpl restaurantService = new RestaurantServiceImpl();
        restaurantService.restaurantRepository = mock(RestaurantRepository.class);
        when(restaurantService.restaurantRepository.getOne(Matchers.anyLong())).thenReturn(restaurant);
        restaurantService.converter = new RestaurantEntityConverter();
        RestaurantDTO dto = restaurantService.get(11L);
        assertEquals(restaurant.getId(), dto.getId());
        assertEquals(restaurant.getName(), dto.getName());
    }

    @Test(expected = EntityDeletedException.class)
    public void testDeleteForDeletedRestaurant() {
        final RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setDeleted(true);
        RestaurantServiceImpl restaurantService = new RestaurantServiceImpl();
        restaurantService.restaurantRepository = mock(RestaurantRepository.class);
        when(restaurantService.restaurantRepository.getOne(Matchers.anyLong())).thenReturn(restaurant);
        restaurantService.delete("admin1", 11L);
    }

    @Test
    public void testDeleteNormal() {
        final RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(111L);
        RestaurantServiceImpl restaurantService = new RestaurantServiceImpl();
        restaurantService.restaurantRepository = mock(RestaurantRepository.class);
        when(restaurantService.restaurantRepository.getOne(Matchers.anyLong())).thenReturn(restaurant);
        restaurantService.userActionService = mock(IUserActionService.class);
        String username = "admin1";
        restaurantService.delete(username, restaurant.getId());
        verify(restaurantService.userActionService, times(1)).log(
                IUserActionService.EntityType.RESTAURANT,
                username,
                IUserActionService.UserAction.DELETE,
                Long.toString(restaurant.getId())
        );
    }

    @Test(expected = EntityDeletedException.class)
    public void testEditForDeletedRestaurant() {
        final RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setDeleted(true);
        RestaurantServiceImpl restaurantService = new RestaurantServiceImpl();
        restaurantService.restaurantRepository = mock(RestaurantRepository.class);
        when(restaurantService.restaurantRepository.getOne(Matchers.anyLong())).thenReturn(restaurant);
        restaurantService.edit("admin1", 11L, "new restaurant name");
    }

    @Test
    public void testEditNormal() {
        final RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(11L);
        restaurant.setName("aaa");
        RestaurantServiceImpl restaurantService = new RestaurantServiceImpl();
        restaurantService.restaurantRepository = mock(RestaurantRepository.class);
        when(restaurantService.restaurantRepository.getOne(Matchers.anyLong())).thenReturn(restaurant);
        restaurantService.converter = new RestaurantEntityConverter();
        restaurantService.userActionService = mock(IUserActionService.class);
        String newName = "bbb";
        String username = "admin1";
        RestaurantDTO dto = restaurantService.edit(username, restaurant.getId(), newName);
        assertEquals(restaurant.getId(), dto.getId());
        assertEquals(newName, dto.getName());
        verify(restaurantService.userActionService, times(1)).log(
                IUserActionService.EntityType.RESTAURANT,
                username,
                IUserActionService.UserAction.UPDATE,
                Long.toString(restaurant.getId())
        );
    }

    @Test
    public void testCreateNormal() {
        String name = "aaa";
        String username = "admin1";

        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(333L);
        restaurant.setName(name);
        restaurant.setCreateDate(new Date());
        restaurant.setUpdateDate(restaurant.getCreateDate());

        RestaurantServiceImpl restaurantService = new RestaurantServiceImpl();
        restaurantService.restaurantRepository = mock(RestaurantRepository.class);
        when(restaurantService.restaurantRepository.saveAndFlush(Matchers.any(RestaurantEntity.class))).thenReturn(restaurant);
        restaurantService.converter = new RestaurantEntityConverter();
        restaurantService.userActionService = mock(IUserActionService.class);
        RestaurantDTO dto = restaurantService.create(username, name);
        assertEquals(restaurant.getId(), dto.getId());
        assertEquals(name, dto.getName());
        verify(restaurantService.userActionService, times(1)).log(
                IUserActionService.EntityType.RESTAURANT,
                username,
                IUserActionService.UserAction.CREATE,
                Long.toString(restaurant.getId())
        );
    }
}
