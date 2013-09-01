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
package net.daboross.bukkitdev.skywars.api.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.daboross.bukkitdev.skywars.api.arenaconfig.SkyArenaConfig;

/**
 *
 * @author daboross
 */
public interface SkyConfiguration {

    public void load();

    public void reload();

    public void save();

    public List<SkyArenaConfig> getEnabledArenas();

    public ArenaOrder getArenaOrder();

    public String getMessagePrefix();

    public static enum ArenaOrder {

        RANDOM, ORDERED;
        private static final Map<String, ArenaOrder> BY_NAME = new HashMap<String, ArenaOrder>(2);

        static {
            for (ArenaOrder order : values()) {
                BY_NAME.put(order.name().toLowerCase(), order);
            }
        }

        public static ArenaOrder getOrder(String name) {
            return BY_NAME.get(name.toLowerCase());
        }
    }
}
