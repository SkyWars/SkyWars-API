/*
 * Copyright (C) 2013-2014 Dabo Ross <http://www.daboross.net/>
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Dabo Ross <http://www.daboross.net/>
 */
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
        SkyArenaConfig copy = new SkyArenaConfig(null, config.getRawSpawns(), config.getRawNumTeams(), config.getRawTeamSize(), config.getRawPlacementY(), config.getBoundaries(), config.getMessages());
        Assert.assertEquals(config, copy);
    }

    @Test
    public void testSerializeDeserialize() {
        SkyArenaConfig config = getRandom(r);
        YamlConfiguration serializationMedium = new YamlConfiguration();
        config.serialize(serializationMedium);
        SkyArenaConfig deserialized = SkyArenaConfig.deserialize(serializationMedium);
        Assert.assertEquals(config, deserialized);
    }

    public static SkyArenaConfig getRandom(Random r) {
        List<SkyPlayerLocation> spawns = null;
        if (r.nextBoolean()) {
            spawns = new ArrayList<SkyPlayerLocation>();
            for (int i = 0; i < r.nextInt(20); i++) {
                spawns.add(SkyPlayerLocationTest.getRandomLoc());
            }
        }
        return new SkyArenaConfig(r.nextBoolean() ? "okspawn" : "notokrandomname", spawns, 2 + r.nextInt(22), 1 + r.nextInt(33), r.nextInt(22), SkyBoundariesConfigTest.getRandom(r), null);
    }
}
