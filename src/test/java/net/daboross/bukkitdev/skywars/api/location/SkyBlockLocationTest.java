/*
 * Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.skywars.api.location;

import java.util.Map;
import java.util.Random;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Dabo Ross <http://www.daboross.net/>
 */
public class SkyBlockLocationTest {

    private Random r;

    @Before
    public void setup() {
        r = new Random();
    }

    @Test
    public void testEquals() {
        SkyBlockLocation loc1 = getRandomLoc(r);
        SkyBlockLocation loc2 = new SkyBlockLocation(loc1.x, loc1.y, loc1.z, loc1.world);
        SkyBlockLocation loc3 = loc1.changeWorld(null);
        assertEquals(loc1, loc2);
        assertNotEquals(loc1, loc3);
    }

    @Test
    public void testSerializeDeserializeMap() {
        SkyBlockLocation original = getRandomLoc(r);
        Map<String, Object> serialized = original.serialize();
        SkyBlockLocation deserialized = SkyBlockLocation.deserialize(serialized);
        assertEquals(original, deserialized);
    }

    @Test
    public void testSerializeDeserializeConfigurationSection() {
        SkyBlockLocation original = getRandomLoc(r);
        YamlConfiguration serializationMedium = new YamlConfiguration();
        original.serialize(serializationMedium);
        SkyBlockLocation deserialized = SkyBlockLocation.deserialize(serializationMedium);
        assertEquals(original, deserialized);
    }

    private SkyBlockLocation getRandomLoc(Random r) {
        int x = 100 - r.nextInt(200);
        int y = 100 - r.nextInt(200);
        int z = 100 - r.nextInt(200);
        String world = "worldTest";
        return new SkyBlockLocation(x, y, z, world);
    }
}
