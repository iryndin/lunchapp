package net.iryndin.lunchapp.service;

import com.google.common.base.Optional;
import net.iryndin.lunchapp.model.MenuItemDTO;

import java.util.List;

/**
 * Management of menu items
 */
public interface IMenuItemService {
    /**
     * List all menu items for restaurant
     * @param restaurantId
     * @return
     */
    List<MenuItemDTO> listAll(long restaurantId);

    /**
     * Create menu item for restaurant
     *
     * @param username
     * @param restaurantId
     * @param name
     * @param price
     * @return
     */
    MenuItemDTO create(String username, long restaurantId, String name, double price);

    /**
     * Edit menu item for restaurant
     *
     * @param username
     * @param menuId
     * @param name
     * @param price
     * @return
     */
    MenuItemDTO edit(String username, long menuId, Optional<String> name, Optional<Double> price);

    /**
     * Delete menu item
     *
     * @param username
     * @param menuId
     */
    void delete(String username, long menuId);
}
