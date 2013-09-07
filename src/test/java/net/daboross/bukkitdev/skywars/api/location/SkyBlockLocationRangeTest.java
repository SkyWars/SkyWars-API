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

import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dabo Ross <http://www.daboross.net/>
 */
public class SkyBlockLocationRangeTest {

    @Test
    public void testEquals() {
        SkyBlockLocation loc1 = getRandomPositiveLoc();
        SkyBlockLocationRange range = new SkyBlockLocationRange(loc1, loc1.add(getRandomPositiveLoc()), "testWorld");
        SkyBlockLocationRange range1 = new SkyBlockLocationRange(range.min, range.max, range.world);
        assertEquals(range, range1);
    }

    @Test
    public void testRenameWorld() {
        SkyBlockLocation loc1 = getRandomPositiveLoc().changeWorld("blah");
        SkyBlockLocationRange range = new SkyBlockLocationRange(loc1, loc1.add(getRandomPositiveLoc()), "testWorld");
        assertEquals("testWorld", range.min.world);
        assertEquals("testWorld", range.max.world);
    }

    @Test
    public void testSerializeDeserializeConfigurationSection() {
        SkyBlockLocation min = getRandomPositiveLoc();
        SkyBlockLocationRange original = new SkyBlockLocationRange(min, min.add(getRandomPositiveLoc()), "whatWorld");
        YamlConfiguration serializationMedium = new YamlConfiguration();
        original.serialize(serializationMedium);
        SkyBlockLocationRange deserialized = SkyBlockLocationRange.deserialize(serializationMedium);
        assertEquals(original, deserialized);
    }

    public static SkyBlockLocationRange getRandom() {
        SkyBlockLocation min = getRandomPositiveLoc();
        return new SkyBlockLocationRange(min, min.add(getRandomPositiveLoc()), "randomWorld");
    }

    public static SkyBlockLocation getRandomPositiveLoc() {
        int x = (int) (Math.random() * 20);
        int y = (int) (Math.random() * 20);
        int z = (int) (Math.random() * 20);
        String world = "worldTest";
        return new SkyBlockLocation(x, y, z, world);
    }
}
