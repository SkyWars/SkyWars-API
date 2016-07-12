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
import net.daboross.bukkitdev.skywars.api.kits.SkyItemMetaType;
import org.bukkit.inventory.ItemStack;

public class SkyRawDataMeta extends SkyItemMeta {

    private final byte data;

    public SkyRawDataMeta(final byte data) {
        this.data = data;
    }

    @Override
    public void applyToItem(final ItemStack item) {
        item.getData().setData(data);
    }

    @Override
    public SkyItemMetaType getType() {
        return SkyItemMetaType.RAW_DATA;
    }

    public byte getData() {
        return data;
    }

    @Override
    public String toString() {
        return "SkyRawDataMeta{" +
                "data=" + data +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SkyRawDataMeta)) return false;

        SkyRawDataMeta meta = (SkyRawDataMeta) o;

        return data == meta.data;
    }

    @Override
    public int hashCode() {
        return (int) data;
    }
}
