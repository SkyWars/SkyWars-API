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
     * @return true if the inventory GUI was opened, false if it was not due to lack of kits.
     */
    boolean openKitGui(Player player);

    /**
     * Opens a kit GUI for the player if the player has the `skywars.kitgui` permission, and the "show-kit-gui-on-join"
     * configuration is enabled.
     *
     * @param player the player to show the inventory GUI to, if appropriate
     * @return true if the inventory GUI was opened, false if it was not.
     */
    boolean autoOpenGuiIfApplicable(Player player);

    /**
     * Gets the title used for kit inventory GUIs. This can be used to tell whether or not an interaction occurs in a
     * SkyWars kit GUI.
     */
    String getKitGuiTitle();
}
