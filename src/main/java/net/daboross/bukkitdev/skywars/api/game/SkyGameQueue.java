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
package net.daboross.bukkitdev.skywars.api.game;

public interface SkyGameQueue {

    /**
     * Checks if a given player is in the queue.
     *
     * @param playerName the name of the player to check.
     * @return true if a player with the given name is in the queue, false
     * otherwise.
     */
    public boolean inQueue(String playerName);

    /**
     * Adds a player to the queue, and starts the game if there are enough
     * people in the queue.
     *
     * @param playerName the name of the player to add.
     */
    public void queuePlayer(String playerName);

    /**
     * Removes a player from the queue.
     *
     * @param playerName the name of the player to remove.
     */
    public void removePlayer(String playerName);

    /**
     * Gets a copy of the queue.
     *
     * @return a copy of the current queue.
     */
    public String[] getCopy();
}
