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
package net.daboross.bukkitdev.skywars.api.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.daboross.bukkitdev.skywars.api.arenaconfig.SkyArenaConfig;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Dabo Ross <http://www.daboross.net/>
 */
public interface SkyConfiguration {

    public void reload() throws IOException, InvalidConfigurationException, SkyConfigurationException;

    public File getArenaFolder();

    public ArenaOrder getArenaOrder();

    public String getMessagePrefix();

    public boolean isInventorySaveEnabled();

    public boolean isEnablePoints();

    public int getDeathPointDiff();

    public int getWinPointDiff();

    public int getKillPointDiff();

    public int getArenaDistanceApart();

    public List<SkyArenaConfig> getEnabledArenas();

    public SkyArenaConfig getParentArena();

    public void saveArena(SkyArenaConfig config);

    public YamlConfiguration getRawConfig();

    public static enum ArenaOrder {

        RANDOM, ORDERED;
        private static final Map<String, ArenaOrder> BY_NAME = new HashMap<String, ArenaOrder>(2);

        static {
            for (ArenaOrder order : values()) {
                BY_NAME.put(order.name().toLowerCase(Locale.ENGLISH), order);
            }
        }

        public static ArenaOrder getOrder(String name) {
            return BY_NAME.get(name.toLowerCase(Locale.ENGLISH));
        }
    }
}
