package net.iryndin.lunchapp.web.rest;

import net.iryndin.lunchapp.AppConstants;
import net.iryndin.lunchapp.error.CannotDeleteEntityException;
import net.iryndin.lunchapp.service.IRestaurantService;
import net.iryndin.lunchapp.web.AuthUtils;
import net.iryndin.lunchapp.web.model.ApiResponseDTO;
import net.iryndin.lunchapp.model.RestaurantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Restaurant management
 */
@RestController
public class RestaurantController {

    @Autowired
    IRestaurantService restaurantService;
    @Autowired
    AuthUtils authUtils;

    /**
     * List all restaurants
     * @return
     */
    @RequestMapping(value = "/restaurant/list", method = RequestMethod.GET)
    public ApiResponseDTO<List<RestaurantDTO>> listAll() {
        List<RestaurantDTO> list = restaurantService.listAll();
        return new ApiResponseDTO<>(list);
    }

    /**
     * Get certain restaurant data
     * @param restaurantId restaurant ID
     * @return restaurant entity
     */
    @RequestMapping(value = "/restaurant/{id}", method = RequestMethod.GET)
    public ApiResponseDTO<RestaurantDTO> get(@PathVariable("id") Long restaurantId) {
        RestaurantDTO r = restaurantService.get(restaurantId);
        return new ApiResponseDTO<>(r);
    }

    /**
     * Edit restaurant
     * @param username username of user who is editing the restaurant
     * @param authKey authentication key for the user
     * @param restaurantId restaurant ID
     * @param newName new name of the restaurant
     * @return updated restaurant entity
     */
    @RequestMapping(
            value = "/restaurant/{id}",
            method = RequestMethod.POST,
            consumes = "application/x-www-form-urlencoded")
    public ApiResponseDTO<RestaurantDTO> edit(
            @RequestHeader(AppConstants.HEADER_USERNAME) String username,
            @RequestHeader(AppConstants.HEADER_KEY) String authKey,
            @PathVariable("id") Long restaurantId,
            @RequestParam("name") String newName) {
        authUtils.validateAdminUser(username, authKey);
        RestaurantDTO r = restaurantService.edit(username, restaurantId, newName);
        return new ApiResponseDTO<>(r);
    }

    /**
     * Create new restaurant
     * @param username username of user who is editing the restaurant
     * @param authKey authentication key for the user
     * @param name name of the restaurant
     * @return created restaurant entity
     */
    @RequestMapping(
            value = "/restaurant",
            method = RequestMethod.PUT,
            consumes = "application/x-www-form-urlencoded")
    public ApiResponseDTO<RestaurantDTO> create(
            @RequestHeader(AppConstants.HEADER_USERNAME) String username,
            @RequestHeader(AppConstants.HEADER_KEY) String authKey,
            @RequestParam("name") String name
    ) {
        authUtils.validateAdminUser(username, authKey);
        RestaurantDTO r = restaurantService.create(username, name);
        return new ApiResponseDTO<>(r);
    }

    /**
     * Delete restaurant by ID
     * @param username username of user who is editing the restaurant
     * @param authKey authentication key for the user
     * @param restaurantId restaurant ID
     * @return true if restaurant was deleted successfully, false otherwise
     */
    @RequestMapping(value = "/restaurant/{id}", method = RequestMethod.DELETE)
    public ApiResponseDTO<Boolean> delete(
            @RequestHeader(AppConstants.HEADER_USERNAME) String username,
            @RequestHeader(AppConstants.HEADER_KEY) String authKey,
            @PathVariable("id") Long restaurantId
    ) {
        try {
            authUtils.validateAdminUser(username, authKey);
            restaurantService.delete(username, restaurantId);
            return new ApiResponseDTO<>(true);
        } catch (CannotDeleteEntityException cdee) {
            return new ApiResponseDTO<>(false);
        }
    }
}
