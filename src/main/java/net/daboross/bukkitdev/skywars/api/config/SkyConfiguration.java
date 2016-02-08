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
package net.daboross.bukkitdev.skywars.api.config;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import net.daboross.bukkitdev.skywars.api.arenaconfig.SkyArenaConfig;
import org.bukkit.configuration.InvalidConfigurationException;

public interface SkyConfiguration {

    /**
     * Reloads the current configuration from file. This method failing may leave SkyWars in an unstable state.
     *
     * @throws IOException                   If an IOException is thrown by any of the underlying configurations.
     * @throws InvalidConfigurationException If the configuration file is invalid YAML.
     * @throws SkyConfigurationException     If a configuration value is not of the correct type, or if there are other
     *                                       errors in the configuration.
     */
    public void reload() throws IOException, InvalidConfigurationException, SkyConfigurationException;

    /**
     * Gets the folder where arenas are stored.
     *
     * @return The folder where arena files are stored.
     */
    public Path getArenaFolder();

    /**
     * Gets the order which arenas should be put out.
     *
     * @return The ArenaOrder in which arenas will be chosen.
     */
    public ArenaOrder getArenaOrder();

    /**
     * Gets the message prefix to prefix to all game messages.
     *
     * @return The message prefix to prefix to all game messages.
     */
    public String getMessagePrefix();

    /**
     * Gets whether inventory saving will be enabled.
     *
     * @return Whether or not to enable inventory saving.
     */
    public boolean isInventorySaveEnabled();

    /**
     * Gets whether or not to enable score storage.
     *
     * @return Whether or not to enable score.
     */
    public boolean isEnableScore();

    /**
     * Gets the amount of score to add a player's score when they die.
     *
     * @return The amount of score to add to a player's score when they die.
     */
    public int getDeathScoreDiff();

    /**
     * Gets the amount of score to add to a player's score when they win.
     *
     * @return The amount of score to add to a player's score when they win a game.
     */
    public int getWinScoreDiff();

    /**
     * Gets the amount of score to add to a player's score when they kill someone.
     *
     * @return The amount of score to add to a player's score when they kill a player inside a game.
     */
    public int getKillScoreDiff();

    public boolean isScoreUseSql();

    public String getScoreSqlHost();

    public int getScoreSqlPort();

    public String getScoreSqlDatabase();

    public String getScoreSqlUsername();

    public String getScoreSqlPassword();

    /**
     * Gets the interval that score is to be saved on. This is in seconds.
     *
     * @return The score save interval.
     */
    public long getScoreSaveInterval();

    /**
     * Gets whether or not to enable economy rewards.
     *
     * @return Whether or not to reward people with economy currency.
     */
    public boolean isEconomyEnabled();

    /**
     * Gets the amount of currency to add to someone's economy account when they win a game.
     *
     * @return The win reward.
     */
    public int getEconomyWinReward();

    /**
     * Gets the amount of currency to add to someone's economy account when they kill someone in a game.
     *
     * @return The kill reward.
     */
    public int getEconomyKillReward();

    /**
     * Gets whether or not to give in-game messages about economy rewards.
     *
     * @return Whether or not to message for economy rewards.
     */
    public boolean areEconomyRewardMessagesEnabled();

    /**
     * Gets the distance between arenas when created. This distance is from one arena's minimum corner to the next
     * arena's minimum corner.
     *
     * @return The distance between created arenas.
     */
    public int getArenaDistanceApart();

    /**
     * Gets all enabled arenas.
     *
     * @return A list of SkyArenaConfigs that the next arena will be chosen from.
     */
    public List<SkyArenaConfig> getEnabledArenas();

    /**
     * Save the given arena to file.
     *
     * @param config The arena to save to file.
     */
    public void saveArena(SkyArenaConfig config);

    /**
     * Gets whether or not command whitelist is enabled.
     *
     * @return Whether or not in-game commands will be whitelisted / blacklisted.
     */
    public boolean isCommandWhitelistEnabled();

    /**
     * Gets whether or not the command whitelist is treated as a blacklist.
     *
     * @return Whether or not the command whitelist is a blacklist.
     */
    public boolean isCommandWhitelistABlacklist();

    /**
     * Gets a command regex that will match all commands in the command whitelist.
     *
     * @return The command regex.
     */
    public Pattern getCommandWhitelistCommandRegex();

    /**
     * Gets the chosen locale that should be used for picking translation strings.
     *
     * @return The chosen locale.
     */
    public String getLocale();

    /**
     * Gets whether or not the user has disabled reporting.
     *
     * @return True if the user has disabled /sw report, false otherwise.
     */
    public boolean isDisableReport();
//    /**
//     * Gets whether or not death messages should only be broadcasted to people
//     * in the arena.
//     *
//     * @return whether or not per arena death messages are enabled.
//     */
//    public boolean arePerArenaDeathMessagesEnabled();

//    /**
//     * Gets whether or not win messages should only be broadcasted to people in
//     * the arena.
//     *
//     * @return whether or not per arena win messages are enabled.
//     */
//    public boolean arePerArenaWinMessagesEnabled();

    /**
     * Whether or not to attempt to hook with Multiverse Core.
     *
     * @return False if the user has disabled the Multiverse Core hook, true otherwise.
     */
    public boolean isMultiverseCoreHookEnabled();

    /**
     * Whether or not to attempt to hook with Multiverse Inventories.
     *
     * @return False if the user has disabled the Multiverse Inventories hook, true otherwise.
     */
    public boolean isMultiverseInventoriesHookEnabled();

    /**
     * Whether or not to attempt to hook with WorldEdit.
     *
     * @return False if the user has disabled the WorldEdit hook, true otherwise.
     */
    public boolean isWorldeditHookEnabled();

    /**
     * Whether or not to skip checking for uuid support.
     *
     * @return True if the user has disabled the uuid check, false otherwise.
     */
    public boolean isSkipUuidCheck();

    /**
     * An Enum to record the order that arenas are chosen.
     */
    public static enum ArenaOrder {

        RANDOM, ORDERED;
        private static final Map<String, ArenaOrder> BY_NAME = new HashMap<String, ArenaOrder>(2);

        static {
            for (ArenaOrder order : values()) {
                BY_NAME.put(order.name().toLowerCase(Locale.ENGLISH), order);
            }
        }

        /**
         * Gets an arena order by name.
         *
         * @param name The name of the ArenaOrder, case insensitive.
         * @return The ArenaOrder identified by the given name, or null if not found.
         */
        public static ArenaOrder getOrder(String name) {
            return BY_NAME.get(name.toLowerCase(Locale.ENGLISH));
        }
    }
}
