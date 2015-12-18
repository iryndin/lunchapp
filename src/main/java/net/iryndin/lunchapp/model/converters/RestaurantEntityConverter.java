package net.iryndin.lunchapp.model.converters;

import net.iryndin.lunchapp.datastore.entity.RestaurantEntity;
import net.iryndin.lunchapp.model.RestaurantDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RestaurantEntityConverter implements Converter<RestaurantEntity, RestaurantDTO> {

    @Override
    public RestaurantDTO convert(RestaurantEntity restaurantEntity) {
        if (restaurantEntity != null) {
            return new RestaurantDTO(restaurantEntity.getId(), restaurantEntity.getName());
        }
        return null;
    }
}
