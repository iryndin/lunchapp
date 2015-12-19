package net.iryndin.lunchapp.service.impl;

import com.google.common.base.Optional;
import net.iryndin.lunchapp.datastore.dao.MenuItemRepository;
import net.iryndin.lunchapp.datastore.dao.RestaurantRepository;
import net.iryndin.lunchapp.datastore.entity.MenuItemEntity;
import net.iryndin.lunchapp.datastore.entity.RestaurantEntity;
import net.iryndin.lunchapp.error.EntityDeletedException;
import net.iryndin.lunchapp.model.MenuItemDTO;
import net.iryndin.lunchapp.model.converters.MenuItemEntityConverter;
import net.iryndin.lunchapp.service.IMenuItemService;
import net.iryndin.lunchapp.service.IUserActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * IMenuItemService implementation
 */
@Service
public class MenuItemServiceImpl implements IMenuItemService {

    @Autowired
    MenuItemRepository menuItemRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    IUserActionService userActionService;
    @Autowired
    MenuItemEntityConverter converter;

    @Transactional
    @Override
    public List<MenuItemDTO> listAll(long restaurantId) {
        RestaurantEntity restaurant = restaurantRepository.getOne(restaurantId);
        if (restaurant.isDeleted()) {
            throw new EntityDeletedException("Restaurant is deleted, id=" + restaurantId);
        }
        List<MenuItemEntity> list = menuItemRepository.findAllNotDeleted(restaurantId);
        List<MenuItemDTO> result = list.stream()
                .map(x -> converter.convert(x))
                .collect(Collectors.toList());
        return result;
    }

    @Transactional
    @Override
    public MenuItemDTO create(String username, long restaurantId, String name, double price) {
        MenuItemEntity a = new MenuItemEntity();
        RestaurantEntity restaurant = restaurantRepository.getOne(restaurantId);
        if (restaurant.isDeleted()) {
            throw new EntityDeletedException("Restaurant is deleted, id=" + restaurantId);
        }
        a.setRestaurant(restaurant);
        a.setName(name);
        a.setPrice(price);
        a.setCreateDate(new Date());
        a.setUpdateDate(a.getCreateDate());
        MenuItemEntity b = menuItemRepository.saveAndFlush(a);
        userActionService.log(
                IUserActionService.EntityType.MENUITEM,
                username,
                IUserActionService.UserAction.CREATE,
                Long.toString(b.getId())
        );
        return converter.convert(b);
    }

    @Transactional
    @Override
    public MenuItemDTO edit(String username, long menuId, Optional<String> name, Optional<Double> price) {
        MenuItemEntity a = menuItemRepository.getOne(menuId);
        if (a.isDeleted()) {
            throw new EntityDeletedException("MenuItem is deleted, id=" + menuId);
        }
        if (name.isPresent()) {
            a.setName(name.get());
        }
        if (price.isPresent()) {
            a.setPrice(price.get());
        }
        a.setUpdateDate(new Date());
        menuItemRepository.saveAndFlush(a);
        userActionService.log(
                IUserActionService.EntityType.MENUITEM,
                username,
                IUserActionService.UserAction.UPDATE,
                Long.toString(menuId)
        );
        return converter.convert(a);
    }

    @Transactional
    @Override
    public void delete(String username, long menuId) {
        MenuItemEntity a = menuItemRepository.getOne(menuId);
        if (a.isDeleted()) {
            throw new EntityDeletedException("MenuItem is deleted, id=" + menuId);
        }
        a.setDeleted(true);
        a.setUpdateDate(new Date());
        menuItemRepository.saveAndFlush(a);
        userActionService.log(
                IUserActionService.EntityType.MENUITEM,
                username,
                IUserActionService.UserAction.DELETE,
                Long.toString(menuId)
        );
    }
}
