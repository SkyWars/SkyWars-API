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
import lombok.NonNull;
import net.daboross.bukkitdev.skywars.api.SkyWars;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Event called when a player leaves a game. No player will ever leave a game
 * without this event being called.
 * <br>
 * When a game ends whether canceled or the server is quit, this event is called
 * once for all remaining players.
 */
@EqualsAndHashCode(callSuper = false)
public class LeaveGameEvent extends PlayerEvent {

    private static final HandlerList handlerList = new HandlerList();
    private final SkyWars plugin;
    private final int id;

    public LeaveGameEvent(@NonNull SkyWars plugin, int id, @NonNull Player who) {
        super(who);
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
}
