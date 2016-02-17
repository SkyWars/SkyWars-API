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

import net.daboross.bukkitdev.skywars.api.kits.SkyItemMeta;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;

public class SkyItemPotion implements SkyItemMeta {

    private final Potion potion;

    public SkyItemPotion(final Potion potion) {
        this.potion = potion;
    }

    @Override
    public void applyToItem(final ItemStack itemStack) {
        if (itemStack.getType() == Material.POTION && potion != null) {
            potion.apply(itemStack);
        }
    }

    @Override
    public String toString() {
        return "SkyItemPotion{" +
                "potion=" + potion +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SkyItemPotion)) return false;

        SkyItemPotion potion1 = (SkyItemPotion) o;

        return potion != null ? potion.equals(potion1.potion) : potion1.potion == null;
    }

    @Override
    public int hashCode() {
        return potion != null ? potion.hashCode() : 0;
    }
}
