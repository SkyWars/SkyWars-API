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

import java.util.Random;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Dabo Ross <http://www.daboross.net/>
 */
public class SkyBlockLocationRangeTest {

    private Random r;

    @Before
    public void setup() {
        r = new Random();
    }

    @Test
    public void testEquals() {
        SkyBlockLocationRange range = getRandom(r);
        SkyBlockLocationRange range1 = new SkyBlockLocationRange(range.min, range.max, range.world);
        assertEquals(range, range1);
    }

    @Test
    public void testRenameWorld() {
        SkyBlockLocation loc1 = getRandomPositiveLoc(r).changeWorld("blah");
        SkyBlockLocationRange range = new SkyBlockLocationRange(loc1, loc1.add(getRandomPositiveLoc(r)), "testWorld");
        assertEquals("testWorld", range.min.world);
        assertEquals("testWorld", range.max.world);
    }

    @Test
    public void testSerializeDeserializeConfigurationSection() {
        SkyBlockLocationRange original = getRandom(r);
        YamlConfiguration serializationMedium = new YamlConfiguration();
        original.serialize(serializationMedium);
        SkyBlockLocationRange deserialized = SkyBlockLocationRange.deserialize(serializationMedium);
        assertEquals(original, deserialized);
    }

    public static SkyBlockLocationRange getRandom(Random r) {
        SkyBlockLocation min = getRandomPositiveLoc(r);
        return new SkyBlockLocationRange(min, min.add(getRandomPositiveLoc(r)), "randomWorld");
    }

    public static SkyBlockLocation getRandomPositiveLoc(Random r) {
        int x = r.nextInt(200);
        int y = r.nextInt(200);
        int z = r.nextInt(200);
        String world = "worldTest";
        return new SkyBlockLocation(x, y, z, world);
    }
}
