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
package net.daboross.bukkitdev.skywars.api.kits.impl;

import java.util.ArrayList;
import java.util.List;
import net.daboross.bukkitdev.skywars.api.kits.SkyItemMeta;
import net.daboross.bukkitdev.skywars.api.kits.SkyItemMetaType;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SkyNameLoreMeta extends SkyItemMeta {

    private final String name;
    private final List<String> lore;

    public SkyNameLoreMeta(final String name, final List<String> lore) {
        if (name != null) {
            this.name = ChatColor.translateAlternateColorCodes('&', name);
        } else {
            this.name = null;
        }
        if (lore != null && !lore.isEmpty()) {
            this.lore = new ArrayList<>(lore.size());
            for (String line : lore) {
                this.lore.add(ChatColor.translateAlternateColorCodes('&', line));
            }
        } else {
            this.lore = null;
        }
    }

    @Override
    public void applyToItem(final ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (name != null) {
            meta.setDisplayName(name);
        }
        if (lore != null && !lore.isEmpty()) {
            meta.setLore(lore);
        }
        item.setItemMeta(meta);
    }

    @Override
    public SkyItemMetaType getType() {
        return SkyItemMetaType.NAME_LORE;
    }

    public String getName() {
        return name;
    }

    public List<String> getLore() {
        return lore;
    }

    @Override
    public String toString() {
        return "SkyNameLoreMeta{" +
                "name='" + name + '\'' +
                ", lore=" + lore +
                '}';
    }

    @Override
    @SuppressWarnings("RedundantIfStatement")
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SkyNameLoreMeta)) return false;

        SkyNameLoreMeta meta = (SkyNameLoreMeta) o;

        if (name != null ? !name.equals(meta.name) : meta.name != null) return false;
        if (!lore.equals(meta.lore)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + lore.hashCode();
        return result;
    }
}
