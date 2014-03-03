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
package net.daboross.bukkitdev.skywars.api.kits.impl;

import java.util.Map;
import net.daboross.bukkitdev.skywars.api.kits.SkyKitItem;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class SkyKitItemConfig implements SkyKitItem {

    private final Material material;
    private final int amount;
    private final Map<Enchantment, Integer> enchantments;

    public SkyKitItemConfig(Material material) {
        Validate.notNull(material, "Material cannot be null");
        this.material = material;
        this.amount = 1;
        this.enchantments = null;
    }

    public SkyKitItemConfig(Material material, int amount) {
        Validate.notNull(material, "Material cannot be null");
        this.material = material;
        if (amount > material.getMaxStackSize()) {
            throw new IllegalArgumentException("Material " + material + " can't have a stack size of " + amount + ". It is too high.");
        } else if (amount < 0) {
            throw new IllegalArgumentException("Amount can't be negative.");
        }
        this.amount = amount;
        this.enchantments = null;
    }

    public SkyKitItemConfig(Material material, int amount, Map<Enchantment, Integer> enchantments) {
        Validate.notNull(material, "Material cannot be null");
        this.material = material;
        if (amount > material.getMaxStackSize()) {
            throw new IllegalArgumentException("Material " + material + " can't have a stack size of " + amount + ". It is too high.");
        } else if (amount < 0) {
            throw new IllegalArgumentException("Amount can't be negative.");
        }
        this.amount = amount;
        this.enchantments = enchantments;
    }

    @Override
    public ItemStack toItem() {
        ItemStack itemStack = new ItemStack(material, amount);
        if (enchantments != null) {
            itemStack.addEnchantments(enchantments);
        }
        return itemStack;
    }

    @Override
    public String toString() {
        return "SkyKitItemConfig{" +
                "material=" + material +
                ", amount=" + amount +
                ", enchantments=" + enchantments +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SkyKitItemConfig)) return false;

        SkyKitItemConfig config = (SkyKitItemConfig) o;

        if (amount != config.amount) return false;
        if (enchantments != null ? !enchantments.equals(config.enchantments) : config.enchantments != null)
            return false;
        if (material != config.material) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = material.hashCode();
        result = 31 * result + amount;
        result = 31 * result + (enchantments != null ? enchantments.hashCode() : 0);
        return result;
    }
}
