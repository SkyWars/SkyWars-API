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
package net.daboross.bukkitdev.skywars.api.events;

import java.util.List;
import lombok.NonNull;
import net.daboross.bukkitdev.skywars.api.SkyWars;
import net.daboross.bukkitdev.skywars.api.game.SkyGame;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Dabo Ross <http://www.daboross.net/>
 */
public class GameEndEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private final SkyWars plugin;
    private final SkyGame game;
    private final List<Player> alivePlayers;

    public GameEndEvent(@NonNull SkyWars plugin, @NonNull SkyGame game, @NonNull List<Player> players) {
        this.plugin = plugin;
        this.game = game;
        this.alivePlayers = players;
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

    public SkyGame getGame() {
        return game;
    }

    public List<Player> getAlivePlayers() {
        return alivePlayers;
    }
}
