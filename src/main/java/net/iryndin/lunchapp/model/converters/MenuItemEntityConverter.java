package net.iryndin.lunchapp.model.converters;

import net.iryndin.lunchapp.datastore.entity.MenuItemEntity;
import net.iryndin.lunchapp.model.MenuItemDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MenuItemEntityConverter implements Converter<MenuItemEntity, MenuItemDTO> {

    @Override
    public MenuItemDTO convert(MenuItemEntity e) {
        if (e != null) {
            return new MenuItemDTO(e.getId(), e.getRestaurant().getId(), e.getName(), e.getPrice());
        }
        return null;
    }
}
