/*
 * Copyright (C) 2013-2016 Dabo Ross <http://www.daboross.net/>
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
package net.daboross.bukkitdev.skywars.api.arenaconfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocation;
import net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocationTest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SkyArenaConfigTest {

    private Random r;

    @Before
    public void setup() {
        r = new Random();
    }

    @Test
    public void testEquals() {
        SkyArenaConfig config = getRandom(r);
        // It shouldn't take into account the arena name, so 'null' is passed.
        SkyArenaConfig copy = new SkyArenaConfig(null, config.getSpawns(), config.getNumTeams(), config.getTeamSize(), config.getPlacementY(), config.getBoundaries());
        assertEquals(config, copy);
    }

    @Test
    public void testSerializeDeserialize() {
        SkyArenaConfig config = getRandom(r);
        YamlConfiguration serializationMedium = new YamlConfiguration();
        config.serialize(serializationMedium);
        SkyArenaConfig deserialized = SkyArenaConfig.deserialize(serializationMedium);
        assertEquals(config, deserialized);
    }

    @Test
    public void ensureConfigVersionCheck() {
        SkyArenaConfig config = getRandom(r);
        YamlConfiguration serializationMedium = new YamlConfiguration();
        config.serialize(serializationMedium);
        serializationMedium.set("config-version", serializationMedium.getInt("config-version") + 1);
        try {
            SkyArenaConfig.deserialize(serializationMedium);
            fail("Deserialization on future config version doesn't throw IllegalArgumentException.");
        } catch (IllegalArgumentException ignored) {
        }
    }

    public static SkyArenaConfig getRandom(Random r) {
        int numTeams = 2 + r.nextInt(22);
        List<SkyPlayerLocation> spawns = new ArrayList<SkyPlayerLocation>();
        for (int i = 0; i < Math.max(numTeams, r.nextInt(20)); i++) {
            spawns.add(SkyPlayerLocationTest.getRandomLoc());
        }
        return new SkyArenaConfig(r.nextBoolean() ? "name1" : "name2", spawns, numTeams, 1 + r.nextInt(33), r.nextInt(22), SkyBoundariesConfigTest.getRandom(r));
    }
}
