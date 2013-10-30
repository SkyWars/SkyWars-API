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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.daboross.bukkitdev.skywars.api.translations.TransKey;

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
    public static final Map<String, TransKey> DEFAULT_KEYS;

    static {
        Map<String, TransKey> tempKeys = new HashMap<String, TransKey>(TransKey.values().length);
        tempKeys.put(KILLED_VOID, TransKey.GAME_DEATH_KILLED_BY_PLAYER_AND_VOID);
        tempKeys.put(SUICIDE_VOID, TransKey.GAME_DEATH_KILLED_BY_VOID);
        tempKeys.put(KILLED_DAMAGED, TransKey.GAME_DEATH_KILLED_BY_PLAYER);
        tempKeys.put(KILLED_OTHER, TransKey.GAME_DEATH_KILLED_BY_ENVIRONMENT);
        tempKeys.put(FORFEITED_DAMAGED, TransKey.GAME_DEATH_FORFEITED_WHILE_ATTACKED);
        tempKeys.put(FORFEITED, TransKey.GAME_DEATH_FORFEITED);
        tempKeys.put(SINGLE_WON, TransKey.GAME_WINNING_SINGLE_WON);
        tempKeys.put(MULTI_WON, TransKey.GAME_WINNING_MULTI_WON);
        tempKeys.put(NONE_WON, TransKey.GAME_WINNING_NONE_WON);
        tempKeys.put(GAME_STARTING, TransKey.GAME_STARTING_GAMESTARTING);
        DEFAULT_KEYS = Collections.unmodifiableMap(tempKeys);
    }
}
