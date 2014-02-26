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

    private static final String implementationVersion = SkyStatic.class.getPackage().getImplementationVersion();
    private static boolean debug = false;
    private static Logger defaultLogger = Bukkit.getLogger();
    private static Logger logger = defaultLogger;
    private static String pluginName = "SkyWars";
    private static String version = "Unknown";

    public static void debug(String message) {
        if (debug) {
            if (logger.equals(defaultLogger)) {
                logger.log(Level.INFO, "[SkyWars] {0}", message);
            } else {
                logger.log(Level.INFO, message);
            }
        }
    }

    public static void debug(String message, Object... args) {
        if (debug) {
            if (logger.equals(defaultLogger)) {
                logger.log(Level.INFO, "[SkyWars] {0}", String.format(message, args));
            } else {
                logger.log(Level.INFO, String.format(message, args));
            }
        }
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
