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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import net.daboross.bukkitdev.skywars.api.SkyStatic;
import net.daboross.bukkitdev.skywars.api.config.SkyConfigurationException;
import net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocation;
import net.daboross.bukkitdev.skywars.api.parent.Parentable;
import org.bukkit.configuration.ConfigurationSection;

public class SkyArenaConfig extends Parentable<SkyArenaConfig> implements SkyArena {

    private List<SkyPlayerLocation> rawSpawns;
    private Integer rawNumTeams;
    private Integer rawTeamSize;
    private Integer rawPlacementY;
    private final SkyBoundariesConfig boundaries = new SkyBoundariesConfig();
    private final SkyMessagesConfig messages = new SkyMessagesConfig();
    private File file;
    private String arenaName = "null";

    public SkyArenaConfig() {
    }

    public SkyArenaConfig(SkyArenaConfig parent, String arenaName, List<SkyPlayerLocation> spawns, Integer numTeams, Integer teamSize, Integer placementY, SkyBoundariesConfig boundaries, SkyMessagesConfig messages) {
        super(parent);
        if (numTeams != null && numTeams < 2) {
            throw new IllegalArgumentException("num-teams can't be smaller than 2");
        }
        if (teamSize != null && teamSize < 1) {
            throw new IllegalArgumentException("team-size can't be smaller than 1");
        }
        if (placementY != null && placementY < 0) {
            throw new IllegalArgumentException("placement-y cannot be smaller than 0.");
        }
        this.arenaName = arenaName;
        if (spawns == null) {
            rawSpawns = null;
        } else {
            this.rawSpawns = new ArrayList<SkyPlayerLocation>(spawns.size());
            for (SkyPlayerLocation l : spawns) {
                rawSpawns.add(l.changeWorld(null));
            }
        }
        this.rawNumTeams = numTeams;
        this.rawTeamSize = teamSize;
        this.rawPlacementY = placementY;
        if (parent != null) {
            this.boundaries.setParent(parent.getBoundaries());
        }
        if (boundaries != null) {
            this.boundaries.copyDataFrom(boundaries);
        }
        if (messages != null) {
            this.messages.copyDataFrom(messages);
        }
    }

    public SkyArenaConfig(String arenaName, List<SkyPlayerLocation> spawns, Integer numTeams, Integer teamSize, Integer placementY, SkyBoundariesConfig boundaries, SkyMessagesConfig messages) {
        this(null, arenaName, spawns, numTeams, teamSize, placementY, boundaries, messages);
    }

    @Override
    public void setParent(SkyArenaConfig parent) {
        super.setParent(parent);
        if (parent != null) {
            messages.setParent(parent.getMessages());
            boundaries.setParent(parent.getBoundaries());
        } else {
            messages.setParent(null);
            boundaries.setParent(null);
        }
    }

    @Override
    public List<SkyPlayerLocation> getSpawns() {
        if (rawSpawns == null) {
            if (parent == null) {
                throw new IllegalStateException("Ultimate parent spawns not found.");
            } else {
                return parent.getSpawns();
            }
        } else {
            return rawSpawns;
        }
    }

    @Override
    public void setSpawns(List<SkyPlayerLocation> spawns) {
        this.rawSpawns = spawns;
    }

    @Override
    public int getNumTeams() {
        if (rawNumTeams == null) {
            if (parent == null) {
                throw new IllegalStateException("Ultimate parent numTeams not found.");
            } else {
                return parent.getNumTeams();
            }
        } else {
            return rawNumTeams;
        }
    }

    @Override
    public void setNumTeams(Integer numTeams) {
        if (numTeams != null && numTeams < 2) {
            throw new IllegalArgumentException("Num teams can't be smaller than 2");
        }
        this.rawNumTeams = numTeams;
    }

    @Override
    public int getNumPlayers() {
        return getNumTeams() * getTeamSize();
    }

    @Override
    public int getTeamSize() {
        if (rawTeamSize == null) {
            if (parent == null) {
                throw new IllegalStateException("Ultimate parent teamSize not found.");
            } else {
                return parent.getTeamSize();
            }
        } else {
            return rawTeamSize;
        }
    }

    @Override
    public void setTeamSize(Integer teamSize) {
        if (teamSize != null && teamSize < 1) {
            throw new IllegalArgumentException("Team size can't be smaller than 1");
        }
    }

    @Override
    public int getPlacementY() {
        if (rawPlacementY == null) {
            if (parent == null) {
                throw new IllegalStateException("Ultimate parent placementY not found.");
            } else {
                return parent.getPlacementY();
            }
        } else {
            return rawPlacementY;
        }
    }

    @Override
    public void setPlacementY(Integer placementY) {
        if (placementY != null && placementY < 0) {
            throw new IllegalStateException("Placement y cannot be smaller than 0.");
        }
        rawPlacementY = placementY;
    }

    public void confirmAllValuesExist() throws SkyConfigurationException {
        if (rawSpawns == null) {
            throw new SkyConfigurationException("'spawns' not defined for arena config " + file.getAbsolutePath());
        }
        if (rawSpawns.isEmpty()) {
            throw new SkyConfigurationException("No spawns defined in 'spawns' for arena config " + file.getAbsolutePath());
        }
        if (rawNumTeams == null) {
            throw new SkyConfigurationException("'num-teams' not defined for arena config " + file.getAbsolutePath());
        }
        if (rawTeamSize == null) {
            throw new SkyConfigurationException("'team-size' not defined for arena config " + file.getAbsolutePath());
        }
        if (rawPlacementY == null) {
            throw new SkyConfigurationException("'placement-y' not defined for arena config " + file.getAbsolutePath());
        }
    }

    @Override
    public void serialize(ConfigurationSection section) {
        section.set("config-version", 1);
        if (rawSpawns != null) {
            List<Map> spawnsList = new ArrayList<Map>(rawSpawns.size());
            for (SkyPlayerLocation loc : rawSpawns) {
                spawnsList.add(loc.changeWorld(null).serialize());
            }
            section.set("spawns", spawnsList);
        }
        section.set("num-teams", rawNumTeams);
        section.set("team-size", rawTeamSize);

        if (boundaries.definesAnything()) {
            boundaries.serialize(section.createSection("boundaries"));
        }
        section.set("placement-y", rawPlacementY);
        if (messages.definesAnything()) {
            messages.serialize(section.createSection("messages"));
        }
    }

    public static SkyArenaConfig deserialize(ConfigurationSection configurationSection) {
        if (configurationSection.getInt("config-version") != 1) {
            throw new IllegalArgumentException("Config version not 1");
        }
        ConfigurationSection boundariesSection = configurationSection.getConfigurationSection("boundaries"),
                messagesSection = configurationSection.getConfigurationSection("messages");
        List<?> spawnsObjList = configurationSection.getList("spawns");
        List<SkyPlayerLocation> spawns = null;
        if (spawnsObjList != null) {
            spawns = new ArrayList<SkyPlayerLocation>(spawnsObjList.size());
            for (Object obj : spawnsObjList) {
                if (obj instanceof Map) {
                    SkyPlayerLocation loc = SkyPlayerLocation.deserialize((Map) obj);
                    if (loc == null) {
                        continue;
                    }
                    spawns.add(loc);
                } else {
                    SkyStatic.getLogger().log(Level.WARNING, "[SkyArenaConfig] Non-Map object {0} found in arena configuration spawn list. Ignoring it", obj);
                }
            }
        }
        Integer numPlayers = configurationSection.isInt("num-teams") ? configurationSection.getInt("num-teams") : null;
        Integer teamSize = configurationSection.isInt("team-size") ? configurationSection.getInt("team-size") : null;
        Integer placementY = configurationSection.isInt("placement-y") ? configurationSection.getInt("placement-y") : null;
        SkyBoundariesConfig boundaries = boundariesSection != null ? SkyBoundariesConfig.deserialize(boundariesSection) : null;
        SkyMessagesConfig messages = messagesSection != null ? SkyMessagesConfig.deserialize(messagesSection) : null;
        return new SkyArenaConfig(null, spawns, numPlayers, teamSize, placementY, boundaries, messages);
    }

    public void setFile(final File file) {
        this.file = file;
    }

    public void setArenaName(final String arenaName) {
        this.arenaName = arenaName;
    }

    @Override
    public String getArenaName() {
        return arenaName;
    }

    public List<SkyPlayerLocation> getRawSpawns() {
        return rawSpawns;
    }

    @Override
    public Integer getRawNumTeams() {
        return rawNumTeams;
    }

    @Override
    public Integer getRawTeamSize() {
        return rawTeamSize;
    }

    @Override
    public Integer getRawPlacementY() {
        return rawPlacementY;
    }

    @Override
    public SkyBoundariesConfig getBoundaries() {
        return boundaries;
    }

    @Override
    public SkyMessagesConfig getMessages() {
        return messages;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "SkyArenaConfig{" +
                "rawSpawns=" + rawSpawns +
                ", rawNumTeams=" + rawNumTeams +
                ", rawTeamSize=" + rawTeamSize +
                ", rawPlacementY=" + rawPlacementY +
                ", boundaries=" + boundaries +
                ", messages=" + messages +
                ", file=" + file +
                ", arenaName='" + arenaName + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SkyArenaConfig)) return false;

        SkyArenaConfig config = (SkyArenaConfig) o;

        if (!boundaries.equals(config.boundaries)) return false;
        if (!messages.equals(config.messages)) return false;
        if (rawNumTeams != null ? !rawNumTeams.equals(config.rawNumTeams) : config.rawNumTeams != null) return false;
        if (rawPlacementY != null ? !rawPlacementY.equals(config.rawPlacementY) : config.rawPlacementY != null)
            return false;
        if (rawSpawns != null ? !rawSpawns.equals(config.rawSpawns) : config.rawSpawns != null) return false;
        if (rawTeamSize != null ? !rawTeamSize.equals(config.rawTeamSize) : config.rawTeamSize != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rawSpawns != null ? rawSpawns.hashCode() : 0;
        result = 31 * result + (rawNumTeams != null ? rawNumTeams.hashCode() : 0);
        result = 31 * result + (rawTeamSize != null ? rawTeamSize.hashCode() : 0);
        result = 31 * result + (rawPlacementY != null ? rawPlacementY.hashCode() : 0);
        result = 31 * result + boundaries.hashCode();
        result = 31 * result + messages.hashCode();
        return result;
    }
}
