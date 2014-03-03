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
package net.daboross.bukkitdev.skywars.api;

import java.util.List;
import java.util.Random;
import org.apache.commons.lang.Validate;

public class Randomation {

    private static final Random random = new Random();

    /**
     * Gets a random item in a given list.
     *
     * @param <T>  The type of the item and list.
     * @param list The list to get the items from.
     * @return A random item from this list.
     * @throws NullPointerException If list is null.
     */
    public static <T> T getRandom(List<T> list) {
        Validate.notNull(list, "List cannot be null");
        return list.get(random.nextInt(list.size()));
    }
}
