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
package net.daboross.bukkitdev.skywars.api.score;

import java.io.IOException;
import java.util.UUID;
import net.daboross.bukkitdev.skywars.api.SkyWars;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public abstract class ScoreStorageBackend {

    protected final SkyWars skywars;

    protected ScoreStorageBackend(final SkyWars skywars) {
        this.skywars = skywars;
    }

    public abstract void addScore(UUID playerUuid, int diff);

    public abstract void setScore(UUID playerUuid, int score);

    public abstract void getScore(UUID playerUuid, ScoreCallback callback);

    public abstract void save() throws IOException;

    public abstract int getCachedOnlineScore(UUID playerUuid);

    public abstract void loadCachedScore(Player player);
}
