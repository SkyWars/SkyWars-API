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
public interface SkyAttackerStorage {

    /**
     * Gets the person who last damaged a given player. This is reset every time
     * a player leaves a game.
     *
     * @param playerName the name of the player to check for
     * @return the name of the last player who hit the player with the given
     * name, or null if no one has hit that player since they last left a game.
     */
    public String getKiller(String playerName);
}
