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

import java.util.Collection;
import java.util.UUID;
import net.daboross.bukkitdev.skywars.api.arenaconfig.SkyArena;
import org.bukkit.entity.Player;

public interface SkyGameQueue {

    /**
     * Checks if a given player is in the queue.
     *
     * @param playerUuid the uuid of the player to check.
     * @return true if a player with the given name is in the queue, false if in the secondary queue, or if not in any
     * queue.
     */
    boolean inQueue(UUID playerUuid);

    /**
     * Checks if a given player is in the secondary queue.
     *
     * @param playerUuid the uuid of the player to check.
     * @return true if the player is in the secondary queue, false if in the primary queue or if not in any queue.
     */
    boolean inSecondaryQueue(UUID playerUuid);

    /**
     * Adds a player to the queue, and starts a queue timer if neccessary. If the queue is full, the player will be
     * added to a secondary queue, and will be added to the queue after the game starts.
     *
     * @param player the player to add.
     * @return true if the player was added to the primary queue, false if they were added to the secondary queue.
     */
    boolean queuePlayer(Player player);

    /**
     * Removes a player from the queue.
     *
     * @param player the player to remove.
     */
    void removePlayer(Player player);

    /**
     * Gets a view onto the current queue. Do not queue or unqueue players while holding this collection. In addition,
     * this collection is not guaranteed to remain updated between game ticks. It should be re-retrieved if it
     * is needed multiple times.
     * <p>
     * Not thread safe.
     *
     * @return an unmodifiable version of the current queue.
     */
    Collection<UUID> getInQueue();

    /**
     * Gets a view onto the current secondary queue. Do not queue or unqueue players while holding this collection. In
     * addition, this collection is not guaranteed to remain updated between game ticks. It should be re-retrieved if it
     * is needed multiple times.
     * <p>
     * Not thread safe.
     *
     * @return an unmodifiable version of the current queue.
     */
    Collection<UUID> getInSecondaryQueue();

    /**
     * Gets a copy of the queue. Use {@code SkyGameQueue.getInQueue()} instead when possible.
     *
     * @return a copy of the current queue.
     */
    UUID[] getCopy();

    /**
     * Gets the number of players currently queued.
     *
     * @return the number of players in the queue
     */
    int getNumPlayersInQueue();

    /**
     * Gets the next planned arena people are queuing for.
     *
     * @return the next arena to be started
     */
    SkyArena getPlannedArena();

    /**
     * Gets whether or not the number of queued players is equal to the maximum player count for the next arena.
     *
     * @return true if the queue is full, false otherwise.
     */
    boolean isQueueFull();

    /**
     * Gets whether or not the number of queued players is equal to or greater than the minimum player count for the
     * next arena.
     *
     * @return true if the minimum number of players are present, false otherwise.
     */
    boolean areMinPlayersPresent();
}
