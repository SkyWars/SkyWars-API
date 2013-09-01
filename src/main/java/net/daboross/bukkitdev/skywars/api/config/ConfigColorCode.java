/*
 * Copyright (C) 2013 daboross
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
package net.daboross.bukkitdev.skywars.api.config;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;

/**
 *
 * @author daboross
 */
public enum ConfigColorCode {

    REG('r', '3'),
    NAME('n', '2'),
    BROADCAST('b', 'a'),
    DATA('d', '2');
    private static final Map<Character, ConfigColorCode> BY_SHORTVER = new HashMap<Character, ConfigColorCode>();
    private final char code;
    private final char shortVer;
    private final String color;

    private ConfigColorCode(char shortVer, char code) {
        this.shortVer = shortVer;
        this.code = code;
        this.color = String.valueOf(new char[]{ChatColor.COLOR_CHAR, code});
    }

    @Override
    public String toString() {
        return color;
    }

    public static ConfigColorCode getByShortVer(char shortVer) {
        return BY_SHORTVER.get(Character.toLowerCase(shortVer));
    }

    public static String translateCodes(String input) {
        char[] array = input.toCharArray();
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] == '#' && "rRnNbBdD".indexOf(array[i + 1]) > -1) {
                array[i] = ChatColor.COLOR_CHAR;
                array[i + 1] = getByShortVer(array[i + 1]).code;
            }
        }
        return String.valueOf(array);
    }

    static {
        for (ConfigColorCode code : values()) {
            BY_SHORTVER.put(code.shortVer, code);
        }
    }
}
