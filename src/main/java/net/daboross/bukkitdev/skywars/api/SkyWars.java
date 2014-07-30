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

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import net.daboross.bukkitdev.skywars.api.config.SkyConfiguration;
import net.daboross.bukkitdev.skywars.api.economy.SkyEconomyAbstraction;
import net.daboross.bukkitdev.skywars.api.game.SkyAttackerStorage;
import net.daboross.bukkitdev.skywars.api.game.SkyCurrentGameTracker;
import net.daboross.bukkitdev.skywars.api.game.SkyGameHandler;
import net.daboross.bukkitdev.skywars.api.game.SkyGameQueue;
import net.daboross.bukkitdev.skywars.api.game.SkyIDHandler;
import net.daboross.bukkitdev.skywars.api.kits.SkyKits;
import net.daboross.bukkitdev.skywars.api.location.SkyLocationStore;
import net.daboross.bukkitdev.skywars.api.players.SkyPlayers;
import net.daboross.bukkitdev.skywars.api.storage.SkyStorage;
import net.daboross.bukkitdev.skywars.api.translations.SkyTranslations;
import org.bukkit.plugin.Plugin;

public interface SkyWars extends Plugin {

    /**
     * Gets the SkyLocationStore for this plugin.
     * <br>
     * This method will never return null if this plugin is enabled.
     *
     * @return The SkyLocationStore, or null if this plugin is not yet enabled.
     */
    public SkyLocationStore getLocationStore();

    /**
     * Gets the SkyGameQueue for this plugin.
     * <br>
     * This method will never return null if this plugin is enabled.
     *
     * @return The SkyGameQueue, or null if this plugin is not yet enabled.
     */
    public SkyGameQueue getGameQueue();

    /**
     * Gets the SkyPlayers for this plugin. This will store all player info for people in queue &amp; game.
     * <br>
     * This method will never return null if this plugin is enabled.
     *
     * @return The SkyPlayers, or null if this plugin is not yet enabled.
     */
    public SkyPlayers getPlayers();

    /**
     * Gets the SkyCurrentGameTrackter for this plugin.
     * <br>
     * This method will never return null if the plugin is enabled.
     *
     * @return The SkyGameQueue, or null if this plugin is not yet enabled.
     */
    public SkyCurrentGameTracker getCurrentGameTracker();

    /**
     * Gets the SkyGameHandler for this plugin.
     * <br>
     * This method will never return null if the plugin is enabled.
     *
     * @return The SkyGameHandler, or null if this plugin is not yet enabled.
     */
    public SkyGameHandler getGameHandler();

    /**
     * Gets the SkyGameHandler for this plugin.
     * <br>
     * This method will never return null if the plugin is enabled.
     *
     * @return The SkyGameHandler, or null if this plugin is not yet enabled.
     */
    public SkyIDHandler getIDHandler();

    /**
     * Gets the SkyAttackerStorage for this plugin.
     * <br>
     * This method will never return null if this plugin is enabled.
     *
     * @return The SkyAttackerStorage, or null if this plugin is not yet enabled.
     */
    public SkyAttackerStorage getAttackerStorage();

    /**
     * Gets the SkyConfiguration for this plugin.
     * <br>
     * This method will never return null if this plugin is enabled.
     *
     * @return The SkyConfiguration, or null if this plugin is not yet enabled.
     */
    public SkyConfiguration getConfiguration();

    /**
     * Gets the SkyScore for this plugin.
     * <br>
     * This method will return null if score is not enabled, that is if {@link #getConfiguration()
     * this.getConfiguration()}. {@link net.daboross.bukkitdev.skywars.api.config.SkyConfiguration#isEnableScore()
     * isEnableScore()} returns false.
     *
     * @return The SkyScore, or null if this plugin is not yet enabled or score is disabled.
     */
    public SkyStorage getScore();

    /**
     * Gets the SkyEconomyAbstraction for this plugin.
     * <br>
     * This method will return null if economy is not enabled, that is if {@link #getConfiguration()
     * this.getConfiguration()}. {@link net.daboross.bukkitdev.skywars.api.config.SkyConfiguration#isEconomyEnabled()
     * isEconomyEnabled()} returns false.
     *
     * @return The SkyEconomyAbstraction, or null if this plugin is not yet enabled or economy is disabled.
     */
    public SkyEconomyAbstraction getEconomyHook();

    /**
     * Gets the SkyTranslations for this plugin. Use of the {@link net.daboross.bukkitdev.skywars.api.translations.SkyTrans
     * SkyTrans} over SkyTranslations is prefered.
     *
     * @return The SkyTranslations, or null if this plugin is not yet enabled.
     */
    public SkyTranslations getTranslations();

    /**
     * Reloads the translations configuration. Note that this does not reload content that is saved separately from the
     * translations on load, such as command descriptions.
     *
     * @return True if the new translations configuration was loaded successfully, false otherwise.
     */
    public boolean reloadTranslations();

    /**
     * Gets the kit configuration for this plugin. This configuration is loaded from the kits.yml file.
     *
     * @return The SkyKits, or null if this plugin is not yet enabled.
     */
    public SkyKits getKits();

    /**
     * Gets the folder arena configurations are stored in.
     *
     * @return The arena folder
     */
    public Path getArenaPath();

    /**
     * Gets the base data folder for SkyWars, as a path.
     *
     * @return The data folder
     */
    public Path getDataPath();

    /**
     * Acts exactly like getResource(), except for throwing a FileNotSoundException when not found
     *
     * @param filename Filename to get from internal jar
     * @return InputStream for file
     * @throws java.io.FileNotFoundException If the file doesn't exist in the jar
     */
    public InputStream getResourceAsStream(final String filename) throws FileNotFoundException;
}
