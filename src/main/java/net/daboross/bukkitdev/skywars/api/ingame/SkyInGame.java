/*
 * Copyright (C) 2014 Dabo Ross <http://www.daboross.net/>
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
package net.daboross.bukkitdev.skywars.api.ingame;

import org.bukkit.entity.Player;

public interface SkyInGame {

    /**
     * Gets info on the given player.
     *
     * @param player the player to get info on
     * @return the information stored on the player, or null if no information is stored.
     */
    public SkyPlayer getPlayer(Player player);

    /**
     * Gets info on the given player, or null if there is no information on the player.
     *
     * @param name the name of the player to check, case insensitive
     * @return the information stored on the player, or null if no information is stored.
     */
    public SkyPlayer getPlayer(String name);

    /**
     * Gets info stored on a player, or starts storing info if there is no information on the player.
     *
     * @param player the player to get info on
     * @return the information stored on the player
     */
    public SkyPlayer getPlayerForce(Player player);
}
