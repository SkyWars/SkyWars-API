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

import lombok.EqualsAndHashCode;
import net.daboross.bukkitdev.skywars.api.SkyWars;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@EqualsAndHashCode(callSuper = true)
public class ArenaPlayerDeathEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private final SkyWars plugin;
    private final int gameId;
    private final Player killed;

    public ArenaPlayerDeathEvent(final SkyWars plugin, final int gameId, final Player killed) {
        Validate.notNull(killed, "Killed cannot be null");
        this.plugin = plugin;
        this.gameId = gameId;
        this.killed = killed;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public SkyWars getPlugin() {
        return plugin;
    }

    public int getGameId() {
        return gameId;
    }

    public Player getKilled() {
        return killed;
    }

    @Override
    public String toString() {
        return "ArenaPlayerDeathEvent{" +
                "plugin=" + plugin +
                ", gameId=" + gameId +
                ", killed=" + killed +
                '}';
    }
}
