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
import net.daboross.bukkitdev.skywars.api.parent.Parentable;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;

@Deprecated
public class SkyMessagesConfig extends Parentable<SkyMessagesConfig> implements SkyMessages {

    private final Map<String, String> rawMessages = new HashMap<String, String>();

    public SkyMessagesConfig() {
    }

    public SkyMessagesConfig(SkyMessagesConfig parent) {
        super(parent);
    }

    public void copyDataFrom(SkyMessagesConfig config) {
        this.rawMessages.putAll(config.rawMessages);
    }

    @Override
    public boolean definesAnything() {
        return !rawMessages.isEmpty();
    }

    @Override
    public String getMessage(String key) {
        throw new UnsupportedOperationException("Per-arena messages have been removed.");
    }

    @Override
    public String getRawMessage(String key) {
        Validate.notNull(key, "Key cannot be null");
        String message = rawMessages.get(key.toLowerCase(Locale.ENGLISH));
        if (message == null) {
            if (parent == null) {
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
        Validate.notNull(key, "Key cannot be null");
        key = key.toLowerCase(Locale.ENGLISH);
        if (message == null) {
            rawMessages.remove(key);
        } else {
            rawMessages.put(key, message);
        }
    }

    public Map<String, String> getInternalRawMessages() {
        return Collections.unmodifiableMap(rawMessages);
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
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SkyMessagesConfig)) return false;

        SkyMessagesConfig config = (SkyMessagesConfig) o;

        if (!rawMessages.equals(config.rawMessages)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return rawMessages.hashCode();
    }
}
