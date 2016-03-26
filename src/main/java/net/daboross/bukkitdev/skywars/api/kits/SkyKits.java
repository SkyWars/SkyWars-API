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
package net.daboross.bukkitdev.skywars.api.kits;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.bukkit.entity.Player;

public interface SkyKits {

    Set<String> getKitNames();

    Collection<SkyKit> getAllKits();

    SkyKit getKit(String name);

    List<SkyKit> getAvailableKits(Player p);

    List<SkyKit> getUnavailableKits(Player p);

    /**
     * Saves the current kit list to disk in kits.yml. This will overwrite all comments in kits.yml, and all changes
     * made to kits.yml since the server started.
     *
     * @throws IOException If an IOException occurs when saving to disk.
     */
    void save() throws IOException;

    /**
     * Adds a kit to the current kit list. Recommended to use .save() after this to save all kits to disk.
     *
     * @param kit Kit to add.
     */
    void addKit(SkyKit kit);
}
