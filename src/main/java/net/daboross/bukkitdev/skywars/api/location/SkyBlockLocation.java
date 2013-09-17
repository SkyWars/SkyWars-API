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
@SerializableAs("SkyLocation")
public class SkyBlockLocation implements ConfigurationSerializable {

    public final int x;
    public final int y;
    public final int z;
    public final String world;

    public SkyBlockLocation() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.world = null;
    }

    public SkyBlockLocation( int x, int y, int z, String world ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public SkyBlockLocation( @NonNull Block block ) {
        this( block.getX(), block.getY(), block.getZ(), block.getWorld().getName() );
    }

    public SkyBlockLocation( @NonNull Location location ) {
        this( location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getWorld().getName() );
    }

    public SkyBlockLocation( @NonNull Entity entity ) {
        this( entity.getLocation() );
    }

    public SkyBlockLocation add( int modX, int modY, int modZ ) {
        return new SkyBlockLocation( x + modX, y + modY, z + modZ, world );
    }

    public SkyBlockLocation add( @NonNull SkyBlockLocation location ) {
        return new SkyBlockLocation( this.x + location.x, this.y + location.y, this.z + location.z, world );
    }

    public SkyPlayerLocation add( @NonNull SkyPlayerLocation location ) {
        return new SkyPlayerLocation( this.x + location.x, this.y + location.y, this.z + location.z, world );
    }

    public SkyBlockLocation changeWorld( String newWorld ) {
        return new SkyBlockLocation( x, y, z, newWorld );
    }

    public boolean isNear( @NonNull Location loc ) {
        return world.equals( loc.getWorld().getName() )
                && x <= loc.getX() + 1 && x >= loc.getX() - 1
                && y <= loc.getY() + 1 && y >= loc.getY() - 1
                && z <= loc.getZ() + 1 && z >= loc.getZ() - 1;
    }

    public Location toLocation() {
        World bukkitWorld = null;
        if ( world != null ) {
            bukkitWorld = Bukkit.getWorld( world );
        }
        if ( bukkitWorld == null ) {
            Bukkit.getLogger().log( Level.WARNING, "[SkyWars] [SkyBlockLocation] World ''{0}'' not found when {1}.toLocation() called", new Object[]{
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
        if ( world != null ) {
            map.put( "world", world );
        }
        return map;
    }

    public void serialize( @NonNull ConfigurationSection section ) {
        section.set( "x", x );
        section.set( "y", y );
        section.set( "z", z );
        if ( world != null ) {
            section.set( "world", world );
        }
    }

    public static SkyBlockLocation deserialize( @NonNull Map<String, Object> map ) {
        Object xObject = map.get( "x" ),
                yObject = map.get( "y" ),
                zObject = map.get( "z" ),
                worldObject = map.get( "world" );
        if ( !( xObject instanceof Integer && yObject instanceof Integer && zObject instanceof Integer ) ) {
            xObject = map.get( "xpos" );
            yObject = map.get( "ypos" );
            zObject = map.get( "zpos" );
            if ( !( xObject instanceof Integer && yObject instanceof Integer && zObject instanceof Integer ) ) {
                Bukkit.getLogger().log( Level.WARNING, "[SkyWars] [SkyBlockLocation] Silently failing deserialization due to x, y or z not existing on map or not being integers." );
                return null;
            }
        }
        Integer x = (Integer) xObject, y = (Integer) yObject, z = (Integer) zObject;
        String worldString = worldObject == null ? null : worldObject.toString();
        return new SkyBlockLocation( x, y, z, worldString );
    }

    public static SkyBlockLocation deserialize( @NonNull ConfigurationSection configurationSection ) {
        Object xObject = configurationSection.get( "x" );
        Object yObject = configurationSection.get( "y" );
        Object zObject = configurationSection.get( "z" );
        Object worldObject = configurationSection.get( "world" );
        if ( !( xObject instanceof Integer
                && yObject instanceof Integer
                && zObject instanceof Integer ) ) {
            xObject = configurationSection.get( "xpos" );
            yObject = configurationSection.get( "ypos" );
            zObject = configurationSection.get( "zpos" );
            if ( !( xObject instanceof Integer
                    && yObject instanceof Integer
                    && zObject instanceof Integer ) ) {
                Bukkit.getLogger().log( Level.WARNING, "[SkyWars] [SkyBlockLocation] Silently failing deserialization from configurationSection due to x, y or z not existing on map or not being integers." );
                return null;
            }
        }
        Integer x = (Integer) xObject, y = (Integer) yObject, z = (Integer) zObject;
        String worldString = worldObject instanceof String ? (String) worldObject : worldObject == null ? null : worldObject.toString();
        return new SkyBlockLocation( x, y, z, worldString );
    }
}
