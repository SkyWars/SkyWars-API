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
package net.daboross.bukkitdev.skywars.api.location;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import net.daboross.bukkitdev.skywars.api.SkyStatic;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Entity;

@SerializableAs("SkyLocation")
public class SkyBlockLocation implements ConfigurationSerializable {

    public final int x;
    public final int y;
    public final int z;
    public final String world;

    public SkyBlockLocation(int x, int y, int z, String world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public SkyBlockLocation() {
        this(0, 0, 0, null);
    }

    public SkyBlockLocation(Block block) {
        Validate.notNull(block, "Block cannot be null");
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
        this.world = block.getWorld() == null ? null : block.getWorld().getName();
    }

    public SkyBlockLocation(Location location) {
        Validate.notNull(location, "Location cannot be null");
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.world = location.getWorld() == null ? null : location.getWorld().getName();
    }

    public SkyBlockLocation(Entity entity) {
        Validate.notNull(entity, "Entity cannot be null");
        Location location = entity.getLocation();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.world = location.getWorld() == null ? null : location.getWorld().getName();
    }

    public SkyBlockLocation add(int modX, int modY, int modZ) {
        return new SkyBlockLocation(x + modX, y + modY, z + modZ, world);
    }

    public SkyBlockLocation add(SkyBlockLocation location) {
        Validate.notNull(location, "Location cannot be null");
        return new SkyBlockLocation(this.x + location.x, this.y + location.y, this.z + location.z, world);
    }

    public SkyPlayerLocation add(SkyPlayerLocation location) {
        Validate.notNull(location, "Location cannot be null");
        return new SkyPlayerLocation(this.x + location.x, this.y + location.y, this.z + location.z, world);
    }

    public SkyBlockLocation changeWorld(String newWorld) {
        return new SkyBlockLocation(x, y, z, newWorld);
    }

    public boolean isNear(Location loc) {
        Validate.notNull(loc, "Location cannot be null");
        return world.equals(loc.getWorld().getName())
                && x <= loc.getX() + 1 && x >= loc.getX() - 1
                && y <= loc.getY() + 1 && y >= loc.getY() - 1
                && z <= loc.getZ() + 1 && z >= loc.getZ() - 1;
    }

    public Location toLocation() {
        World bukkitWorld = null;
        if (world != null) {
            bukkitWorld = Bukkit.getWorld(world);
        }
        if (bukkitWorld == null) {
            SkyStatic.log(Level.WARNING, "[SkyBlockLocation] World '%s' not found when using toLocation", world);
        }
        return new Location(bukkitWorld, x, y, z);
    }

    public Block toBlock() {
        World bukkitWorld = null;
        if (world != null) {
            bukkitWorld = Bukkit.getWorld(world);
        }
        return bukkitWorld == null ? null : new Location(bukkitWorld, x, y, z).getBlock();
    }

    public Location toLocationWithWorldObj(World bukkitWorld) {
        return new Location(bukkitWorld, x, y, z);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", x);
        map.put("y", y);
        map.put("z", z);
        if (world != null) {
            map.put("world", world);
        }
        return map;
    }

    public void serialize(ConfigurationSection section) {
        Validate.notNull(section, "ConfigurationSection cannot be null");
        section.set("x", x);
        section.set("y", y);
        section.set("z", z);
        if (world != null) {
            section.set("world", world);
        }
    }

    public static SkyBlockLocation deserialize(Map<String, Object> map) {
        Validate.notNull(map, "Map cannot be null");
        Object xObject = map.get("x"),
                yObject = map.get("y"),
                zObject = map.get("z"),
                worldObject = map.get("world");
        if (!(xObject instanceof Integer && yObject instanceof Integer && zObject instanceof Integer)) {
            xObject = map.get("xpos");
            yObject = map.get("ypos");
            zObject = map.get("zpos");
            if (!(xObject instanceof Integer && yObject instanceof Integer && zObject instanceof Integer)) {
                SkyStatic.log(Level.WARNING, "[SkyBlockLocation] Silently failing deserialization due to x, y or z not existing on map or not being integers.");
                return null;
            }
        }
        Integer x = (Integer) xObject, y = (Integer) yObject, z = (Integer) zObject;
        String worldString = worldObject == null ? null : worldObject.toString();
        return new SkyBlockLocation(x, y, z, worldString);
    }

    public static SkyBlockLocation deserialize(ConfigurationSection configurationSection) {
        Validate.notNull(configurationSection, "ConfigurationSection cannot be null");
        Object xObject = configurationSection.get("x");
        Object yObject = configurationSection.get("y");
        Object zObject = configurationSection.get("z");
        Object worldObject = configurationSection.get("world");
        if (!(xObject instanceof Integer
                && yObject instanceof Integer
                && zObject instanceof Integer)) {
            xObject = configurationSection.get("xpos");
            yObject = configurationSection.get("ypos");
            zObject = configurationSection.get("zpos");
            if (!(xObject instanceof Integer
                    && yObject instanceof Integer
                    && zObject instanceof Integer)) {
                SkyStatic.log(Level.WARNING, "[SkyWars] [SkyBlockLocation] Silently failing deserialization from configurationSection due to x, y or z not existing on map or not being integers.");
                return null;
            }
        }
        Integer x = (Integer) xObject, y = (Integer) yObject, z = (Integer) zObject;
        String worldString = worldObject instanceof String ? (String) worldObject : worldObject == null ? null : worldObject.toString();
        return new SkyBlockLocation(x, y, z, worldString);
    }

    public static SkyBlockLocation min(SkyBlockLocation loc1, SkyBlockLocation loc2) {
        return new SkyBlockLocation(Math.min(loc1.x, loc2.x), Math.min(loc1.y, loc2.y), Math.min(loc1.z, loc2.z), loc1.world);
    }

    public static SkyBlockLocation max(SkyBlockLocation loc1, SkyBlockLocation loc2) {
        return new SkyBlockLocation(Math.max(loc1.x, loc2.x), Math.max(loc1.y, loc2.y), Math.max(loc1.z, loc2.z), loc1.world);
    }

    @Override
    public String toString() {
        return "SkyBlockLocation{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", world='" + world + '\'' +
                '}';
    }

    @Override
    @SuppressWarnings("RedundantIfStatement")
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SkyBlockLocation)) return false;

        SkyBlockLocation location = (SkyBlockLocation) o;

        if (x != location.x) return false;
        if (y != location.y) return false;
        if (z != location.z) return false;
        if (world != null ? !world.equals(location.world) : location.world != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        result = 31 * result + (world != null ? world.hashCode() : 0);
        return result;
    }
}
