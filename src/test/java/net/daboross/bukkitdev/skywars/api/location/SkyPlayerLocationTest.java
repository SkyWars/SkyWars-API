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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dabo Ross <http://www.daboross.net/>
 */
public class SkyPlayerLocationTest
{

    @Test
    public void testEquals()
    {
        SkyPlayerLocation loc1 = getRandomLoc();
        SkyPlayerLocation loc2 = new SkyPlayerLocation( loc1.x, loc1.y, loc1.z, loc1.yaw, loc1.pitch, loc1.world );
        SkyPlayerLocation loc3 = loc1.changeWorld( null );
        assertEquals( loc1, loc2 );
        assertNotEquals( loc1, loc3 );
    }

    @Test
    public void testSerializeDeserializeMap()
    {
        SkyPlayerLocation original = getRandomLoc();
        Map<String, Object> serialized = original.serialize();
        SkyPlayerLocation deserialized = SkyPlayerLocation.deserialize( serialized );
        assertEquals( original, deserialized );
    }

    @Test
    public void testSerializeDeserializeConfigurationSection()
    {
        SkyPlayerLocation original = getRandomLoc();
        YamlConfiguration serializationMedium = new YamlConfiguration();
        original.serialize( serializationMedium );
        SkyPlayerLocation deserialized = SkyPlayerLocation.deserialize( serializationMedium );
        assertEquals( original, deserialized );
    }

    public static SkyPlayerLocation getRandomLoc()
    {
        double x = ( Math.random() - 0.5 ) * 20;
        double y = ( Math.random() - 0.5 ) * 20;
        double z = ( Math.random() - 0.5 ) * 20;
        double yaw = ( Math.random() - 0.5 ) * 20;
        double pitch = ( Math.random() - 0.5 ) * 20;
        String world = "worldTest";
        return new SkyPlayerLocation( x, y, z, yaw, pitch, world );
    }
}
