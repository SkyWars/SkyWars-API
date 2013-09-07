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
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

/**
 *
 * @author Dabo Ross <http://www.daboross.net/>
 */
@ToString
@EqualsAndHashCode
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
     * @throws NullPointerException if min or max is null
     */
    public SkyBlockLocationRange(@NonNull SkyBlockLocation min, @NonNull SkyBlockLocation max, String world) {
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

    public SkyBlockLocationRange add(@NonNull SkyBlockLocation loc) {
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

    public void serialize(@NonNull ConfigurationSection section) {
        min.changeWorld(null).serialize(section.createSection("min"));
        max.changeWorld(null).serialize(section.createSection("max"));
        section.set("world", world);
    }

    public static SkyBlockLocationRange deserialize(@NonNull Map<String, Object> map) {
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
        if (configurationSection == null) {
            return null;
        }
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

    public String toIndentedString(int indentAmount) {
        return "SkyBlockLocationRange{\n"
                + getIndent(indentAmount + 1) + "min=" + min + ",\n"
                + getIndent(indentAmount + 1) + "max=" + max + ",\n"
                + (world == null ? "" : getIndent(indentAmount + 1) + "world=" + world + "\n")
                + getIndent(indentAmount) + "}";
    }

    private String getIndent(int indentAmount) {
        return StringUtils.repeat("\t", indentAmount);
    }
}
