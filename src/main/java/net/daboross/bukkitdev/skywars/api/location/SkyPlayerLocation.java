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

@SerializableAs("SkyLocationAccurate")
public class SkyPlayerLocation implements ConfigurationSerializable {

    public final double x;
    public final double y;
    public final double z;
    public final double yaw;
    public final double pitch;
    public final String world;

    public SkyPlayerLocation(double x, double y, double z, double yaw, double pitch, String world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.world = world;
    }

    public SkyPlayerLocation() {
        this(0, 0, 0, 0, 0, null);
    }

    public SkyPlayerLocation(double x, double y, double z, String world) {
        this(x, y, z, 0, 0, world);
    }

    public SkyPlayerLocation(Block block) {
        Validate.notNull(block, "Block cannot be null");
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
        this.pitch = 0;
        this.yaw = 0;
        this.world = block.getWorld() == null ? null : block.getWorld().getName();
    }

    public SkyPlayerLocation(Location location) {
        Validate.notNull(location, "Location cannot be null");
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.world = location.getWorld() == null ? null : location.getWorld().getName();
    }

    public SkyPlayerLocation(Entity entity) {
        Validate.notNull(entity, "Entity cannot be null");
        Location location = entity.getLocation();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.world = location.getWorld() == null ? null : location.getWorld().getName();
    }

    public SkyPlayerLocation(SkyBlockLocation location) {
        Validate.notNull(location, "Location cannot be null");
        this.x = location.x;
        this.y = location.y;
        this.z = location.z;
        this.yaw = 0;
        this.pitch = 0;
        this.world = location.world;
    }

    public SkyPlayerLocation add(double x, double y, double z) {
        return new SkyPlayerLocation(this.x + x, this.y + y, this.z + z, world);
    }

    public SkyPlayerLocation add(SkyBlockLocation location) {
        Validate.notNull(location, "Location cannot be null");
        return new SkyPlayerLocation(this.x + location.x, this.y + location.y, this.z + location.z, world);
    }

    public SkyPlayerLocation add(SkyPlayerLocation location) {
        Validate.notNull(location, "Location cannot be null");
        return new SkyPlayerLocation(this.x + location.x, this.y + location.y, this.z + location.z, world);
    }

    public SkyPlayerLocation subtract(SkyPlayerLocation location) {
        Validate.notNull(location, "Location cannot be null");
        return new SkyPlayerLocation(this.x - location.x, this.y - location.y, this.z - location.z, world);
    }

    public SkyPlayerLocation subtract(SkyBlockLocation location) {
        Validate.notNull(location, "Location cannot be null");
        return new SkyPlayerLocation(this.x - location.x, this.y - location.y, this.z - location.z, world);
    }

    public SkyPlayerLocation changeWorld(String newWorld) {
        return (newWorld == null ? world == null : newWorld.equals(world)) ? this : new SkyPlayerLocation(x, y, z, yaw, pitch, newWorld);
    }

    public SkyBlockLocation round() {
        return new SkyBlockLocation((int) Math.round(x), (int) Math.round(y), (int) Math.round(z), world);
    }

    public Location toLocation() {
        World bukkitWorld = null;
        if (world != null) {
            bukkitWorld = Bukkit.getWorld(world);
        }
        if (bukkitWorld == null) {
            SkyStatic.getLogger().log(Level.WARNING, "[SkyPlayerLocation] World ''{0}'' not found when {1}.toLocation() called", new Object[]{
                    world, this
            });
        }
        return new Location(bukkitWorld, x, y, z);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("x", x);
        map.put("y", y);
        map.put("z", z);
        if (yaw != 0) {
            map.put("yaw", yaw);
        }
        if (pitch != 0) {
            map.put("pitch", pitch);
        }
        if (world != null) {
            map.put("world", world);
        }
        return map;
    }

    public void serialize(ConfigurationSection configurationSection) {
        configurationSection.set("x", x);
        configurationSection.set("y", y);
        configurationSection.set("z", z);
        if (yaw != 0) {
            configurationSection.set("yaw", yaw);
        }
        if (pitch != 0) {
            configurationSection.set("pitch", pitch);
        }
        if (world != null) {
            configurationSection.set("world", world);
        }
    }

    public static SkyPlayerLocation deserialize(Map<String, Object> map) {
        Validate.notNull(map, "Map cannot be null");
        Object worldO = map.get("world");
        Double x = getEitherDouble(map, "x", "xpos");
        Double y = getEitherDouble(map, "y", "ypos");
        Double z = getEitherDouble(map, "z", "zpos");
        Double yaw = get(map.get("yaw"));
        Double pitch = get(map.get("pitch"));
        if (x == null || y == null || z == null) {
            SkyStatic.getLogger().log(Level.WARNING, "[SkyPlayerLocation] Silently failing deserialization due to x, y or z not existing on map or not being valid doubles.");
            return null;
        }
        String world = worldO == null ? null : worldO instanceof String ? (String) worldO : worldO.toString();
        return new SkyPlayerLocation(x, y, z, yaw == null ? 0 : yaw, pitch == null ? 0 : pitch, world);
    }

    public static SkyPlayerLocation deserialize(ConfigurationSection configurationSection) {
        Validate.notNull(configurationSection, "Configuration cannot be null");
        Object worldO = configurationSection.get("world");
        Double x = getEitherDouble(configurationSection, "x", "xpos");
        Double y = getEitherDouble(configurationSection, "y", "ypos");
        Double z = getEitherDouble(configurationSection, "z", "zps");
        Double yaw = get(configurationSection.get("yaw"));
        Double pitch = get(configurationSection.get("pitch"));
        if (x == null || y == null || z == null) {
            SkyStatic.getLogger().log(Level.WARNING, "[SkyPlayerLocation] Silently failing deserialization due to x, y or z not existing on map or not being valid doubles.");
            return null;
        }
        String world = worldO == null ? null : worldO instanceof String ? (String) worldO : worldO.toString();
        return new SkyPlayerLocation(x, y, z, yaw == null ? 0 : yaw, pitch == null ? 0 : pitch, world);
    }

    private static Double getEitherDouble(Map<String, Object> map, String key1, String key2) {
        Double result = get(map.get(key1));
        if (result == null) {
            result = get(map.get(key2));
        }
        return result;
    }

    private static Double getEitherDouble(ConfigurationSection section, String key1, String key2) {
        Double result = get(section.get(key1));
        if (result == null) {
            result = get(section.get(key2));
        }
        return result;
    }

    private static Double get(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Number) {
            return ((Number) o).doubleValue();
        }
        return null;
    }

    @Override
    public String toString() {
        return "SkyPlayerLocation{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                ", world='" + world + '\'' +
                '}';
    }

    @Override
    @SuppressWarnings("RedundantIfStatement")
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SkyPlayerLocation)) return false;

        SkyPlayerLocation location = (SkyPlayerLocation) o;

        if (Double.compare(location.pitch, pitch) != 0) return false;
        if (Double.compare(location.x, x) != 0) return false;
        if (Double.compare(location.y, y) != 0) return false;
        if (Double.compare(location.yaw, yaw) != 0) return false;
        if (Double.compare(location.z, z) != 0) return false;
        if (world != null ? !world.equals(location.world) : location.world != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(yaw);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(pitch);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (world != null ? world.hashCode() : 0);
        return result;
    }
}
