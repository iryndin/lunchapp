package net.iryndin.lunchapp.service;

import net.iryndin.lunchapp.model.RestaurantDTO;

import java.util.List;

/**
 * CRUD methods for Restaurant entity
 */
public interface IRestaurantService {

    /**
     * List all restaurants
     * @return
     */
    List<RestaurantDTO> listAll();

    /**
     * Get one single restaurant
     * @param id restaurant ID
     * @return restaurant entity
     */
    RestaurantDTO get(long id);


    /**
     * Delete restaurant
     * @param username username of the user who is deleting this particular restaurant
     * @param id restaurant ID
     */
    void delete(String username, long id);

    /**
     * Edit restaurant data
     * @param username username of the user who is editing this particular restaurant
     * @param restaurantId restaurant ID
     * @param newName new name for the restaurant
     * @return updated restaurant data
     */
    RestaurantDTO edit(String username, Long restaurantId, String newName);

    /**
     * Create new restaurant
     * @param username username of the user who is creating this particular restaurant
     * @param name name for the restaurant
     * @return created restaurant data
     */
    RestaurantDTO create(String username, String name);
}
