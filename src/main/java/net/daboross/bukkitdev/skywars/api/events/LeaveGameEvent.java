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

/**
 * Event called when a player leaves a game. No player will ever leave a game without this event being called. <br> When
 * a game ends whether canceled or the server is quit, this event is called once for all remaining players.
 */
public class LeaveGameEvent extends PlayerEvent {

    private static final HandlerList handlerList = new HandlerList();
    private final SkyWars plugin;
    private final int id;

    public LeaveGameEvent(SkyWars plugin, int id, Player who) {
        super(who);
        Validate.notNull(plugin, "Plugin cannot be null");
        Validate.notNull(who, "Player cannot be null");
        this.plugin = plugin;
        this.id = id;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public SkyWars getPlugin() {
        return plugin;
    }

    public int getId() {
        return id;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public String toString() {
        return "LeaveGameEvent{" +
                "plugin=" + plugin +
                ", id=" + id +
                ", player=" + player +
                '}';
    }

    @Override
    @SuppressWarnings("RedundantIfStatement")
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof LeaveGameEvent)) return false;

        LeaveGameEvent event = (LeaveGameEvent) o;

        if (id != event.id) return false;
        if (!player.equals(event.player)) return false;
        if (!plugin.equals(event.plugin)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = plugin.hashCode();
        result = 31 * result + id;
        result = 31 * result + player.hashCode();
        return result;
    }
}
