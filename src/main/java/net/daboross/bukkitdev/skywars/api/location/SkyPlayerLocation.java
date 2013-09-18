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

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Entity;

/**
 *
 * @author Dabo Ross <http://www.daboross.net/>
 */
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@SerializableAs("SkyLocationAccurate")
public class SkyPlayerLocation implements ConfigurationSerializable {

    public final double x;
    public final double y;
    public final double z;
    public final double yaw;
    public final double pitch;
    public final String world;

    public SkyPlayerLocation() {
        this( 0, 0, 0, 0, 0, null );
    }

    public SkyPlayerLocation( double x, double y, double z, String world ) {
        this( x, y, z, 0, 0, world );
    }

    public SkyPlayerLocation( @NonNull Block block ) {
        this( block.getX(), block.getY(), block.getZ(), block.getWorld() == null ? null : block.getWorld().getName() );
    }

    public SkyPlayerLocation( @NonNull Location location ) {
        this( location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), location.getWorld() == null ? null : location.getWorld().getName() );
    }

    public SkyPlayerLocation( @NonNull Entity entity ) {
        this( entity.getLocation() );
    }

    public SkyPlayerLocation( @NonNull SkyBlockLocation location ) {
        this( location.x, location.y, location.z, location.world );
    }

    public SkyPlayerLocation add( double x, double y, double z ) {
        return new SkyPlayerLocation( this.x + x, this.y + y, this.z + z, world );
    }

    public SkyPlayerLocation add( @NonNull SkyBlockLocation location ) {
        return new SkyPlayerLocation( this.x + location.x, this.y + location.y, this.z + location.z, world );
    }

    public SkyPlayerLocation add( @NonNull SkyPlayerLocation location ) {
        return new SkyPlayerLocation( this.x + location.x, this.y + location.y, this.z + location.z, world );
    }

    public SkyPlayerLocation changeWorld( String newWorld ) {
        return new SkyPlayerLocation( x, y, z, yaw, pitch, newWorld );
    }

    public SkyBlockLocation round() {
        return new SkyBlockLocation( (int) Math.round( x ), (int) Math.round( y ), (int) Math.round( z ), world );
    }

    public Location toLocation() {
        World bukkitWorld = null;
        if ( world != null ) {
            bukkitWorld = Bukkit.getWorld( world );
        }
        if ( bukkitWorld == null ) {
            Bukkit.getLogger().log( Level.WARNING, "[SkyWars] [SkyPlayerLocation] World ''{0}'' not found when {1}.toLocation() called", new Object[]{
                world, this
            } );
        }
        return new Location( bukkitWorld, x, y, z );
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( "x", x );
        map.put( "y", y );
        map.put( "z", z );
        if ( yaw != 0 ) {
            map.put( "yaw", yaw );
        }
        if ( pitch != 0 ) {
            map.put( "pitch", pitch );
        }
        if ( world != null ) {
            map.put( "world", world );
        }
        return map;
    }

    public void serialize( ConfigurationSection configurationSection ) {
        configurationSection.set( "x", x );
        configurationSection.set( "y", y );
        configurationSection.set( "z", z );
        if ( yaw != 0 ) {
            configurationSection.set( "yaw", yaw );
        }
        if ( pitch != 0 ) {
            configurationSection.set( "pitch", pitch );
        }
        if ( world != null ) {
            configurationSection.set( "world", world );
        }
    }

    public static SkyPlayerLocation deserialize( @NonNull Map<String, Object> map ) {
        Object worldO = map.get( "world" );
        Double x = get( map.get( "x" ) );
        Double y = get( map.get( "y" ) );
        Double z = get( map.get( "z" ) );
        Double yaw = get( map.get( "yaw" ) );
        Double pitch = get( map.get( "pitch" ) );
        if ( x == null || y == null || z == null ) {
            x = get( map.get( "xpos" ) );
            y = get( map.get( "ypos" ) );
            z = get( map.get( "zpos" ) );
            if ( x == null || y == null || z == null ) {
                Bukkit.getLogger().log( Level.WARNING, "[SkyWars] [SkyPlayerLocation] Silently failing deserialization due to x, y or z not existing on map or not being valid doubles." );
                return null;
            }
        }
        String world = worldO == null ? null : worldO instanceof String ? (String) worldO : worldO.toString();
        return new SkyPlayerLocation( x, y, z, yaw == null ? 0 : yaw, pitch == null ? 0 : pitch, world );
    }

    public static SkyPlayerLocation deserialize( @NonNull ConfigurationSection configurationSection ) {
        Object worldO = configurationSection.get( "world" );
        Double x = get( configurationSection.get( "x" ) );
        Double y = get( configurationSection.get( "y" ) );
        Double z = get( configurationSection.get( "z" ) );
        Double yaw = get( configurationSection.get( "yaw" ) );
        Double pitch = get( configurationSection.get( "pitch" ) );
        if ( x == null || y == null || z == null ) {
            x = get( configurationSection.get( "xpos" ) );
            y = get( configurationSection.get( "ypos" ) );
            z = get( configurationSection.get( "zpos" ) );
            if ( x == null || y == null || z == null ) {
                Bukkit.getLogger().log( Level.WARNING, "[SkyWars] [SkyPlayerLocation] Silently failing deserialization due to x, y or z not existing on map or not being valid doubles." );
                return null;
            }
        }
        String world = worldO == null ? null : worldO instanceof String ? (String) worldO : worldO.toString();
        return new SkyPlayerLocation( x, y, z, yaw == null ? 0 : yaw, pitch == null ? 0 : pitch, world );
    }

    private static Double get( Object o ) {
        if ( o == null ) {
            return null;
        }
        if ( o instanceof Integer ) {
            return Double.valueOf( ( (Integer) o ).doubleValue() );
        }
        if ( o instanceof Double ) {
            return (Double) o;
        }
        return null;
    }
}
