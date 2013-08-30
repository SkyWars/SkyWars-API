/*
 * Copyright (C) 2013 daboross
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
import net.daboross.bukkitdev.skywars.api.Parentable;
import net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocation;

/**
 *
 * @author daboross
 */
public class SkyArenaConfig extends Parentable<SkyArenaConfig> {

    private List<SkyPlayerLocation> spawns;
    private Integer numPlayers;
    private final SkyBoundariesConfig boundaries = new SkyBoundariesConfig();
    private final SkyMessagesConfig messages = new SkyMessagesConfig();

    public SkyArenaConfig(SkyArenaConfig parent, List<SkyPlayerLocation> spawns, Integer numPlayers, SkyBoundariesConfig boundaries) {
        this.parent = parent;
        this.spawns = spawns;
        this.numPlayers = numPlayers;
        if (parent != null) {
            this.boundaries.setParent(parent.getBoundaries());
        }
        if (boundaries != null) {
            this.boundaries.copyDataFrom(boundaries);
        }
    }

    @Override
    public void setParent(SkyArenaConfig parent) {
        super.setParent(parent);
        if (parent != null) {
            messages.setParent(parent.getMessages());
            boundaries.setParent(parent.getBoundaries());
        } else {
            messages.setParent(null);
            boundaries.setParent(null);
        }
    }

    public List<SkyPlayerLocation> getSpawns() {
        if (spawns == null) {
            if (parent == null) {
                throw new IllegalStateException("Ultimate parent spawns not found.");
            } else {
                return parent.getSpawns();
            }
        } else {
            return spawns;
        }
    }

    public void setSpawns(List<SkyPlayerLocation> spawns) {
        this.spawns = spawns;
    }

    public int getNumPlayers() {
        if (numPlayers == null) {
            if (parent == null) {
                throw new IllegalStateException("Ultimate parent numPlayers not found.");
            } else {
                return parent.getNumPlayers();
            }
        } else {
            return numPlayers.intValue();
        }
    }

    public void setNumPlayers(Integer numPlayers) {
        this.numPlayers = numPlayers;
    }

    public SkyBoundariesConfig getBoundaries() {
        return boundaries;
    }

    public SkyMessagesConfig getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "ArenaConfig{parent=" + parent + ",spawns=" + spawns + ",numPlayers=" + numPlayers + ",boundaries=" + boundaries + ",messages=" + messages + "}";
    }
}
