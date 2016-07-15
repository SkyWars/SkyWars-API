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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.daboross.bukkitdev.skywars.api.kits.SkyKit;
import net.daboross.bukkitdev.skywars.api.kits.SkyKitItem;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class SkyKitConfig implements SkyKit {

    private final List<SkyKitItem> inventoryContents;
    private final List<SkyKitItem> armorContents;
    private final int cost;
    private final String permission;
    private final String name;
    private final String description;
    private final Material totem;
    private final List<String> displayDescription;

    public SkyKitConfig(List<SkyKitItem> inventoryContents, List<SkyKitItem> armorContents, String name, final String description, final Material totem, int cost, String permissionNode) {
        Validate.notNull(inventoryContents, "Inventory cannot be null");
        Validate.notNull(armorContents, "Armor contents cannot be null");
        Validate.notNull(name, "Name cannot be null");
        Validate.notNull(description, "Description cannot be null");
        Validate.notNull(totem, "Totem cannot be null");
        Validate.isTrue(armorContents.size() == 4, "Armor contents size must be 4");
        Validate.isTrue(cost >= 0);
        this.inventoryContents = inventoryContents;
        this.armorContents = armorContents;
        this.name = name;
        this.description = description.replace(ChatColor.COLOR_CHAR, '&');
        this.displayDescription = Arrays.asList(ChatColor.translateAlternateColorCodes('&', description).split("\n"));
        this.totem = totem;
        this.cost = cost;
        this.permission = permissionNode;
    }

    @Override
    public void applyTo(Player p) {
        PlayerInventory inv = p.getInventory();
        int inventoryLength = inv.getContents().length;
        if (inventoryLength > 36) {
            // In Minecraft versions 1.9+, the inventory "contents" actually also contains armor and the "off hand" item.
            ItemStack[] fullContents = new ItemStack[inv.getContents().length];
            // Hardcoded 36: the number of "storage" slot (not including the armor)
            int numItems = Math.min(36, inventoryContents.size());
            for (int i = 0; i < numItems; i++) {
                SkyKitItem skyKitItem = inventoryContents.get(i);
                if (skyKitItem != null) {
                    fullContents[i] = skyKitItem.toItem();
                }
            }
            // Set the armor contents as the last part of the full contents.
            for (int i = 0; i < armorContents.size(); i++) {
                SkyKitItem skyKitItem = armorContents.get(i);
                if (skyKitItem != null) {
                    fullContents[36 + i] = skyKitItem.toItem();
                }
            }
            inv.setContents(fullContents);
        } else {
            // In versions before 1.9, we need to apply armor and inventory contents separately.
            ItemStack[] armor = new ItemStack[inv.getArmorContents().length];
            for (int i = 0; i < armor.length; i++) {
                SkyKitItem skyKitItem = armorContents.get(i);
                if (skyKitItem != null) {
                    armor[i] = skyKitItem.toItem();
                }
            }
            inv.setArmorContents(armor);

            ItemStack[] contents = new ItemStack[inventoryLength];
            int numItems = Math.min(contents.length, inventoryContents.size());
            for (int i = 0; i < numItems; i++) {
                SkyKitItem skyKitItem = inventoryContents.get(i);
                if (skyKitItem != null) {
                    contents[i] = skyKitItem.toItem();
                }
            }
            inv.setContents(contents);
        }
    }

    @Override
    public List<SkyKitItem> getArmorContents() {
        return Collections.unmodifiableList(armorContents);
    }

    @Override
    public List<SkyKitItem> getInventoryContents() {
        return Collections.unmodifiableList(inventoryContents);
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<String> getDisplayDescription() {
        return displayDescription;
    }

    @Override
    public Material getTotem() {
        return totem;
    }

    @Override
    public String toString() {
        return "SkyKitConfig{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", totem=" + totem +
                (cost == 0 ? "" : (", cost=" + cost)) +
                (permission == null ? "" : (", permission='" + permission + '\'')) +
                ", inventoryContents=" + inventoryContents +
                ", armorContents=" + armorContents +
                '}';
    }

    @Override
    @SuppressWarnings("RedundantIfStatement")
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SkyKitConfig)) return false;

        SkyKitConfig config = (SkyKitConfig) o;

        if (cost != config.cost) return false;
        if (!inventoryContents.equals(config.inventoryContents)) return false;
        if (!armorContents.equals(config.armorContents)) return false;
        if (permission != null ? !permission.equals(config.permission) : config.permission != null) return false;
        if (!name.equals(config.name)) return false;
        if (!description.equals(config.description)) return false;
        if (totem != config.totem) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = inventoryContents.hashCode();
        result = 31 * result + armorContents.hashCode();
        result = 31 * result + cost;
        result = 31 * result + (permission != null ? permission.hashCode() : 0);
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + totem.hashCode();
        return result;
    }
}
