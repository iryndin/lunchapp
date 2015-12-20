package net.iryndin.lunchapp.service.impl;

import com.google.common.base.Optional;
import net.iryndin.lunchapp.datastore.dao.MenuItemRepository;
import net.iryndin.lunchapp.datastore.dao.RestaurantRepository;
import net.iryndin.lunchapp.datastore.entity.MenuItemEntity;
import net.iryndin.lunchapp.datastore.entity.RestaurantEntity;
import net.iryndin.lunchapp.error.EntityDeletedException;
import net.iryndin.lunchapp.model.MenuItemDTO;
import net.iryndin.lunchapp.model.converters.MenuItemEntityConverter;
import net.iryndin.lunchapp.service.IUserActionService;
import org.junit.Test;
import org.mockito.Matchers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for MenuItemServiceImpl
 */
public class MenuItemServiceImplTest {

    @Test(expected = EntityDeletedException.class)
    public void testListAllForDeletedRestaurant() {
        final RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setDeleted(true);
        RestaurantRepository restRep = mock(RestaurantRepository.class);
        when(restRep.getOne(Matchers.anyLong())).thenReturn(restaurant);
        MenuItemServiceImpl menuItemService = new MenuItemServiceImpl();
        menuItemService.restaurantRepository = restRep;
        menuItemService.listAll(11);
    }

    @Test
    public void testListAllNormal() {
        final RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(111L);
        MenuItemServiceImpl menuItemService = new MenuItemServiceImpl();
        menuItemService.restaurantRepository = mock(RestaurantRepository.class);
        when(menuItemService.restaurantRepository.getOne(Matchers.anyLong())).thenReturn(restaurant);
        menuItemService.converter = new MenuItemEntityConverter();
        List<MenuItemEntity> list = Arrays.asList(
                new MenuItemEntity(1L, "a", 12.34, false, restaurant, new Date(), new Date()),
                new MenuItemEntity(2L, "b", 12.35, false, restaurant, new Date(), new Date()),
                new MenuItemEntity(3L, "c", 12.36, false, restaurant, new Date(), new Date()),
                new MenuItemEntity(4L, "d", 12.37, false, restaurant, new Date(), new Date())
        );
        menuItemService.menuItemRepository = mock(MenuItemRepository.class);
        when(menuItemService.menuItemRepository.findAllNotDeleted(Matchers.anyLong())).thenReturn(list);
        List<MenuItemDTO> result = menuItemService.listAll(111L);
        assertEquals(list.size(), result.size());
        for (int i=0; i<list.size(); i++) {
            MenuItemEntity e = list.get(i);
            MenuItemDTO dto = result.get(i);
            assertEquals(e.getId(), dto.getId());
            assertEquals(e.getName(), dto.getName());
            assertEquals(e.getPrice(), dto.getPrice(), 1e-10);
            assertEquals(restaurant.getId(), dto.getRestaurantId());
        }
    }

    @Test(expected = EntityDeletedException.class)
    public void testCreateForDeletedRestaurant() {
        final RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setDeleted(true);
        RestaurantRepository restRep = mock(RestaurantRepository.class);
        when(restRep.getOne(Matchers.anyLong())).thenReturn(restaurant);
        MenuItemServiceImpl menuItemService = new MenuItemServiceImpl();
        menuItemService.restaurantRepository = restRep;
        menuItemService.create("user1", 11L, "chips", 12.34);
    }

    @Test
    public void testCreateNormal() {
        final RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(111L);
        MenuItemServiceImpl menuItemService = new MenuItemServiceImpl();
        menuItemService.restaurantRepository = mock(RestaurantRepository.class);
        when(menuItemService.restaurantRepository.getOne(Matchers.anyLong())).thenReturn(restaurant);
        menuItemService.converter = new MenuItemEntityConverter();
        menuItemService.menuItemRepository = mock(MenuItemRepository.class);
        String name = "chips";
        double price = 12.34;
        MenuItemEntity a = new MenuItemEntity();
        a.setId(111111L);
        a.setRestaurant(restaurant);
        a.setName(name);
        a.setPrice(price);
        a.setCreateDate(new Date());
        a.setUpdateDate(a.getCreateDate());
        when(menuItemService.menuItemRepository.saveAndFlush(Matchers.any(MenuItemEntity.class))).thenReturn(a);
        menuItemService.userActionService = mock(IUserActionService.class);
        MenuItemDTO dto = menuItemService.create("user1", restaurant.getId(), name, price);
        assertEquals(name, dto.getName());
        assertEquals(price, dto.getPrice(), 1e-10);
        assertEquals(restaurant.getId(), dto.getRestaurantId());
    }

    @Test(expected = EntityDeletedException.class)
    public void testEditForDeletedMenuItem() {
        final MenuItemEntity mi = new MenuItemEntity();
        mi.setDeleted(true);
        MenuItemRepository miRep = mock(MenuItemRepository.class);
        when(miRep.getOne(Matchers.anyLong())).thenReturn(mi);
        MenuItemServiceImpl menuItemService = new MenuItemServiceImpl();
        menuItemService.menuItemRepository = miRep;
        menuItemService.edit("user1", 11L, Optional.<String>absent(), Optional.of(111.2222));
    }

    @Test
    public void testEditNormal() {
        final MenuItemEntity a = new MenuItemEntity();
        a.setId(111111L);
        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(3333L);
        a.setRestaurant(restaurant);
        a.setName("chips");
        a.setPrice(12.34);
        a.setCreateDate(new Date());
        a.setUpdateDate(a.getCreateDate());
        MenuItemRepository miRep = mock(MenuItemRepository.class);
        when(miRep.getOne(Matchers.anyLong())).thenReturn(a);
        MenuItemServiceImpl menuItemService = new MenuItemServiceImpl();
        menuItemService.menuItemRepository = miRep;
        menuItemService.userActionService = mock(IUserActionService.class);
        menuItemService.converter = new MenuItemEntityConverter();
        String newName = "burger";
        double newPrice = 333.444;
        MenuItemDTO dto = menuItemService.edit("user1", 11L, Optional.of(newName), Optional.of(newPrice));
        assertEquals(newName, dto.getName());
        assertEquals(newPrice, dto.getPrice(), 1e-10);
    }

    @Test(expected = EntityDeletedException.class)
    public void testDeleteForDeletedMenuItem() {
        final MenuItemEntity mi = new MenuItemEntity();
        mi.setDeleted(true);
        MenuItemRepository miRep = mock(MenuItemRepository.class);
        when(miRep.getOne(Matchers.anyLong())).thenReturn(mi);
        MenuItemServiceImpl menuItemService = new MenuItemServiceImpl();
        menuItemService.menuItemRepository = miRep;
        menuItemService.delete("user1", 11L);
    }
}
