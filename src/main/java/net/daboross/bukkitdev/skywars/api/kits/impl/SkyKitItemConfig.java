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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.daboross.bukkitdev.skywars.api.kits.SkyItemMeta;
import net.daboross.bukkitdev.skywars.api.kits.SkyKitItem;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class SkyKitItemConfig implements SkyKitItem {

    private final Material material;
    private final int amount;
    private final Map<Enchantment, Integer> enchantments;
    private final List<SkyItemMeta> itemMeta;

    public SkyKitItemConfig(Material material, int amount, Map<Enchantment, Integer> enchantments, final List<SkyItemMeta> itemMeta) {
        Validate.notNull(material, "Material cannot be null");
        this.material = material;
//        if (amount > material.getMaxStackSize()) {
//            throw new IllegalArgumentException("Material " + material + " can't have a stack size of " + amount + ". It is too high.");
//        } else
        if (amount < 0) {
            throw new IllegalArgumentException("Amount can't be negative.");
        }
        this.amount = amount;
        this.enchantments = enchantments != null && enchantments.isEmpty() ? null : enchantments;
        this.itemMeta = itemMeta == null ? new ArrayList<SkyItemMeta>() : new ArrayList<>(itemMeta);
        Collections.sort(this.itemMeta);
    }

    @Override
    public ItemStack toItem() {
        ItemStack itemStack = new ItemStack(material, amount);
        if (enchantments != null) {
            itemStack.addUnsafeEnchantments(enchantments);
        }
        for (SkyItemMeta meta : itemMeta) {
            meta.applyToItem(itemStack);
        }
        return itemStack;
    }

    @Override
    public String toString() {
        return "SkyKitItemConfig{" +
                "amount=" + amount +
                ", material=" + material +
                ", enchantments=" + enchantments +
                ", itemMeta=" + itemMeta +
                '}';
    }

    @Override
    @SuppressWarnings("RedundantIfStatement")
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SkyKitItemConfig)) return false;

        SkyKitItemConfig config = (SkyKitItemConfig) o;

        if (amount != config.amount) return false;
        if (material != config.material) return false;
        if (enchantments != null ? !enchantments.equals(config.enchantments) : config.enchantments != null)
            return false;
        if (itemMeta != null ? !itemMeta.equals(config.itemMeta) : config.itemMeta != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = material != null ? material.hashCode() : 0;
        result = 31 * result + amount;
        result = 31 * result + (enchantments != null ? enchantments.hashCode() : 0);
        result = 31 * result + (itemMeta != null ? itemMeta.hashCode() : 0);
        return result;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments == null ? null : Collections.unmodifiableMap(enchantments);
    }

    @Override
    public List<SkyItemMeta> getItemMeta() {
        return itemMeta == null ? null : Collections.unmodifiableList(itemMeta);
    }
}
