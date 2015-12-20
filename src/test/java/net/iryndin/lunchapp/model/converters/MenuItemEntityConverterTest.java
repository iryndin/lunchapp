package net.iryndin.lunchapp.model.converters;

import net.iryndin.lunchapp.datastore.entity.MenuItemEntity;
import net.iryndin.lunchapp.datastore.entity.RestaurantEntity;
import net.iryndin.lunchapp.model.MenuItemDTO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit tests for MenuItemEntityConverter
 */
public class MenuItemEntityConverterTest {

    @Test
    public void testConvertNull() {
        MenuItemEntityConverter c = new MenuItemEntityConverter();
        assertNull(c.convert(null));
    }

    @Test
    public void testConvertNormal()  {
        MenuItemEntity e = new MenuItemEntity();
        e.setId(11L);
        e.setPrice(32323.2323);
        e.setName("test name");
        RestaurantEntity r = new RestaurantEntity();
        r.setId(3333L);
        e.setRestaurant(r);
        MenuItemEntityConverter c = new MenuItemEntityConverter();
        MenuItemDTO m = c.convert(e);
        assertEquals(e.getId(), m.getId());
        assertEquals(e.getPrice(), m.getPrice(), 1e-10);
        assertEquals(e.getName(), m.getName());
        assertEquals(r.getId(), m.getRestaurantId());
    }
}
