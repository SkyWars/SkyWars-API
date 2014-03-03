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
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaPlayerKillPlayerEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private final SkyWars plugin;
    private final int gameId;
    private final String killerName;
    private final Player killed;

    public ArenaPlayerKillPlayerEvent(final SkyWars plugin, final int gameId, final String killerName, final Player killed) {
        Validate.notNull(plugin, "Plugin cannot be null");
        Validate.notNull(killerName, "Killer name cannot be null");
        Validate.notNull(killed, "Killed cannot be null");
        this.plugin = plugin;
        this.gameId = gameId;
        this.killerName = killerName;
        this.killed = killed;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public SkyWars getPlugin() {
        return plugin;
    }

    public int getGameId() {
        return gameId;
    }

    public String getKillerName() {
        return killerName;
    }

    public Player getKilled() {
        return killed;
    }

    @Override
    public String toString() {
        return "ArenaPlayerKillPlayerEvent{" +
                "plugin=" + plugin +
                ", gameId=" + gameId +
                ", killerName='" + killerName + '\'' +
                ", killed=" + killed +
                '}';
    }

    @Override
    @SuppressWarnings("RedundantIfStatement")
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ArenaPlayerKillPlayerEvent)) return false;

        ArenaPlayerKillPlayerEvent event = (ArenaPlayerKillPlayerEvent) o;

        if (gameId != event.gameId) return false;
        if (!killed.equals(event.killed)) return false;
        if (!killerName.equals(event.killerName)) return false;
        if (!plugin.equals(event.plugin)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = plugin.hashCode();
        result = 31 * result + gameId;
        result = 31 * result + killerName.hashCode();
        result = 31 * result + killed.hashCode();
        return result;
    }
}
