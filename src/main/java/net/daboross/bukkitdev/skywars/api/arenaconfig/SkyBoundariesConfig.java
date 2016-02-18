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

import net.daboross.bukkitdev.skywars.api.location.SkyBlockLocation;
import net.daboross.bukkitdev.skywars.api.location.SkyBlockLocationRange;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;

public class SkyBoundariesConfig implements SkyBoundaries {

    private final SkyBlockLocationRange origin;
    private final SkyBlockLocationRange building;
    private final SkyBlockLocationRange clearing;

    public SkyBoundariesConfig(SkyBlockLocationRange origin) {
        Validate.notNull(origin);
        this.origin = origin;
        SkyBlockLocation buildingMin = new SkyBlockLocation(-2, -20, -2, null);
        SkyBlockLocation buildingMax = new SkyBlockLocation(
                origin.max.x - origin.min.x + 2,
                origin.max.y - origin.min.y + 2,
                origin.max.z - origin.min.z + 2, null);
        SkyBlockLocation clearingMin = buildingMin.add(-1, -1, -1);
        SkyBlockLocation clearingMax = buildingMax.add(1, 1, 1);
        this.building = new SkyBlockLocationRange(buildingMin, buildingMax, null);
        this.clearing = new SkyBlockLocationRange(clearingMin, clearingMax, null);
    }

    public SkyBoundariesConfig(SkyBlockLocationRange origin, SkyBlockLocationRange building, SkyBlockLocationRange clearing) {
        this.origin = origin;
        this.building = building;
        // ensure clearing can never be smaller than building.
        this.clearing = new SkyBlockLocationRange(
                SkyBlockLocation.min(building.min, clearing.min),
                SkyBlockLocation.max(building.max, clearing.max),
                clearing.world
        );
    }

    @Override
    public SkyBlockLocationRange getOrigin() {
        return origin;
    }

    @Override
    public SkyBlockLocationRange getBuilding() {
        return building;
    }

    @Override
    public SkyBlockLocationRange getClearing() {
        return clearing;
    }

    public void serialize(ConfigurationSection section) {
        if (origin != null) {
            origin.serialize(section.createSection("origin"));
        }
        if (building != null) {
            building.serialize(section.createSection("building"));
        }
        if (clearing != null) {
            clearing.serialize(section.createSection("clearing"));
        }
    }

    public static SkyBoundariesConfig deserialize(ConfigurationSection configurationSection) {
        SkyBlockLocationRange origin = SkyBlockLocationRange.deserialize(configurationSection.getConfigurationSection("origin"));
        SkyBlockLocationRange building = SkyBlockLocationRange.deserialize(configurationSection.getConfigurationSection("building"));
        SkyBlockLocationRange clearing = SkyBlockLocationRange.deserialize(configurationSection.getConfigurationSection("clearing"));
        if (origin != null && building == null && clearing == null) {
            return new SkyBoundariesConfig(origin);
        } else if ((building != null && clearing != null) || origin != null) {
            return new SkyBoundariesConfig(origin, building, clearing);
        } else {
            throw new IllegalArgumentException("Invalid Boundries Configuration: either specify origin, or both building and clearing");
        }
    }

    @Override
    public String toString() {
        return "SkyBoundariesConfig{" +
                "originRaw=" + origin +
                '}';
    }

    @Override
    @SuppressWarnings("RedundantIfStatement")
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SkyBoundariesConfig)) return false;

        SkyBoundariesConfig config = (SkyBoundariesConfig) o;

        if (origin != null ? !origin.equals(config.origin) : config.origin != null) return false;
        if (building != null ? !building.equals(config.building) : config.building != null) return false;
        return clearing != null ? clearing.equals(config.clearing) : config.clearing == null;
    }

    @Override
    public int hashCode() {
        int result = origin != null ? origin.hashCode() : 0;
        result = 31 * result + (building != null ? building.hashCode() : 0);
        result = 31 * result + (clearing != null ? clearing.hashCode() : 0);
        return result;
    }
}
