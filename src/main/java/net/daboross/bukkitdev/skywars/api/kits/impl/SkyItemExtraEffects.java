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

import java.util.List;
import net.daboross.bukkitdev.skywars.api.kits.SkyItemMeta;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

public class SkyItemExtraEffects implements SkyItemMeta {

    private final List<PotionEffect> effects;

    public SkyItemExtraEffects(final List<PotionEffect> effects) {
        this.effects = effects != null && effects.isEmpty() ? null : effects;
    }

    @Override
    public void applyToItem(final ItemStack itemStack) {
        if (itemStack.getType() == Material.POTION && effects != null) {
            PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
            for (PotionEffect effect : effects) {
                meta.addCustomEffect(effect, false);
            }
            meta.setMainEffect(effects.get(0).getType());
            itemStack.setItemMeta(meta);
        }
    }

    @Override
    public String toString() {
        return "SkyItemExtraEffects{" +
                "effects=" + effects +
                '}';
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SkyItemExtraEffects)) return false;

        SkyItemExtraEffects effects1 = (SkyItemExtraEffects) o;

        if (effects != null ? !effects.equals(effects1.effects) : effects1.effects != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return effects != null ? effects.hashCode() : 0;
    }
}
