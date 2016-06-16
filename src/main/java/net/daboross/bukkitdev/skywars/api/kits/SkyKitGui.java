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
package net.daboross.bukkitdev.skywars.api.kits;

import org.bukkit.entity.Player;

public interface SkyKitGui {

    /**
     * Opens a kit GUI for the player. This will work at any time, and will open up an inventory screen immediately. If
     * this is used when a player is in a SkyWars match, the kit selection made will apply for the next match they
     * join.
     *
     * @param player the player to show the inventory gui to.
     */
    void openKitGui(Player player);

    /**
     * Gets the title used for kit inventory GUIs. This can be used to tell whether or not an interaction occurs in a
     * SkyWars kit GUI.
     */
    String getKitGuiTitle();
}
