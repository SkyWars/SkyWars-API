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

import java.util.List;

public interface SkyIDHandler {

    /**
     * Checks whether or not a game is running with the given ID.
     *
     * @param id The ID to check.
     * @return true if there is a game running with the given id, false
     * otherwise.
     */
    public boolean gameRunning(int id);

    /**
     * Gets the SkyGame for the given id.
     *
     * @param id The ID of the game.
     * @return A SkyGame running under the given id, or null if there is no
     * currently running SkyGame with the given id.
     */
    public SkyGame getGame(int id);

    /**
     * Gets an unmodifiable list containing all IDs of running games.
     *
     * @return an unmodifiable list containing an ID for each running game.
     */
    public List<Integer> getCurrentIDs();
}
