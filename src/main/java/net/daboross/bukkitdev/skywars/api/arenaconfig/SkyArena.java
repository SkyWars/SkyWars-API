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
package net.daboross.bukkitdev.skywars.api.arenaconfig;

import java.util.List;
import net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocation;

/**
 *
 * @author Dabo Ross <http://www.daboross.net/>
 */
public interface SkyArena {

    public SkyArena getParent();

    public String getArenaName();

    public List<SkyPlayerLocation> getSpawns();

    public void setSpawns( List<SkyPlayerLocation> spawns );

    public int getNumTeams();

    public void setNumTeams( Integer numTeams );

    public Integer getRawNumTeams();

    @Deprecated
    public int getNumPlayers();

    @Deprecated
    public void setNumPlayers( Integer numPlayers );

    @Deprecated
    public Integer getRawNumPlayers();

    public int getTeamSize();

    public void setTeamSize( Integer teamSize );

    public Integer getRawTeamSize();

    public SkyBoundaries getBoundaries();

    public SkyPlacement getPlacement();

    public SkyMessages getMessages();
}
