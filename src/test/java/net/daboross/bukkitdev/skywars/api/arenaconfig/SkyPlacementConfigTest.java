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
package net.daboross.bukkitdev.skywars.api.arenaconfig;

import java.util.Random;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Dabo Ross <http://www.daboross.net/>
 */
public class SkyPlacementConfigTest {

    private Random r;

    @Before
    public void setupRandom() {
        r = new Random();
    }

    @Test
    public void testEquals() {
        SkyPlacementConfig original = getRandom( r );
        SkyPlacementConfig copy = new SkyPlacementConfig( original.getPlacementY(), original.getDistanceApart() );
        Assert.assertEquals( original, copy );
    }

    @Test
    public void testSerializeDeserialize() {
        SkyPlacementConfig original = getRandom( r );
        YamlConfiguration serializationMedium = new YamlConfiguration();
        original.serialize( serializationMedium );
        SkyPlacementConfig deserialized = SkyPlacementConfig.deserialize( serializationMedium );
        Assert.assertEquals( original, deserialized );
    }

    public static SkyPlacementConfig getRandom( Random r ) {
        return new SkyPlacementConfig( r.nextInt( 200 ), r.nextInt( 1000 ) );
    }
}
