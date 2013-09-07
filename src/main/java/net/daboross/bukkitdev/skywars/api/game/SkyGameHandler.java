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

/**
 *
 * @author Dabo Ross <http://www.daboross.net/>
 */
public interface SkyGameHandler {

    /**
     * Ends a game with the given ID.
     *
     * @param id the id of the game to end.
     * @param broadcast whether or not to broadcast the winner(s) of the game
     * @throws IllegalArgumentException if there is no running game with the
     * given id.
     */
    public void endGame(int id, boolean broadcast);

    /**
     * Removes a player from whatever game they are in.
     *
     * @param playerName the name of the player to remove
     * @param teleport whether or not to teleport the player to the lobby
     * @param broadcast whether or not to broadcast the leaving message
     * @throws IllegalArgumentException if the player is not in a game.
     */
    public void removePlayerFromGame(String playerName, boolean teleport, boolean broadcast);
}
