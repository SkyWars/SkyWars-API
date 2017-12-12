package net.daboross.bukkitdev.skywars.api.location;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import net.daboross.bukkitdev.skywars.api.SkyStatic;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("SkyPortalData")
public class SkyPortalData implements ConfigurationSerializable {

    public final SkyBlockLocation location;
    public final String queueName;

    public SkyPortalData(SkyBlockLocation location, final String queueName) {
        Validate.notNull(location, "location cannot be null");
        this.location = location;
        this.queueName = queueName;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("loc", location);
        if (this.queueName != null) {
            map.put("queue", queueName);
        }
        return map;
    }

    public void serialize(ConfigurationSection section) {
        Validate.notNull(section, "ConfigurationSection cannot be null");
        section.set("loc", location);
        if (this.queueName != null) {
            section.set("queue", queueName);
        }
    }

    public static SkyPortalData deserialize(Map<String, Object> map) {
        Validate.notNull(map, "Map cannot be null");
        Object locObject = map.get("loc"),
                queueNameObject = map.get("queue");
        return deserialize(locObject, queueNameObject);
    }

    public static SkyPortalData deserialize(ConfigurationSection configurationSection) {
        Validate.notNull(configurationSection, "ConfigurationSection cannot be null");
        Object locObject = configurationSection.get("loc"),
                queueNameObject = configurationSection.get("queue");
        return deserialize(locObject, queueNameObject);
    }

    private static SkyPortalData deserialize(Object locObject, Object queueNameObject) {
        if (!(locObject instanceof SkyBlockLocation && (queueNameObject == null || !(queueNameObject instanceof String || queueNameObject instanceof Number)))) {
            SkyStatic.log(Level.WARNING, "[SkySignData] Silently failing deserialization due to loc or lines not existing on map or not being appropriate types.");
            return null;
        }
        SkyBlockLocation location = (SkyBlockLocation) locObject;
        String queueName;
        if (queueNameObject == null) {
            queueName = null;
        } else {
            queueName = String.valueOf(queueNameObject);
        }
        return new SkyPortalData(location, queueName);
    }

    @Override
    public String toString() {
        return "SkyPortalData{" +
                "location=" + location +
                ", queueName='" + queueName + '\'' +
                '}';
    }

    @Override
    @SuppressWarnings("SimplifiableIfStatement")
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SkyPortalData)) return false;

        SkyPortalData data = (SkyPortalData) o;

        if (!location.equals(data.location)) return false;
        return queueName != null ? queueName.equals(data.queueName) : data.queueName == null;
    }

    @Override
    public int hashCode() {
        int result = location.hashCode();
        result = 31 * result + (queueName != null ? queueName.hashCode() : 0);
        return result;
    }
}
