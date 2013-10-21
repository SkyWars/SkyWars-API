/*
 * Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SkyMessageKeys {

    public static final String KILLED_VOID = "death.killed-void";
    public static final String SUICIDE_VOID = "death.suicide-void";
    public static final String KILLED_DAMAGED = "death.killed-damaged";
    public static final String KILLED_OTHER = "death.killed-other";
    public static final String FORFEITED_DAMAGED = "death.forfeited-damaged";
    public static final String FORFEITED = "death.forfeited";
    public static final String SINGLE_WON = "winning.single-won";
    public static final String MULTI_WON = "winning.multi-won";
    public static final String NONE_WON = "winning.none-won";
    public static final String GAME_STARTING = "game.game-starting";
    public static final List<String> ALL_MESSAGES = Collections.unmodifiableList(Arrays.asList(
            KILLED_VOID, SUICIDE_VOID, KILLED_DAMAGED, KILLED_OTHER, FORFEITED_DAMAGED, FORFEITED, SINGLE_WON, MULTI_WON, NONE_WON, GAME_STARTING
    ));
}
