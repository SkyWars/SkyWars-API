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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.daboross.bukkitdev.skywars.api.kits.impl.SkyExtraEffectsMeta;
import net.daboross.bukkitdev.skywars.api.kits.impl.SkyKitConfig;
import net.daboross.bukkitdev.skywars.api.kits.impl.SkyKitItemConfig;
import net.daboross.bukkitdev.skywars.api.kits.impl.SkyPotionMeta;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class SkyKitBukkitDecode {

    public static SkyKit inventoryToKit(PlayerInventory inventory, String name, String permission, int cost) {
        SkyKitItem[] armor = decodeArmor(inventory.getArmorContents());
        ItemStack[] rawItems = inventory.getContents();
        List<SkyKitItem> items = new ArrayList<SkyKitItem>(rawItems.length);
        for (ItemStack rawItem : rawItems) {
            if (rawItem != null && rawItem.getType() != Material.AIR) {
                items.add(decodeItem(rawItem));
            }
        }
        return new SkyKitConfig(items, Arrays.asList(armor), name, cost, permission);
    }

    private static SkyKitItem[] decodeArmor(ItemStack[] itemStacks) {
        SkyKitItem[] results = new SkyKitItem[4];
        for (int i = 0; i < 4; i++) {
            if (itemStacks[i] != null && itemStacks[i].getType() != Material.AIR) {
                results[i] = decodeItem(itemStacks[i]);
            }
        }
        return results;
    }

    public static SkyKitItem decodeItem(ItemStack itemStack) {
        Material type = itemStack.getType();
        int amount = itemStack.getAmount();
        Map<Enchantment, Integer> enchantments = itemStack.getEnchantments();
        List<SkyItemMeta> skyMetaList = new ArrayList<SkyItemMeta>();
        if (type == Material.POTION) {
            Potion potion = Potion.fromItemStack(itemStack);
            if (potion.getType() != null && potion.getType() != PotionType.WATER) {
                skyMetaList.add(new SkyPotionMeta(potion));
            }
            PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
            if (potionMeta.hasCustomEffects()) {
                skyMetaList.add(new SkyExtraEffectsMeta(potionMeta.getCustomEffects()));
            }
        }
        return new SkyKitItemConfig(type, amount, enchantments, skyMetaList);
    }
}
