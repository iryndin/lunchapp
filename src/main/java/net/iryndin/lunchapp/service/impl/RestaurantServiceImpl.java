package net.iryndin.lunchapp.service.impl;

import net.iryndin.lunchapp.datastore.dao.RestaurantRepository;
import net.iryndin.lunchapp.datastore.entity.RestaurantEntity;
import net.iryndin.lunchapp.error.EntityDeletedException;
import net.iryndin.lunchapp.service.IRestaurantService;
import net.iryndin.lunchapp.model.RestaurantDTO;
import net.iryndin.lunchapp.service.IUserActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of IRestaurantService
 */
@Service
public class RestaurantServiceImpl implements IRestaurantService {

    @Autowired
    Converter<RestaurantEntity, RestaurantDTO> converter;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    IUserActionService userActionService;

    @Transactional
    @Override
    public List<RestaurantDTO> listAll() {
        List<RestaurantEntity> list = restaurantRepository.findAllNotDeleted();
        List<RestaurantDTO> result = list.stream()
                .map(x -> converter.convert(x))
                .collect(Collectors.toList());
        return result;
    }

    @Transactional
    @Override
    public RestaurantDTO get(long restaurantId) {
        RestaurantEntity a = restaurantRepository.getOne(restaurantId);
        if (a.isDeleted()) {
            throw new EntityDeletedException("Restaurant is deleted, id=" + restaurantId);
        }
        return converter.convert(a);
    }

    @Transactional
    @Override
    public void delete(String username, long restaurantId) {
        RestaurantEntity a = restaurantRepository.getOne(restaurantId);
        if (a.isDeleted()) {
            throw new EntityDeletedException("Restaurant is deleted, id=" + restaurantId);
        }
        a.setDeleted(true);
        a.setUpdateDate(new Date());
        restaurantRepository.saveAndFlush(a);
        userActionService.log(
                IUserActionService.EntityType.RESTAURANT,
                username,
                IUserActionService.UserAction.DELETE,
                Long.toString(restaurantId)
                );
    }

    @Transactional
    @Override
    public RestaurantDTO edit(String username, Long restaurantId, String newName) {
        RestaurantEntity a = restaurantRepository.getOne(restaurantId);
        if (a.isDeleted()) {
            throw new EntityDeletedException("Restaurant is deleted, id=" + restaurantId);
        }
        a.setName(newName);
        a.setUpdateDate(new Date());
        restaurantRepository.saveAndFlush(a);
        userActionService.log(
                IUserActionService.EntityType.RESTAURANT,
                username,
                IUserActionService.UserAction.UPDATE,
                Long.toString(restaurantId)
        );
        return converter.convert(a);
    }

    @Transactional
    @Override
    public RestaurantDTO create(String username, String name) {
        RestaurantEntity a = new RestaurantEntity();
        a.setName(name);
        a.setCreateDate(new Date());
        a.setUpdateDate(a.getCreateDate());
        RestaurantEntity res = restaurantRepository.saveAndFlush(a);
        userActionService.log(
                IUserActionService.EntityType.RESTAURANT,
                username,
                IUserActionService.UserAction.CREATE,
                Long.toString(res.getId())
        );
        return converter.convert(res);
    }
}
