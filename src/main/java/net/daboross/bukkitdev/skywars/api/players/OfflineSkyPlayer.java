/*
 * Copyright (C) 2016 Dabo Ross <http://www.daboross.net/>
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
package net.daboross.bukkitdev.skywars.api.players;

import java.util.UUID;

/**
 * Offline Sky Player - contains offline data from a top player list. Note that the data is in no way guaranteed to be
 * completely accurate, and it is in no way guaranteed to be updated after being returned.
 * <p/>
 * A valid OfflineSkyPlayer implementation returned from getTopPlayers() could just store static values, and the only
 * way to get new values would be to run getTopPlayers() again.
 */
public interface OfflineSkyPlayer {

    String getName();

    UUID getUuid();

    /**
     * Gets the score. Not guaranteed to be as up to date as ScoreStorage.getScore().
     */
    int getScore();

    /**
     * Gets the rank. Top rank is rank 0. Not guaranteed to be as up to date as ScoreStorage.getRank().
     */
    int getRank();
}
