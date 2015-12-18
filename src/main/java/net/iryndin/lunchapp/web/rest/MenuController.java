package net.iryndin.lunchapp.web.rest;

import com.google.common.base.Optional;
import net.iryndin.lunchapp.AppConstants;
import net.iryndin.lunchapp.model.MenuItemDTO;
import net.iryndin.lunchapp.service.IMenuItemService;
import net.iryndin.lunchapp.web.AuthUtils;
import net.iryndin.lunchapp.web.model.ApiResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController
public class MenuController {

    @Autowired
    IMenuItemService menuItemService;
    @Autowired
    AuthUtils authUtils;

    @RequestMapping(value = "/menu/{restaurantId}", method = RequestMethod.GET)
    public ApiResponseDTO<List<MenuItemDTO>> listAll(@PathVariable("restaurantId") Long restaurantId) {
        List<MenuItemDTO> list = menuItemService.listAll(restaurantId);
        return new ApiResponseDTO<>(list);
    }

    @RequestMapping(
            value = "/menu/{restaurantId}",
            method = RequestMethod.PUT,
            consumes = "application/x-www-form-urlencoded")
    public ApiResponseDTO<MenuItemDTO> create(
            @RequestHeader(AppConstants.HEADER_USERNAME) String username,
            @RequestHeader(AppConstants.HEADER_KEY) String authKey,
            @PathVariable("restaurantId") Long restaurantId,
            @RequestParam("name") String name,
            @RequestParam("price") double price) {
        authUtils.validateAdminUser(username, authKey);
        MenuItemDTO m = menuItemService.create(username, restaurantId, name, price);
        return new ApiResponseDTO<>(m);
    }

    @RequestMapping(
            value = "/menu/{menuItemId}",
            method = RequestMethod.POST,
            consumes = "application/x-www-form-urlencoded")
    public ApiResponseDTO<MenuItemDTO> edit(
            @RequestHeader(AppConstants.HEADER_USERNAME) String username,
            @RequestHeader(AppConstants.HEADER_KEY) String authKey,
            @PathVariable("menuItemId") Long menuItemId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "price", required = false) Double price
    ) {
        authUtils.validateAdminUser(username, authKey);
        MenuItemDTO m = menuItemService.edit(username, menuItemId, Optional.fromNullable(name), Optional.fromNullable(price));
        return new ApiResponseDTO<>(m);
    }


    @RequestMapping(value = "/menu/{menuItemId}", method = RequestMethod.DELETE)
    public ApiResponseDTO<Boolean> delete(
            @RequestHeader(AppConstants.HEADER_USERNAME) String username,
            @RequestHeader(AppConstants.HEADER_KEY) String authKey,
            @PathVariable("menuItemId") Long menuItemId
    ) {
        authUtils.validateAdminUser(username, authKey);
        menuItemService.delete(username, menuItemId);
        return new ApiResponseDTO<>(true);
    }
}
