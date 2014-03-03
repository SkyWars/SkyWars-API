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
package net.daboross.bukkitdev.skywars.api.arenaconfig;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.daboross.bukkitdev.skywars.api.config.ConfigColorCode;
import net.daboross.bukkitdev.skywars.api.config.SkyMessageKeys;
import net.daboross.bukkitdev.skywars.api.parent.Parentable;
import net.daboross.bukkitdev.skywars.api.translations.SkyTrans;
import net.daboross.bukkitdev.skywars.api.translations.TransKey;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public class SkyMessagesConfig extends Parentable<SkyMessagesConfig> implements SkyMessages {

    private final Map<String, String> rawMessages = new HashMap<String, String>();
    private final Map<String, String> messages = new HashMap<String, String>();
    private String prefix;

    public SkyMessagesConfig() {
    }

    public SkyMessagesConfig(SkyMessagesConfig parent) {
        super(parent);
    }

    public void copyDataFrom(SkyMessagesConfig config) {
        this.rawMessages.putAll(config.rawMessages);
        this.messages.putAll(config.messages);
    }

    @Override
    public boolean definesAnything() {
        return !rawMessages.isEmpty();
    }

    public void setPrefix(String prefix) {
        this.prefix = translate(prefix);
    }

    @Override
    public String getMessage(String key) {
        if (prefix == null) {
            throw new IllegalStateException("Prefix not set");
        }
        key = key.toLowerCase(Locale.ENGLISH);
        String message = messages.get(key);
        if (message == null) {
            if (parent == null) {
                TransKey transKey = SkyMessageKeys.DEFAULT_KEYS.get(key);
                if (transKey != null) {
                    Object[] args = new String[transKey.args];
                    for (int i = 0; i < args.length; i++) {
                        args[i] = "%s";
                    }
                    return prefix + SkyTrans.get(transKey, args);
                }
                throw new IllegalArgumentException("Ultimate parent does not define message '" + key + "'");
            } else {
                return parent.getMessage(key);
            }
        } else {
            return prefix + message;
        }
    }

    @Override
    public String getRawMessage(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Null key");
        }
        String message = rawMessages.get(key.toLowerCase(Locale.ENGLISH));
        if (message == null) {
            if (parent == null) {
                TransKey transKey = SkyMessageKeys.DEFAULT_KEYS.get(key);
                if (transKey != null) {
                    Object[] args = new String[transKey.args];
                    for (int i = 0; i < args.length; i++) {
                        args[i] = "%s";
                    }
                    return SkyTrans.get(transKey, args);
                }
                throw new IllegalArgumentException("Ultimate parent does not define message '" + key + "'");
            } else {
                return parent.getRawMessage(key);
            }
        } else {
            return message;
        }
    }

    @Override
    public void setRawMessage(String key, String message) {
        if (key == null) {
            throw new IllegalArgumentException("Null key");
        }
        key = key.toLowerCase(Locale.ENGLISH);
        if (message == null) {
            rawMessages.remove(key);
            messages.remove(key);
        } else {
            rawMessages.put(key, message);
            messages.put(key, translate(message));
        }
    }

    public Map<String, String> getInternalRawMessages() {
        return Collections.unmodifiableMap(rawMessages);
    }

    private String translate(String original) {
        return ChatColor.translateAlternateColorCodes('&', original);
    }

    public void serialize(ConfigurationSection section) {
        for (Map.Entry<String, String> entry : rawMessages.entrySet()) {
            section.set(entry.getKey(), entry.getValue());
        }
    }

    public static SkyMessagesConfig deserialize(ConfigurationSection configurationSection) {
        SkyMessagesConfig returnValue = new SkyMessagesConfig();
        for (String key : configurationSection.getKeys(true)) {
            if (configurationSection.isString(key)) {
                returnValue.setRawMessage(key, ConfigColorCode.translateCodes(configurationSection.getString(key)));
            }
        }
        return returnValue;
    }

    @Override
    public String toString() {
        return "SkyMessagesConfig{" +
                "rawMessages=" + rawMessages +
                ", messages=" + messages +
                ", prefix='" + prefix + '\'' +
                '}';
    }

    @Override
    @SuppressWarnings("RedundantIfStatement")
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SkyMessagesConfig)) return false;

        SkyMessagesConfig config = (SkyMessagesConfig) o;

        if (!messages.equals(config.messages)) return false;
        if (prefix != null ? !prefix.equals(config.prefix) : config.prefix != null) return false;
        if (!rawMessages.equals(config.rawMessages)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rawMessages.hashCode();
        result = 31 * result + messages.hashCode();
        result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
        return result;
    }
}
