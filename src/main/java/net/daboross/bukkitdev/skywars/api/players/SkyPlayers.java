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
package net.daboross.bukkitdev.skywars.api.players;

import java.util.UUID;
import org.bukkit.entity.Player;

public interface SkyPlayers {

    /**
     * Tells whether or not storage (score for now, more to be added) is enabled. If it isn't SkyPlayer's
     * get/set/addScore methods will error.
     *
     * @return Whether or not storage is enabled.
     */
    public boolean storageEnabled();

    /**
     * Gets info on the given player.
     *
     * @param player the player to get info on
     * @return the information stored on the player, or null if the player is offline.
     */
    public SkyPlayer getPlayer(Player player);

    /**
     * Gets info on the given player.
     *
     * @param uuid the name of the player to check, case insensitive
     * @return the information stored on the player, or null if the player is offline.
     */
    public SkyPlayer getPlayer(UUID uuid);

    /**
     * Loads the given player from storage.
     */
    public void loadPlayer(Player player);

    /**
     * Unloads the given player from memory.
     */
    public void unloadPlayer(UUID uuid);
}
