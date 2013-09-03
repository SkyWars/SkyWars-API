/*
 * Copyright (C) 2013 daboross
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
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

/**
 *
 * @author daboross
 */
@SerializableAs("SkyLocationRange")
public class SkyBlockLocationRange implements ConfigurationSerializable {

    public final SkyBlockLocation min;
    public final SkyBlockLocation max;
    public final String world;

    /**
     * Creates a SkyBlockLocationRange.
     *
     * @param min minimum position. min.world is expected to be equal to world,
     * and will be changed if not world already.
     * @param max maximum position. max.world is expected to be equal to world,
     * and will be changed if not world already.
     * @param world world of the range.
     * @throws IllegalArgumentException if min == null || max == null || min.x >
     * max.x || min.y > max.y || min.z > max.z
     */
    public SkyBlockLocationRange(SkyBlockLocation min, SkyBlockLocation max, String world) {
        if (min == null || max == null) {
            throw new IllegalArgumentException("Cannot be null");
        }
        if (min.x > max.x || min.y > max.y || min.z > max.z) {
            throw new IllegalArgumentException("min position cannot be bigger than max position in any dimension.");
        }
        if (min.world == null ? world != null : !min.world.equals(world)) {
            min = min.changeWorld(world);
        }
        if (max.world == null ? world != null : !max.world.equals(world)) {
            max = max.changeWorld(world);
        }
        this.world = min.world;
        this.min = min;
        this.max = max;
    }

    public SkyBlockLocationRange add(SkyBlockLocation loc) {
        return new SkyBlockLocationRange(loc.add(min), loc.add(max), world);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("min", min.changeWorld(null));
        map.put("max", max.changeWorld(null));
        map.put("world", world);
        return map;
    }

    public void serialize(ConfigurationSection section) {
        min.changeWorld(null).serialize(section.createSection("min"));
        max.changeWorld(null).serialize(section.createSection("max"));
        section.set("world", world);
    }

    public static SkyBlockLocationRange deserialize(Map<String, Object> map) {
        Object minObject = map.get("min"), maxObject = map.get("max"),
                worldObject = map.get("world");
        if (!(minObject instanceof SkyBlockLocation && maxObject instanceof SkyBlockLocation)) {
            return null;
        }
        SkyBlockLocation min = (SkyBlockLocation) minObject, max = (SkyBlockLocation) maxObject;
        String world = worldObject == null ? null : worldObject.toString();
        return new SkyBlockLocationRange(min, max, world);
    }

    public static SkyBlockLocationRange deserialize(ConfigurationSection configurationSection) {
        Object worldObject = configurationSection.get("world");
        ConfigurationSection minSection = configurationSection.getConfigurationSection("min"),
                maxSection = configurationSection.getConfigurationSection("max");
        if (minSection == null || maxSection == null) {
            return null;
        }
        SkyBlockLocation min = SkyBlockLocation.deserialize(minSection);
        SkyBlockLocation max = SkyBlockLocation.deserialize(maxSection);
        if (min == null || max == null) {
            return null;
        }
        String world = worldObject instanceof String ? (String) worldObject : worldObject == null ? null : worldObject.toString();
        return new SkyBlockLocationRange(min, max, world);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SkyBlockLocationRange)) {
            return false;
        }
        SkyBlockLocationRange l = (SkyBlockLocationRange) obj;
        return this.min.equals(l.min) && this.max.equals(l.max) && this.world.equals(l.world);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + (this.min != null ? this.min.hashCode() : 0);
        hash = 13 * hash + (this.max != null ? this.max.hashCode() : 0);
        hash = 13 * hash + (this.world != null ? this.world.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "SkyBlockLocationRange{min=" + min + ",max=" + max + ",world=" + world + "}";
    }

    public String toIndentedString(int indent) {
        return "SkyBlockLocationRange{\n"
                + getIndent(indent + 1) + "min=" + min + ",\n"
                + getIndent(indent + 1) + "max=" + max + ",\n"
                + (world == null ? "" : getIndent(indent + 1) + "world=" + world + "\n")
                + getIndent(indent) + "}";
    }

    private String getIndent(int indentAmount) {
        return StringUtils.repeat("\t", indentAmount);
    }
}
