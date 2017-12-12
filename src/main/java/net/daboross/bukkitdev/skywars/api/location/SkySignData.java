package net.daboross.bukkitdev.skywars.api.location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import net.daboross.bukkitdev.skywars.api.SkyStatic;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("SkySignData")
public class SkySignData implements ConfigurationSerializable {

    public final SkyBlockLocation location;
    /**
     * Can be null. If so, this is a legacy migrated sign.
     */
    public final List<String> lastLines;
    public final String queueName;

    public SkySignData(SkyBlockLocation location, List<String> lastLines, final String queueName) {
        Validate.notNull(location, "location cannot be null");
        if (lastLines != null) {
            Validate.isTrue(lastLines.size() == 4, "signs must have 4 lines");
        }
        this.location = location;
        this.lastLines = lastLines;
        this.queueName = queueName;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("loc", location);
        map.put("lines", lastLines);
        if (this.queueName != null) {
            map.put("queue", queueName);
        }
        return map;
    }

    public void serialize(ConfigurationSection section) {
        Validate.notNull(section, "ConfigurationSection cannot be null");
        section.set("loc", location);
        section.set("lines", lastLines);
        if (this.queueName != null) {
            section.set("queue", queueName);
        }
    }

    public static SkySignData deserialize(Map<String, Object> map) {
        Validate.notNull(map, "Map cannot be null");
        Object locObject = map.get("loc"),
                linesObject = map.get("lines"),
                queueNameObject = map.get("queue");
        return deserialize(locObject, linesObject, queueNameObject);
    }

    public static SkySignData deserialize(ConfigurationSection configurationSection) {
        Validate.notNull(configurationSection, "ConfigurationSection cannot be null");
        Object locObject = configurationSection.get("loc"),
                linesObject = configurationSection.get("lines"),
                queueNameObject = configurationSection.get("queue");
        return deserialize(locObject, linesObject, queueNameObject);
    }

    private static SkySignData deserialize(Object locObject, Object linesObject, Object queueNameObject) {
        if (!(locObject instanceof SkyBlockLocation && linesObject instanceof List && (queueNameObject == null || !(queueNameObject instanceof String || queueNameObject instanceof Number)))) {
            SkyStatic.log(Level.WARNING, "[SkySignData] Silently failing deserialization due to loc or lines not existing on map or not being appropriate types.");
            return null;
        }
        if (((List)linesObject).size() != 4) {
            SkyStatic.log(Level.WARNING, "[SkySignData] Silently failing deserialization due to there not being exactly 4 lines of previous sign.");
            return null;
        }
        SkyBlockLocation location = (SkyBlockLocation) locObject;
        List<String> lines = interpretList((List) linesObject);
        if (lines == null) {
            return null;
        }
        String queueName;
        if (queueNameObject == null) {
            queueName = null;
        } else {
            queueName = String.valueOf(queueNameObject);
        }
        return new SkySignData(location, lines, queueName);
    }

    private static List<String> interpretList(List<?> input) {
        List<String> lines = new ArrayList<>(input.size());
        for (Object o : input) {
            if (!(o instanceof String || o instanceof Number)) {
                SkyStatic.log(Level.WARNING, "[SkySignData] Silently failing deserialization due to lines list not containing correct types.");
                return null;
            }
            lines.add(o.toString());
        }
        return lines;
    }

    @Override
    public String toString() {
        return "SkySignData{" +
                "location=" + location +
                ", lastLines=" + lastLines +
                ", queueName='" + queueName + '\'' +
                '}';
    }

    @Override
    @SuppressWarnings("SimplifiableIfStatement")
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SkySignData)) return false;

        SkySignData data = (SkySignData) o;

        if (!location.equals(data.location)) return false;
        if (lastLines != null ? !lastLines.equals(data.lastLines) : data.lastLines != null) return false;
        return queueName != null ? queueName.equals(data.queueName) : data.queueName == null;
    }

    @Override
    public int hashCode() {
        int result = location.hashCode();
        result = 31 * result + (lastLines != null ? lastLines.hashCode() : 0);
        result = 31 * result + (queueName != null ? queueName.hashCode() : 0);
        return result;
    }
}
