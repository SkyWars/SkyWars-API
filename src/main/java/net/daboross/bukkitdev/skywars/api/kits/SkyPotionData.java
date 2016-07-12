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

import java.util.logging.Level;
import net.daboross.bukkitdev.skywars.api.SkyStatic;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

/**
 * Class to manage potions across multiple Minecraft versions.
 */
public class SkyPotionData {

    public static final boolean modernApiSupported;

    static {
        boolean tempSupported;
        try {
            Class.forName("org.bukkit.potion.PotionData");
            tempSupported = true;
        } catch (ClassNotFoundException ignored) {
            tempSupported = false;
        }
        modernApiSupported = tempSupported;
    }

    public static SkyPotionData extractData(ItemStack item) {
        if (modernApiSupported) {
            return getDataModernApi(item);
        } else {
            return getDataRawData(item);
        }
    }

    private static SkyPotionData getDataModernApi(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        Validate.isTrue(meta instanceof PotionMeta);
        return new SkyPotionData(item.getType(), (PotionMeta) meta);
    }

    @SuppressWarnings("deprecation")
    private static SkyPotionData getDataRawData(ItemStack item) {
        Validate.isTrue(item.getType() == Material.POTION);

        return new SkyPotionData(item.getData().getData());
    }

    private final PotionType potionType;
    private final boolean extended;
    private final boolean upgraded;
    private final boolean splash;
    private final boolean lingering;

    /**
     * Creates a modern potion data storage from the PotionMeta of an existing item. This method could take a plain
     * ItemStack, but in most cases when it's actually used it is more useful to be checking if meta instanceof
     * PotionMeta before the ModernPotionData is constructed, so the constructor has this form instead.
     *
     * @param meta The potion metadata to store as modern cross data storage.
     */
    public SkyPotionData(Material type, PotionMeta meta) {
        PotionData data = meta.getBasePotionData();
        this.potionType = data.getType();
        this.extended = data.isExtended();
        this.upgraded = data.isUpgraded();
        switch (type) {
            case POTION:
                this.splash = false;
                this.lingering = false;
                break;
            case SPLASH_POTION:
                this.splash = true;
                this.lingering = false;
                break;
            case LINGERING_POTION:
                this.splash = false;
                this.lingering = true;
                break;
            default:
                // What the heck? Still, we should have sensible defaults.
                this.splash = false;
                this.lingering = false;
                SkyStatic.debug("Unknown potion type used to create ModernPotionData: %s", type);
                break;
        }
    }

    public SkyPotionData(PotionType potionType, boolean extended, boolean upgraded, boolean splash, boolean lingering) {
        Validate.notNull(potionType);
        this.potionType = potionType;
        this.extended = extended;
        this.upgraded = upgraded;
        this.splash = splash;
        this.lingering = lingering && !splash; // This class has a fail-last kind of standpoint.
        // If the user tries to do something wrong, do the closest thing which is right.
    }

    /**
     * Converts old raw data potion storage to modern potion data.
     *
     * @param rawData The raw item data, from an item on a pre-1.9 server.
     */
    public SkyPotionData(int rawData) {
        // This is some byte manipulation to get from old data to modern data.
        PotionType type;
        // first four bits determine type
        switch (rawData & 15) {
            case 1:
                type = PotionType.REGEN;
                break;
            case 2:
                type = PotionType.SPEED;
                break;
            case 3:
                type = PotionType.FIRE_RESISTANCE;
                break;
            case 4:
                type = PotionType.POISON;
                break;
            case 5:
                type = PotionType.INSTANT_HEAL;
                break;
            case 6:
                type = PotionType.NIGHT_VISION;
                break;
            case 8:
                type = PotionType.WEAKNESS;
                break;
            case 9:
                type = PotionType.STRENGTH;
                break;
            case 10:
                type = PotionType.SLOWNESS;
                break;
            case 11:
                type = PotionType.JUMP;
                break;
            case 12:
                type = PotionType.INSTANT_DAMAGE;
                break;
            case 13:
                type = PotionType.WATER_BREATHING;
                break;
            case 14:
                type = PotionType.INVISIBILITY;
                break;
            default:
                switch (rawData) {
                    case 0:
                        type = PotionType.WATER;
                        break;
                    case 16:
                        type = PotionType.AWKWARD;
                        break;
                    case 32:
                        type = PotionType.THICK;
                        break;
                    case 64:
                    case 8192:
                        type = PotionType.MUNDANE;
                        break;
                    default:
                        // This is an invalid potion! Make it water.
                        type = PotionType.WATER;
                }
        }
        boolean upgraded = (rawData & 32) == 32;
        boolean extended = (rawData & 64) == 64;
        // Technically we should check `((rawData & 8192) == 8192)` to detect a non-splash potion, but
        // since splash is only recorded as a boolean in the new API, we can get away with this.
        boolean splash = (rawData & 16384) == 16384;
        this.potionType = type;
        this.upgraded = upgraded && type.isUpgradeable();
        this.extended = extended && type.isExtendable();
        this.splash = splash;
        this.lingering = false; // Lingering potions didn't exist pre-1.9 (when this data format was valid).
    }

    public void applyTo(ItemStack item) {
        if (modernApiSupported) {
            applyToModernApi(item);
        } else {
            applyToRawData(item);
        }
    }

    private void applyToModernApi(ItemStack item) {
        if (splash) {
            item.setType(Material.SPLASH_POTION);
        } else if (lingering) {
            item.setType(Material.LINGERING_POTION);
        } else {
            item.setType(Material.POTION);
        }
        ItemMeta bukkitMeta = item.getItemMeta();
        Validate.isTrue(bukkitMeta instanceof PotionMeta, "Cannot apply potion to non-potion item.");
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        PotionData bukkitData = new PotionData(potionType, extended, upgraded);
        meta.setBasePotionData(bukkitData);
        item.setItemMeta(meta);
    }

    @SuppressWarnings("deprecation")
    private void applyToRawData(ItemStack item) {
        if (item.getType() != Material.POTION) {
            throw new IllegalArgumentException("Cannot apply potion to non-potion item");
        }
        int rawData = 0;
        // type:
        switch (potionType) {
            case REGEN:
                rawData |= 1;
                break;
            case SPEED:
                rawData |= 2;
                break;
            case FIRE_RESISTANCE:
                rawData |= 3;
                break;
            case POISON:
                rawData |= 4;
                break;
            case INSTANT_HEAL:
                rawData |= 5;
                break;
            case NIGHT_VISION:
                rawData |= 6;
                break;
            case WEAKNESS:
                rawData |= 8;
                break;
            case STRENGTH:
                rawData |= 9;
                break;
            case SLOWNESS:
                rawData |= 10;
                break;
            case JUMP:
                rawData |= 11;
                break;
            case INSTANT_DAMAGE:
                rawData |= 12;
                break;
            case WATER_BREATHING:
                rawData |= 13;
                break;
            case INVISIBILITY:
                rawData |= 14;
                break;
            // special cases:
            case WATER:
                rawData = 0;
                break;
            case AWKWARD:
                rawData = 16;
                break;
            case THICK:
                rawData = 32;
                break;
            case MUNDANE:
                if (extended) {
                    rawData = 64;
                } else {
                    rawData = 8192;
                }
                break;
            case UNCRAFTABLE:
                rawData |= 64 + 32;
                break;
            default:
                SkyStatic.debug("Couldn't find old-data equivalent for new-data potion type! %s", potionType);
                break;
        }
        boolean upgraded = (rawData & 32) == 32;
        boolean extended = (rawData & 64) == 64;
        // Technically we should check `((rawData & 8192) == 8192)` to detect a non-splash potion, but
        // since splash is only recorded as a boolean in the new API, we can get away with this.
        boolean splash = (rawData & 16384) == 16384;
        if (upgraded) {
            rawData |= 32;
        }
        if (extended) {
            rawData |= 64;
        }
        if (!(potionType == PotionType.WATER
                || potionType == PotionType.AWKWARD
                || potionType == PotionType.THICK
                || potionType == PotionType.MUNDANE)) {
            if (splash) {
                rawData |= 16384;
            } else {
                // The special cases can't be this.
                rawData |= 8192;
            }
        }

        MaterialData data = item.getData();
        data.setData((byte) rawData);
        item.setData(data);
    }

    public boolean isExtended() {
        return extended;
    }

    public boolean isLingering() {
        return lingering;
    }

    public PotionType getPotionType() {
        return potionType;
    }

    public boolean isSplash() {
        return splash;
    }

    public boolean isUpgraded() {
        return upgraded;
    }

    @Override
    public String toString() {
        return "SkyPotionData{" +
                "extended=" + extended +
                ", potionType=" + potionType +
                ", upgraded=" + upgraded +
                ", splash=" + splash +
                ", lingering=" + lingering +
                '}';
    }

    @Override
    @SuppressWarnings("RedundantIfStatement")
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SkyPotionData)) return false;

        SkyPotionData data = (SkyPotionData) o;

        if (extended != data.extended) return false;
        if (upgraded != data.upgraded) return false;
        if (splash != data.splash) return false;
        if (lingering != data.lingering) return false;
        if (potionType != data.potionType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = potionType.hashCode();
        result = 31 * result + (extended ? 1 : 0);
        result = 31 * result + (upgraded ? 1 : 0);
        result = 31 * result + (splash ? 1 : 0);
        result = 31 * result + (lingering ? 1 : 0);
        return result;
    }

}
