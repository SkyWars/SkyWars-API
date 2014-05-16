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
package net.daboross.bukkitdev.skywars.api.economy;

import java.util.UUID;
import org.bukkit.OfflinePlayer;

public interface SkyEconomyAbstraction {

    public String getCurrencySymbolWord(double amount);

    public String getCurrencySymbol(double amount);

    public void addReward(OfflinePlayer player, double reward);

    public void addReward(UUID playerUuid, double reward);

    public boolean canAfford(OfflinePlayer player, double amount);

    public boolean canAfford(UUID playerUuid, double amount);

    public boolean charge(OfflinePlayer player, double amount);

    public boolean charge(UUID playerUuid, double amount);

    public double getAmount(OfflinePlayer player);

    public double getAmount(UUID playerUuid);
}
