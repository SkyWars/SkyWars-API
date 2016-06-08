/*
 * Copyright (C) 2013-2016 Dabo Ross <http://www.daboross.net/>
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
package net.daboross.bukkitdev.skywars.api.arenaconfig;

import java.util.List;
import net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocation;
import org.bukkit.configuration.ConfigurationSection;

public interface SkyArena {

    String getArenaName();

    List<SkyPlayerLocation> getSpawns();

    void setSpawns(List<SkyPlayerLocation> spawns);

    int getNumTeams();

    void setNumTeams(int numTeams);

    /**
     * @return The total maximum number of players to occupy this arena.
     */
    int getNumPlayers();

    /**
     * @return The maximum size of teams for players in this arena.
     */
    int getTeamSize();

    void setTeamSize(int teamSize);

    int getPlacementY();

    void setPlacementY(int placementY);

    SkyBoundaries getBoundaries();

    void serialize(ConfigurationSection configuration);

    /**
     * Returns a non-null list of chests. If chests haven't been loaded for this arena, returns a new empty list.
     */
    List<SkyArenaChest> getChests();

    /**
     * Returns a nullable list of chests. If chests haven't been loaded for this arena, returns null.
     */
    List<SkyArenaChest> getChestConfiguration();

    void setChests(List<SkyArenaChest> chests);
}
