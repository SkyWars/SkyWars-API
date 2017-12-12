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
package net.daboross.bukkitdev.skywars.api.location;

import java.util.List;
import java.util.Map;

public interface SkyLocationStore {

    SkyPlayerLocation getLobbyPosition();

    void setLobbyPosition(SkyPlayerLocation lobbyPosition);

    /**
     * @return the current and original portal list (not a copy).
     */
    List<SkyPortalData> getPortals();

    void save();

    /**
     * Retrieves a sign we know about at the given location
     *
     * @param loc the location to look for
     * @return the SkySignData for the sign at the given location, or null if there is no known sign there.
     */
    SkySignData getSignAt(SkyBlockLocation loc);

    /**
     * Retrieves all signs registered to a given queue. If queue is null, or multi-queue is disabled, returns all signs.
     *
     * The returned list is modifyable, but it should not be modified.
     *
     * @param queueName The queue, or null for non-multi-queue mode.
     * @return a list of all signs registered for that queue.
     */
    List<SkySignData> getQueueSigns(String queueName);

    /**
     * Registers a new sign data.
     */
    void registerSign(SkySignData data);

    /**
     * Removes a sign registration.
     */
    void removeSign(SkySignData data);
}
