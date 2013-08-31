/*
 * Copyright (C) 2013 daboross
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

import java.util.HashMap;
import java.util.Map;
import net.daboross.bukkitdev.skywars.api.Parentable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

/**
 *
 * @author daboross
 */
@SerializableAs("SkyMessagesConfig")
public class SkyMessagesConfig extends Parentable<SkyMessagesConfig> implements ConfigurationSerializable {

    private final Map<String, String> messages = new HashMap<String, String>();

    public SkyMessagesConfig() {
    }

    public SkyMessagesConfig(SkyMessagesConfig parent) {
        super(parent);
    }

    public void copyDataFrom(SkyMessagesConfig config) {
        this.messages.putAll(config.messages);
    }

    public boolean definesAnything() {
        return !messages.isEmpty();
    }

    public String getMessage(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Null key");
        }
        String message = messages.get(key.toLowerCase());
        if (message == null) {
            if (parent == null) {
                throw new IllegalArgumentException("Ultimate parent does not define message " + key);
            } else {
                return parent.getMessage(key);
            }
        } else {
            return message;
        }
    }

    public void setMessage(String key, String message) {
        if (key == null) {
            throw new IllegalArgumentException("Null key");
        }
        if (message == null) {
            messages.remove(key.toLowerCase());
        } else {
            messages.put(key.toLowerCase(), message);
        }
    }

    @Override
    public Map<String, Object> serialize() {
        return new HashMap<String, Object>(messages);
    }

    public static SkyMessagesConfig deserialize(Map<String, Object> map) {
        SkyMessagesConfig returnValue = new SkyMessagesConfig();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object o = entry.getValue();
            if (o != null) {
                returnValue.setMessage(entry.getKey(), o instanceof String ? (String) o : o.toString());
            }
        }
        return returnValue;
    }

    public static SkyMessagesConfig deserialize(ConfigurationSection configurationSection) {
        SkyMessagesConfig returnValue = new SkyMessagesConfig();
        for (String key : configurationSection.getKeys(true)) {
            String value = configurationSection.getString(key);
            if (value != null) {
                returnValue.setMessage(key, value);
            }
        }
        return returnValue;
    }

    @Override
    public String toString() {
        return "ArenaMessages{parent=" + parent + ",messages=" + messages + "}";
    }
}
