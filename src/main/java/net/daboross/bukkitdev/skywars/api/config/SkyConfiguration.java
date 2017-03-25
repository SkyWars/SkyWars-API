/*
 * Copyright (C) 2013-2016 Dabo Ross <http://www.daboross.net/>
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
    void reload() throws IOException, InvalidConfigurationException, SkyConfigurationException;

    /**
     * Gets the folder where arenas are stored.
     *
     * @return The folder where arena files are stored.
     */
    Path getArenaFolder();

    /**
     * Gets the order which arenas should be put out.
     *
     * @return The ArenaOrder in which arenas will be chosen.
     */
    ArenaOrder getArenaOrder();

    /**
     * Gets the message prefix to prefix to all game messages.
     *
     * @return The message prefix to prefix to all game messages.
     */
    String getMessagePrefix();

    /**
     * Gets whether inventory saving will be enabled.
     *
     * @return Whether or not to enable inventory saving.
     */
    boolean isInventorySaveEnabled();

    /**
     * Gets whether experience saving will be enabled.
     *
     * @return Whether or not to enable inventory saving.
     */
    boolean isExperienceSaveEnabled();

    /**
     * Gets whether position, gamemode, health (and hunger) saving will be enabled.
     *
     * @return Whether or not to enable PGH saving.
     */
    boolean isPghSaveEnabled();

    /**
     * Gets whether or not to enable score storage.
     *
     * @return Whether or not to enable score.
     */
    boolean isEnableScore();

    /**
     * Get gamerules to be set in the arena world
     *
     * @return A map from game rule name to game rule value.
     */
    Map<String, String> getArenaGamerules();

    /**
     * Gets the amount of score to add a player's score when they die.
     *
     * @return The amount of score to add to a player's score when they die.
     */
    int getDeathScoreDiff();

    /**
     * Gets the amount of score to add to a player's score when they win.
     *
     * @return The amount of score to add to a player's score when they win a game.
     */
    int getWinScoreDiff();

    /**
     * Gets the amount of score to add to a player's score when they kill someone.
     *
     * @return The amount of score to add to a player's score when they kill a player inside a game.
     */
    int getKillScoreDiff();

    boolean isScoreUseSql();

    String getScoreSqlHost();

    int getScoreSqlPort();

    String getScoreSqlDatabase();

    String getScoreSqlUsername();

    String getScoreSqlPassword();

    /**
     * Gets the interval that score is to be saved on. This is in seconds.
     *
     * @return The score save interval, in seconds.
     */
    long getScoreSaveInterval();

    /**
     * Gets the interval that individual player's (not top 10) rank should be updated at. This is in seconds.
     *
     * @return The individual rank update interval, in seconds.
     */
    long getScoreIndividualRankUpdateInterval();

    /**
     * Gets whether or not to enable economy rewards.
     *
     * @return Whether or not to reward people with economy currency.
     */
    boolean isEconomyEnabled();

    /**
     * Gets the amount of currency to add to someone's economy account when they win a game.
     *
     * @return The win reward.
     */
    int getEconomyWinReward();

    /**
     * Gets the amount of currency to add to someone's economy account when they kill someone in a game.
     *
     * @return The kill reward.
     */
    int getEconomyKillReward();

    /**
     * Gets whether or not to give in-game messages about economy rewards.
     *
     * @return Whether or not to message for economy rewards.
     */
    boolean areEconomyRewardMessagesEnabled();

    /**
     * Gets the distance between arenas when created. This distance is from one arena's minimum corner to the next
     * arena's minimum corner.
     *
     * @return The distance between created arenas.
     */
    int getArenaDistanceApart();

    /**
     * Gets all enabled arenas.
     *
     * @return A list of SkyArenaConfigs that the next arena will be chosen from.
     */
    List<SkyArenaConfig> getEnabledArenas();

    /**
     * Save the given arena to file.
     *
     * @param config The arena to save to file.
     */
    void saveArena(SkyArenaConfig config);

    /**
     * Gets whether or not command whitelist is enabled.
     *
     * @return Whether or not in-game commands will be whitelisted / blacklisted.
     */
    boolean isCommandWhitelistEnabled();

    /**
     * Gets whether or not the command whitelist is treated as a blacklist.
     *
     * @return Whether or not the command whitelist is a blacklist.
     */
    boolean isCommandWhitelistABlacklist();

    /**
     * Gets a command regex that will match all commands in the command whitelist.
     *
     * @return The command regex.
     */
    Pattern getCommandWhitelistCommandRegex();

    /**
     * Gets the chosen locale that should be used for picking translation strings.
     *
     * @return The chosen locale.
     */
    String getLocale();

    /**
     * Gets whether or not the user has disabled reporting.
     *
     * @return True if the user has disabled /sw report, false otherwise.
     */
    boolean isDisableReport();

    /**
     * Gets whether or not arena start messages should only be broadcasted to people in the arena.
     *
     * @return whether or not to limit start messages to queued players.
     */
    boolean shouldLimitStartMessagesToArenaPlayers();

    /**
     * Gets whether or not death messages should only be broadcasted to people in the arena.
     *
     * @return whether or not limit death messages to arena players.
     */
    boolean shouldLimitDeathMessagesToArenaPlayers();

    /**
     * Gets whether or not win messages should only be broadcasted to people in the arena.
     *
     * @return whether or not to limit win messages to arena players.
     */
    boolean shouldLimitEndMessagesToArenaPlayers();

    /**
     * Gets whether or not start timer messages should only be broadcasted to people in the arena.
     *
     * @return wheter or not to limit "starting in x seconds" messages to queued players.
     */
    boolean shouldLimitStartTimerMessagesToArenaPlayers();

    /**
     * Whether or not to attempt to hook with Multiverse Core.
     *
     * @return False if the user has disabled the Multiverse Core hook, true otherwise.
     */
    boolean isMultiverseCoreHookEnabled();

    /**
     * Whether or not SkyWars should attempt to find MultiInv, and if found, enable the workaround.
     *
     * @return False if the user has disabled the workaround, true otherwise.
     */
    boolean isMultiinvWorkaroundPossible();

    /**
     * Whether or not SkyWars should force the MultiInv workaround even if not found.
     *
     * @return True if the user has forced the workaround, false otherwise.
     */
    boolean isMultiinvWorkaroundForced();

    /**
     * Whether or not to attempt to hook with WorldEdit.
     *
     * @return False if the user has disabled the WorldEdit hook, true otherwise.
     */
    boolean isWorldeditHookEnabled();

    /**
     * Whether or not to skip checking for uuid support.
     *
     * @return True if the user has disabled the uuid check, false otherwise.
     */
    boolean isSkipUuidCheck();

    /**
     * Whether or not to force respawn players immediately after they die.
     *
     * @return True if the user has enabled skipping the respawn screen, false otherwise.
     */
    boolean isRespawnPlayersImmediately();

    /**
     * Whether or not a few options which should only be enabled when developing are enabled.
     *
     * @return True if developer options should be enabled, false otherwise.
     */
    boolean areDeveloperOptionsEnabled();

    /**
     * Whether or not SkyWars should attempt to recover from score/scoreboard errors.
     *
     * @return True if score board error recovery hasn't been disabled, false otherwise.
     */
    boolean isRecoverFromScoreErrors();

    /**
     * Returns a list of sign lines to put on join signs.
     *
     * @return An array of four strings, all non-null.
     */
    String[] getJoinSignLines();

    /**
     * Whether or not to show unavailable kits in the kit inventory gui.
     *
     * @return True if unavailable kits should be shown, false otherwise.
     */
    boolean isShowUnavailableKitsInGui();

    /**
     * Whether or not to completely replace the `/sw kit` command with a kit gui.
     *
     * @return True if `/sw kit` should directly launch a kit gui, false if `/sw kit gui` should be required.
     */
    boolean isReplaceKitCommandWithGui();

    /**
     * Whether or not to show the kit gui when a player joins the queue.
     *
     * @return True if kit gui should automatically be shown on join, false otherwise.
     */
    boolean isShowKitGuiOnJoin();

    /**
     * Whether or not to output debug output. Generally SkyStatic.debug() is used instead of this, but this can be used
     * if creating a debug string is an expensive operation, or to pass to libraries as a debug variable.
     */
    boolean isDebug();

    /**
     * Whether or not plugin-statistics should be enabled.
     *
     * @see <a href='https://github.com/daboross/plugin-statistics'>https://github.com/daboross/plugin-statistics</a>
     */
    boolean isReportPluginStatistics();

    /**
     * The time till a game starts after the maximum number of players have joined the game.
     *
     * @return The time to wait until starting a game, in seconds.
     */
    long getTimeTillStartAfterMaxPlayers();

    /**
     * The time till a game starts after the last player has joined, and there are at least the minimum number of
     * players.
     *
     * @return The time to wait until starting a game, in seconds.
     */
    long getTimeTillStartAfterMinPlayers();

    /**
     * The time before a game start to freeze map votes, and to copy the arena. This is currently not used.
     *
     * @return The time before a game starts to copy the arena, in seconds.
     */
    long getTimeBeforeGameStartToCopyArena();

    /**
     * The time after players are teleported into an arena (game start) before they are allowed to move.
     *
     * @return How long to freeze players after teleportation, in seconds.
     */
    long getInGamePlayerFreezeTime();

    /**
     * A list of times in seconds to broadcast a "starting in x seconds" message.
     *
     * @return A list of times before game start, in seconds.
     */
    List<Long> getStartTimerMessageTimes();

    /**
     * Get the arena copying "block size". This is the number of blocks to copy at once in multi-part arena copying
     * operations.
     *
     * @return The block size for arena copying and clearing.
     */
    int getArenaCopyingBlockSize();

    /**
     * An Enum to record the order that arenas are chosen.
     */
    enum ArenaOrder {

        RANDOM, ORDERED;
        private static final Map<String, ArenaOrder> BY_NAME = new HashMap<>(2);

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
