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
package net.daboross.bukkitdev.skywars.api.players;

import org.bukkit.entity.Player;

public interface SkySavedInventory {

    /**
     * Applies a saved inventory to a given player.
     *
     * @param p                 The player to apply to
     * @param restoreExperience If true, also apply saved experience
     * @param restorePgh        If true, also apply saved gamemode, health, hunger, etc. and teleport the player to
     *                          their old position.
     * @return whether or not teleportation was successful. If it wasn't, inventory wasn't yet applied, and should be done on a delayed basis.
     */
    boolean apply(Player p, boolean restoreExperience, boolean restorePgh);

    /**
     * Applies a saved inventory to a player, with the exception that if restorePgh is enabled, position is not
     * restored.
     *
     * @param p                 The player to apply to
     * @param restoreExperience If true, also apply saved experience
     * @param restorePgh        If true, also apply saved gamemode, health, hunger, etc. (does NOT teleport the player
     *                          to their old position).
     */
    void applyNoTeleportation(Player p, boolean restoreExperience, boolean restorePgh);

    /**
     * If apply() would have teleported the player, teleport the player. Does not do any other restoration.
     *
     * @param p                 The player to apply to
     * @param restoreExperience If true, exprience should be restored with apply() (not used here, included in case this
     *                          is ever a condition for teleporting in the future).
     * @param restorePgh        If true, teleport the player to their old position.
     *
     * @return whether or not teleportation was successful.
     */
    boolean teleportOnlyAndIfPgh(Player p, boolean restoreExperience, boolean restorePgh);
}
