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
package net.daboross.bukkitdev.skywars.api.storage;

import java.util.List;
import java.util.UUID;
import net.daboross.bukkitdev.skywars.api.players.OfflineSkyPlayer;
import org.bukkit.entity.Player;

public abstract class SkyStorage {

    private static Class<? extends SkyStorageBackend> backend;

    public static Class<? extends SkyStorageBackend> getBackend() {
        return backend;
    }

    @SuppressWarnings("UnusedDeclaration")
    public static void setBackend(final Class<? extends SkyStorageBackend> backend) {
        SkyStorage.backend = backend;
    }

    public abstract void addScore(UUID uuid, int diff);

    public abstract void getScore(UUID uuid, ScoreCallback callback);

    public abstract void setScore(UUID uuid, int score);

    public abstract void getRank(UUID uuid, ScoreCallback callback);

    public abstract SkyInternalPlayer loadPlayer(Player player);

    /**
     * Gets a snapshot of the score, name and rank of an offline player with the given UUID.
     * <p>
     * This may return live SkyPlayer objects if the player is online, but could also contain a snapshot of data - act
     * accordingly.
     *
     * @param uuid     UUID to search for.
     * @param callback Callback to give player to - player may be null!
     */
    public abstract void getOfflinePlayer(UUID uuid, Callback<OfflineSkyPlayer> callback);

    /**
     * Gets a snapshot of the score, name and rank of an offline player with the given name.
     * <p>
     * This may return live SkyPlayer objects if the player is online, but could also contain a snapshot of data - act
     * accordingly.
     *
     * @param name     Name to search for. Case insensitive.
     * @param callback Callback to give player to - player may be null!
     */
    public abstract void getOfflinePlayer(String name, Callback<OfflineSkyPlayer> callback);

    public abstract void dataChanged();

    /**
     * Gets the top x players, with the highest scores. First highest score is first in list.
     * <p>
     * This list has no set length, but will be at least size 10 if there are at least 10 players with scores on the
     * server.
     * <p>
     * Note that a valid OfflineSkyPlayer implementation returned from getTopPlayers() could just store static values,
     * and the only way to get new values would be to run getTopPlayers() again.
     *
     * @param count The ideal number of players to get. It isn't guaranteed that this number will be returned, but it
     *              may be, depending on the backend.
     * @return A list of the top players, with the top player being first.
     */
    public abstract List<? extends OfflineSkyPlayer> getTopPlayers(int count);
}
