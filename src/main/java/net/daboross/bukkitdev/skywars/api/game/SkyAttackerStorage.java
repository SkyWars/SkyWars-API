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

public interface SkyAttackerStorage {

    /**
     * Gets the name of the person / entity who last damaged a given player. This is reset every time a player leaves a
     * game.
     *
     * @param uuid the uuid of the player to check for
     * @return the name of the last player / entity who hit the player with the given name, or null if no one has hit
     * that player since they last left a game.
     */
    public String getKillerName(UUID uuid);

    /**
     * Gets the uuid of the player who last damaged a given player. This is reset every time a player leaves a game, and
     * is unset when the player is killed by an entity.
     *
     * @param uuid the uuid of the player to check for
     * @return the uuid of the last player who hit the player with the given name, or null if no one has hit that player
     * since they last left a game.
     */
    public UUID getKillerUuid(UUID uuid);
}
