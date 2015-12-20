package net.iryndin.lunchapp.model.converters;

import net.iryndin.lunchapp.datastore.entity.RestaurantEntity;
import net.iryndin.lunchapp.model.RestaurantDTO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit tests for RestaurantEntityConverter
 */
public class RestaurantEntityConverterTest {

    @Test
    public void testConvertNull() {
        RestaurantEntityConverter c = new RestaurantEntityConverter();
        assertNull(c.convert(null));
    }

    @Test
    public void testConvertNormal()  {
        RestaurantEntity e = new RestaurantEntity();
        e.setId(11L);
        e.setName("test name");
        RestaurantEntityConverter c = new RestaurantEntityConverter();
        RestaurantDTO m = c.convert(e);
        assertEquals(e.getId(), m.getId());
        assertEquals(e.getName(), m.getName());
    }
}
