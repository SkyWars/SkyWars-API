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
package net.daboross.bukkitdev.skywars.api.game;

import java.util.UUID;

public interface SkyCurrentGameTracker {

    /**
     * Gets if a player is currently in a game.
     *
     * @param uuid UUID of the player to check
     * @return whether or not the given player is in a game.
     */
    boolean isInGame(UUID uuid);

    /**
     * Gets the ID of the game a given player is in.
     *
     * @param playerUuid the uuid of the player to check.
     * @return the ID of the game the given player is in, or -1 if none.
     */
    int getGameId(UUID playerUuid);
}
