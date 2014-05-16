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
import java.util.UUID;
import net.daboross.bukkitdev.skywars.api.arenaconfig.SkyArena;
import net.daboross.bukkitdev.skywars.api.location.SkyBlockLocation;
import net.daboross.bukkitdev.skywars.api.location.SkyBlockLocationRange;

public interface SkyGame {

    public int getId();

    public List<UUID> getAlivePlayers();

    public List<UUID> getDeadPlayers();

    public SkyBlockLocation getMin();

    public SkyBlockLocationRange getBuildingBoundaries();

    public SkyArena getArena();

    public boolean areTeamsEnabled();

    public int getTeamNumber(UUID playerUuid);

    public List<UUID> getAlivePlayersInTeam(int teamNumber);

    public List<UUID> getAllPlayersInTeam(int teamNumber);

    public int getNumTeams();
}
