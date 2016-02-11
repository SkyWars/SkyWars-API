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

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;

public class SkyStatic {

    private static final String classImplementationVersion = SkyStatic.class.getPackage().getImplementationVersion();
    private static final String implementationVersion = classImplementationVersion == null ? "" : classImplementationVersion;
    private static final Logger defaultLogger = Bukkit.getLogger();
    private static boolean debug = false;
    private static Logger logger = defaultLogger;
    private static String pluginName = "SkyWars";
    private static String version = "Unknown";

    public static void debug(String message) {
        if (debug) {
            logger.log(Level.INFO, message);
        }
    }

    public static void debug(String message, Object... args) {
        if (debug) {
            logger.log(Level.INFO, String.format(message, args));
        }
    }

    public static void log(String message) {
        logger.log(Level.INFO, message);
    }

    public static void log(String message, Object... args) {
        logger.log(Level.INFO, String.format(message, args));
    }

    public static void log(Level level, String message) {
        logger.log(level, message);
    }

    public static void log(Level level, String message, Object... args) {
        logger.log(level, message, args);
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(final Logger logger) {
        SkyStatic.logger = logger == null ? defaultLogger : logger;
    }

    public static String getPluginName() {
        return pluginName;
    }

    public static void setPluginName(final String pluginName) {
        SkyStatic.pluginName = pluginName;
    }

    public static String getVersion() {
        return version;
    }

    public static void setVersion(final String version) {
        SkyStatic.version = version;
    }

    public static String getImplementationVersion() {
        return implementationVersion;
    }

    public static void setDebug(final boolean debug) {
        SkyStatic.debug = debug;
    }
}
