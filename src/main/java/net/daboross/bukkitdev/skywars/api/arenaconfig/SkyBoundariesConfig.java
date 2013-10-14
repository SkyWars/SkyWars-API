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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.daboross.bukkitdev.skywars.api.parent.Parentable;
import net.daboross.bukkitdev.skywars.api.location.SkyBlockLocationRange;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;

/**
 *
 * @author Dabo Ross <http://www.daboross.net/>
 */
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class SkyBoundariesConfig extends Parentable<SkyBoundariesConfig> implements SkyBoundaries {

    @Getter
    private SkyBlockLocationRange originRaw;
    @Getter
    private SkyBlockLocationRange buildingRaw;
    @Getter
    private SkyBlockLocationRange clearingRaw;

    public SkyBoundariesConfig() {
    }

    public SkyBoundariesConfig(SkyBoundariesConfig parent) {
        super(parent);
    }

    public SkyBoundariesConfig(SkyBlockLocationRange origin, SkyBlockLocationRange building, SkyBlockLocationRange clearing) {
        this.originRaw = origin;
        this.buildingRaw = building;
        this.clearingRaw = clearing;
    }

    public SkyBoundariesConfig(SkyBlockLocationRange origin, SkyBlockLocationRange building, SkyBlockLocationRange clearing, SkyBoundariesConfig parent) {
        super(parent);
        this.originRaw = origin;
        this.buildingRaw = building;
        this.clearingRaw = clearing;
    }

    public void copyDataFrom(SkyBoundariesConfig boundaries) {
        this.originRaw = boundaries.originRaw;
        this.buildingRaw = boundaries.buildingRaw;
        this.clearingRaw = boundaries.clearingRaw;
    }

    @Override
    public boolean definesAnything() {
        return originRaw != null || buildingRaw != null || clearingRaw != null;
    }

    @Override
    public SkyBlockLocationRange getOrigin() {
        if (this.originRaw == null) {
            if (parent == null) {
                throw new IllegalStateException("Ultimate parent origin boundary not found.");
            } else {
                return parent.getOrigin();
            }
        }
        return originRaw;
    }

    @Override
    public SkyBlockLocationRange getBuilding() {
        if (this.originRaw == null) {
            if (parent == null) {
                throw new IllegalStateException("Ultimate parent building boundary not found.");
            } else {
                return parent.getBuilding();
            }
        }
        return buildingRaw;
    }

    @Override
    public SkyBlockLocationRange getClearing() {
        if (this.clearingRaw == null) {
            if (parent == null) {
                throw new IllegalStateException("Ultimate parent clearing boundary not found.");
            } else {
                return parent.getClearing();
            }
        }
        return clearingRaw;
    }

    @Override
    public void setOrigin(SkyBlockLocationRange origin) {
        this.originRaw = origin;
    }

    @Override
    public void setBuilding(SkyBlockLocationRange building) {
        this.buildingRaw = building;
    }

    @Override
    public void setClearing(SkyBlockLocationRange clearing) {
        this.clearingRaw = clearing;
    }

    public SkyBlockLocationRange getOriginInternal() {
        return originRaw;
    }

    public SkyBlockLocationRange getBuildingInternal() {
        return buildingRaw;
    }

    public SkyBlockLocationRange getClearingInternal() {
        return clearingRaw;
    }

    public void serialize(ConfigurationSection section) {
        if (originRaw != null) {
            originRaw.serialize(section.createSection("origin"));
        }
        if (buildingRaw != null) {
            buildingRaw.serialize(section.createSection("building"));
        }
        if (clearingRaw != null) {
            clearingRaw.serialize(section.createSection("clearing"));
        }
    }

    public static SkyBoundariesConfig deserialize(ConfigurationSection configurationSection) {
        SkyBlockLocationRange origin = SkyBlockLocationRange.deserialize(configurationSection.getConfigurationSection("origin")),
                building = SkyBlockLocationRange.deserialize(configurationSection.getConfigurationSection("building")),
                clearing = SkyBlockLocationRange.deserialize(configurationSection.getConfigurationSection("clearing"));
        return new SkyBoundariesConfig(origin, building, clearing);
    }

    public String toIndentedString(int indentAmount) {
        return "SkyBoundariesConfig{\n"
                + (parent == null ? "" : getIndent(indentAmount + 1) + "parent=" + parent.toIndentedString(indentAmount + 1) + ",\n")
                + (originRaw == null ? "" : getIndent(indentAmount + 1) + "origin=" + originRaw.toIndentedString(indentAmount + 1) + ",\n")
                + (buildingRaw == null ? "" : getIndent(indentAmount + 1) + "building=" + buildingRaw.toIndentedString(indentAmount + 1) + ",\n")
                + (clearingRaw == null ? "" : getIndent(indentAmount + 1) + "clearing=" + clearingRaw.toIndentedString(indentAmount + 1) + ",\n")
                + getIndent(indentAmount) + "}";
    }

    private String getIndent(int indentAmount) {
        return StringUtils.repeat("\t", indentAmount);
    }
}
