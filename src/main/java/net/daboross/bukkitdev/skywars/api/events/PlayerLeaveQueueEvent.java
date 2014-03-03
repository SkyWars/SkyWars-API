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

package net.daboross.bukkitdev.skywars.api.events;

import net.daboross.bukkitdev.skywars.api.SkyWars;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerLeaveQueueEvent extends PlayerEvent {

    private static final HandlerList handlerList = new HandlerList();
    private final SkyWars plugin;

    public PlayerLeaveQueueEvent(SkyWars plugin, Player who) {
        super(who);
        Validate.notNull(plugin, "Plugin cannot be null");
        Validate.notNull(who, "Player cannot be null");
        this.plugin = plugin;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public SkyWars getPlugin() {
        return plugin;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public String toString() {
        return "PlayerLeaveQueueEvent{" +
                "plugin=" + plugin +
                ", player=" + player +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerLeaveQueueEvent)) return false;

        PlayerLeaveQueueEvent event = (PlayerLeaveQueueEvent) o;

        if (!player.equals(event.player)) return false;
        if (!plugin.equals(event.plugin)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = plugin.hashCode();
        result = 31 * result + player.hashCode();
        return result;
    }
}
