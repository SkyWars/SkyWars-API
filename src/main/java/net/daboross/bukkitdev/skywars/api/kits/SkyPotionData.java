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

        return new SkyPotionData(item.getDurability());
    }

    private final FullPotionType potionType;
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
        this.potionType = FullPotionType.fromBukkit(data.getType());
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

    public SkyPotionData(FullPotionType potionType, boolean extended, boolean upgraded, boolean splash, boolean lingering) {
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
        FullPotionType type;
        // first four bits determine type
        switch (rawData & 15) {
            case 1:
                type = FullPotionType.REGEN;
                break;
            case 2:
                type = FullPotionType.SPEED;
                break;
            case 3:
                type = FullPotionType.FIRE_RESISTANCE;
                break;
            case 4:
                type = FullPotionType.POISON;
                break;
            case 5:
                type = FullPotionType.INSTANT_HEAL;
                break;
            case 6:
                type = FullPotionType.NIGHT_VISION;
                break;
            case 8:
                type = FullPotionType.WEAKNESS;
                break;
            case 9:
                type = FullPotionType.STRENGTH;
                break;
            case 10:
                type = FullPotionType.SLOWNESS;
                break;
            case 11:
                type = FullPotionType.JUMP;
                break;
            case 12:
                type = FullPotionType.INSTANT_DAMAGE;
                break;
            case 13:
                type = FullPotionType.WATER_BREATHING;
                break;
            case 14:
                type = FullPotionType.INVISIBILITY;
                break;
            default:
                switch (rawData) {
                    case 0:
                        type = FullPotionType.WATER;
                        break;
                    case 16:
                        type = FullPotionType.AWKWARD;
                        break;
                    case 32:
                        type = FullPotionType.THICK;
                        break;
                    case 64:
                    case 8192:
                        type = FullPotionType.MUNDANE;
                        break;
                    default:
                        // This is an invalid potion! Make it water.
                        type = FullPotionType.WATER;
                }
        }
        boolean upgraded = (rawData & 32) == 32;
        boolean extended = (rawData & 64) == 64;
        // Technically we should check `((rawData & 8192) == 8192)` to detect a non-splash potion, but
        // since splash is only recorded as a boolean in the new API, we can get away with this.
        boolean splash = (rawData & 16384) == 16384;
        this.potionType = type;
        this.upgraded = upgraded && type.upgradeable;
        this.extended = extended && type.extendable;
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
        PotionData bukkitData = new PotionData(potionType.toBukkit(), extended, upgraded);
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
        if (upgraded) {
            rawData |= 32;
        }
        if (extended) {
            rawData |= 64;
        }
        if (!(potionType == FullPotionType.WATER
                || potionType == FullPotionType.AWKWARD
                || potionType == FullPotionType.THICK
                || potionType == FullPotionType.MUNDANE)) {
            if (splash) {
                rawData |= 16384;
            } else {
                // The special cases can't be this.
                rawData |= 8192;
            }
        }

        item.setDurability((short) rawData);
    }

    public boolean isExtended() {
        return extended;
    }

    public boolean isLingering() {
        return lingering;
    }

    public FullPotionType getPotionType() {
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

    /**
     * This is an enum that is available on all server versions, which mimics the Bukkit {@link
     * org.bukkit.potion.PotionType} class.
     * <p>
     * Even though PotionType is available both pre-1.9 and post-1.9, this is needed in order to access a full range of
     * allowed names, which don't all exist in pre-1.9 bukkit.
     */
    public enum FullPotionType {
        UNCRAFTABLE(false, false),
        WATER(false, false),
        MUNDANE(false, false),
        THICK(false, false),
        AWKWARD(false, false),
        NIGHT_VISION(false, true),
        INVISIBILITY(false, true),
        JUMP(true, true),
        FIRE_RESISTANCE(false, true),
        SPEED(true, true),
        SLOWNESS(false, true),
        WATER_BREATHING(false, true),
        INSTANT_HEAL(true, false),
        INSTANT_DAMAGE(true, false),
        POISON(true, true),
        REGEN(true, true),
        STRENGTH(true, true),
        WEAKNESS(false, true),
        LUCK(false, false);

        public final boolean upgradeable;
        public final boolean extendable;

        FullPotionType(boolean upgradeable, boolean extendable) {
            this.upgradeable = upgradeable;
            this.extendable = extendable;
        }

        public static FullPotionType fromBukkit(PotionType bukkitType) {
            switch (bukkitType) {
                case WATER:
                    return FullPotionType.WATER;
                case NIGHT_VISION:
                    return FullPotionType.NIGHT_VISION;
                case INVISIBILITY:
                    return FullPotionType.INVISIBILITY;
                case JUMP:
                    return FullPotionType.JUMP;
                case FIRE_RESISTANCE:
                    return FullPotionType.FIRE_RESISTANCE;
                case SPEED:
                    return FullPotionType.SPEED;
                case SLOWNESS:
                    return FullPotionType.SLOWNESS;
                case WATER_BREATHING:
                    return FullPotionType.WATER_BREATHING;
                case INSTANT_HEAL:
                    return FullPotionType.INSTANT_HEAL;
                case INSTANT_DAMAGE:
                    return FullPotionType.INSTANT_DAMAGE;
                case POISON:
                    return FullPotionType.POISON;
                case REGEN:
                    return FullPotionType.REGEN;
                case STRENGTH:
                    return FullPotionType.STRENGTH;
                case WEAKNESS:
                    return FullPotionType.WEAKNESS;
                default:
                    if (modernApiSupported) {
                        switch (bukkitType) {
                            case LUCK:
                                return FullPotionType.LUCK;
                            case UNCRAFTABLE:
                                return FullPotionType.UNCRAFTABLE;
                            case MUNDANE:
                                return FullPotionType.MUNDANE;
                            case THICK:
                                return FullPotionType.THICK;
                            case AWKWARD:
                                return FullPotionType.AWKWARD;
                            default:
                                Bukkit.getLogger().log(Level.WARNING, "[SkyWars] Failed to find potion type for {0}", bukkitType);
                                return FullPotionType.WATER;
                        }
                    } else {
                        Bukkit.getLogger().log(Level.WARNING, "[SkyWars] Failed to find potion type for {0} (excluded new potion types post-1.9)", bukkitType);
                        return FullPotionType.WATER;
                    }
            }
        }

        public static FullPotionType fromString(String str) {
            switch (str.toLowerCase().replaceAll("( |-)", "_")) {
                case "uncraftable":
                    return UNCRAFTABLE;
                case "water":
                    return WATER;
                case "mundane":
                    return MUNDANE;
                case "thick":
                    return THICK;
                case "awkward":
                    return AWKWARD;
                case "night_vision":
                    return NIGHT_VISION;
                case "invisibility":
                    return INVISIBILITY;
                case "jump":
                    return JUMP;
                case "fire_resistance":
                    return FIRE_RESISTANCE;
                case "speed":
                    return SPEED;
                case "slowness":
                    return SLOWNESS;
                case "water_breathing":
                    return WATER_BREATHING;
                case "instant_heal":
                    return INSTANT_HEAL;
                case "instant_damage":
                    return INSTANT_DAMAGE;
                case "poison":
                    return POISON;
                case "regen":
                    return REGEN;
                case "strength":
                    return STRENGTH;
                case "weakness":
                    return WEAKNESS;
                case "luck":
                    return LUCK;
                // Names from PotionEffectTypes
                case "harm":
                    return INSTANT_DAMAGE;
                case "regeneration":
                    return REGEN;
                case "heal":
                    return INSTANT_HEAL;
                case "increase_damage":
                    return STRENGTH;
                case "slow":
                    return SLOWNESS;
                default:
                    throw new IllegalArgumentException("Unknown potion type!");
            }
        }

        public PotionType toBukkit() {
            switch (this) {
                case WATER:
                    return PotionType.WATER;
                case NIGHT_VISION:
                    return PotionType.NIGHT_VISION;
                case INVISIBILITY:
                    return PotionType.INVISIBILITY;
                case JUMP:
                    return PotionType.JUMP;
                case FIRE_RESISTANCE:
                    return PotionType.FIRE_RESISTANCE;
                case SPEED:
                    return PotionType.SPEED;
                case SLOWNESS:
                    return PotionType.SLOWNESS;
                case WATER_BREATHING:
                    return PotionType.WATER_BREATHING;
                case INSTANT_HEAL:
                    return PotionType.INSTANT_HEAL;
                case INSTANT_DAMAGE:
                    return PotionType.INSTANT_DAMAGE;
                case POISON:
                    return PotionType.POISON;
                case REGEN:
                    return PotionType.REGEN;
                case STRENGTH:
                    return PotionType.STRENGTH;
                case WEAKNESS:
                    return PotionType.WEAKNESS;
                default:
                    if (modernApiSupported) {
                        switch (this) {
                            case UNCRAFTABLE:
                                return PotionType.UNCRAFTABLE;
                            case LUCK:
                                return PotionType.LUCK;
                            case MUNDANE:
                                return PotionType.MUNDANE;
                            case THICK:
                                return PotionType.THICK;
                            case AWKWARD:
                                return PotionType.AWKWARD;
                        }
                    }
                    // This should never happen, but return water just in case.
                    return PotionType.WATER;
            }
        }
    }
}
