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
     * @param restaurantId restaurant ID
     * @return list of menu items for this restaurant
     */
    List<MenuItemDTO> listAll(long restaurantId);

    /**
     * Create menu item for restaurant
     *
     * @param username username of the user who is creating this particular menu item
     * @param restaurantId restaurant ID
     * @param name menu item name
     * @param price menu item price
     * @return created menu item data
     */
    MenuItemDTO create(String username, long restaurantId, String name, double price);

    /**
     * Edit menu item for restaurant
     *
     * @param username username of the user who is editing this particular menu item
     * @param menuId menu item ID
     * @param name menu item name
     * @param price menu item price
     * @return updated menu item data
     */
    MenuItemDTO edit(String username, long menuId, Optional<String> name, Optional<Double> price);

    /**
     * Delete menu item
     *
     * @param username username of the user who is deleting this particular menu item
     * @param menuId menu item ID
     */
    void delete(String username, long menuId);
}
