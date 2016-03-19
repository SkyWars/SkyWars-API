/*
 * Copyright (C) 2016 Dabo Ross <http://www.daboross.net/>
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

import java.util.LinkedHashMap;
import java.util.Map;
import net.daboross.bukkitdev.skywars.api.location.SkyBlockLocation;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;

public class SkyArenaChestConfig implements SkyArenaChest {

    private final boolean randomizationEnabled;
    private final int chestLevel;
    private final int minItemValue;
    private final int maxItemValue;
    private final SkyBlockLocation relativeLocation;

    public SkyArenaChestConfig(final SkyBlockLocation relativeLocation) {
        this(false, 30, 0, 30, relativeLocation);
    }

    public SkyArenaChestConfig(final boolean randomizationEnabled, final int chestLevel, final int minItemValue, final int maxItemValue, final SkyBlockLocation relativeLocation) {
        this.randomizationEnabled = randomizationEnabled;
        this.chestLevel = chestLevel;
        this.minItemValue = minItemValue;
        this.maxItemValue = maxItemValue;
        this.relativeLocation = relativeLocation;
    }

    @Override
    public boolean isRandomizationEnabled() {
        return randomizationEnabled;
    }

    @Override
    public int getChestLevel() {
        return chestLevel;
    }

    @Override
    public SkyBlockLocation getLocation() {
        return relativeLocation;
    }

    @Override
    public void serialize(final ConfigurationSection configuration) {
        configuration.set("location", relativeLocation);
        configuration.set("randomize", randomizationEnabled);
        configuration.set("total-level", chestLevel);
        configuration.set("min-item-value", minItemValue);
        configuration.set("max-item-value", maxItemValue);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>(5);
        map.put("location", relativeLocation.serialize());
        map.put("randomize", randomizationEnabled);
        map.put("total-level", chestLevel);
        map.put("min-item-value", minItemValue);
        map.put("max-item-value", maxItemValue);
        return map;
    }

    public static SkyArenaChestConfig deserialize(ConfigurationSection configurationSection) {
        Validate.isTrue(configurationSection.isBoolean("randomize"), "chest missing valid randomize");
        Validate.isTrue(configurationSection.isInt("total-level"), "chest missing valid total-level");
        Validate.isTrue(configurationSection.isInt("min-item-value"), "chest missing valid min-item-value");
        Validate.isTrue(configurationSection.isInt("max-item-value"), "chest missing valid max-item-value");
        Validate.isTrue(configurationSection.isConfigurationSection("location"), "chest missing valid location");

        int level = configurationSection.getInt("total-level");
        int minItemValue = configurationSection.getInt("min-item-value");
        int maxItemValue = configurationSection.getInt("max-item-value");
        boolean randomize = configurationSection.getBoolean("randomize");
        SkyBlockLocation location = SkyBlockLocation.deserialize(configurationSection.getConfigurationSection("location"));

        return new SkyArenaChestConfig(randomize, level, minItemValue, maxItemValue, location);
    }

    public static SkyArenaChestConfig deserialize(Map<String, Object> map) {
        Object randomizeObj = map.get("randomize");
        Object levelObj = map.get("total-level");
        Object minItemValueObj = map.get("min-item-value");
        Object maxItemValueObj = map.get("max-item-value");
        Object locationObj = map.get("location");
        Validate.isTrue(randomizeObj instanceof Boolean, "chest missing valid randomize");
        Validate.isTrue(levelObj instanceof Integer, "chest missing valid total-level");
        Validate.isTrue(minItemValueObj instanceof Integer, "chest missing valid min-item-value");
        Validate.isTrue(maxItemValueObj instanceof Integer, "chest missing valid max-item-value");
        Validate.isTrue(locationObj instanceof Map, "chest missing valid location");
        SkyBlockLocation location = SkyBlockLocation.deserialize((Map) locationObj);
        Validate.isTrue(location != null, "chest missing valid location");
        boolean randomize = (Boolean) randomizeObj;
        int level = (Integer) levelObj;
        int minItemValue = (Integer) minItemValueObj;
        int maxItemValue = (Integer) maxItemValueObj;
        return new SkyArenaChestConfig(randomize, level, minItemValue, maxItemValue, location);
    }
}
