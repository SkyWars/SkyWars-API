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

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocation;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;

public class SkyArenaConfig implements SkyArena {

    private List<SkyPlayerLocation> spawns;
    private Integer numTeams;
    private Integer teamSize;
    private Integer placementY;
    private final SkyBoundariesConfig boundaries;
    private Path file;
    private String arenaName = "null";

    public SkyArenaConfig(String arenaName, List<SkyPlayerLocation> spawns, int numTeams, int teamSize, int placementY, SkyBoundariesConfig boundaries) {
        Validate.isTrue(numTeams >= 2, "Num teams can't be smaller than 2");
        Validate.isTrue(teamSize >= 1, "Team size can't be smaller than 1");
        Validate.isTrue(placementY >= 0, "placement-y can't be smaller than 0");
        Validate.notNull(boundaries);
        Validate.notNull(spawns);
        Validate.isTrue(spawns.size() >= numTeams, "Number of spawns needs to be at least as big as numTeams");
        this.arenaName = arenaName;
        this.spawns = new ArrayList<SkyPlayerLocation>(spawns.size());
        for (SkyPlayerLocation l : spawns) {
            this.spawns.add(l.changeWorld(null));
        }
        this.numTeams = numTeams;
        this.teamSize = teamSize;
        this.placementY = placementY;
        this.boundaries = boundaries;
    }

    @Override
    public String getArenaName() {
        return arenaName;
    }

    @Override
    public int getNumTeams() {
        return numTeams;
    }

    @Override
    public int getTeamSize() {
        return teamSize;
    }

    @Override
    public int getPlacementY() {
        return placementY;
    }

    @Override
    public SkyBoundariesConfig getBoundaries() {
        return boundaries;
    }

    @Override
    public List<SkyPlayerLocation> getSpawns() {
        return spawns;
    }

    @Override
    public void setSpawns(List<SkyPlayerLocation> spawns) {
        Validate.notNull(spawns);
        this.spawns = spawns;
    }

    @Override
    public void setNumTeams(int numTeams) {
        Validate.isTrue(numTeams >= 2, "num-teams can't be smaller than 2");
        this.numTeams = numTeams;
    }

    @Override
    public int getNumPlayers() {
        return getNumTeams() * getTeamSize();
    }

    @Override
    public void setTeamSize(int teamSize) {
        Validate.isTrue(teamSize >= 1, "Team size can't be smaller than 1");
        this.teamSize = teamSize;
    }

    @Override
    public void setPlacementY(int placementY) {
        Validate.isTrue(placementY >= 0, "Placement y cannot be smaller than 0.");
        this.placementY = placementY;
    }

    @Override
    public void serialize(ConfigurationSection section) {
        section.set("config-version", 1);
        if (spawns != null) {
            List<Map> spawnsList = new ArrayList<Map>(spawns.size());
            for (SkyPlayerLocation loc : spawns) {
                spawnsList.add(loc.changeWorld(null).serialize());
            }
            section.set("spawns", spawnsList);
        }
        section.set("num-teams", numTeams);
        section.set("team-size", teamSize);
        boundaries.serialize(section.createSection("boundaries"));
        section.set("placement-y", placementY);
    }

    public static SkyArenaConfig deserialize(ConfigurationSection configurationSection) {
        Validate.isTrue(configurationSection.isConfigurationSection("boundaries"), "boundries must be included in arena");
        Validate.isTrue(configurationSection.isList("spawns"), "Spawns must be included in arena");
        Validate.isTrue(configurationSection.isInt("num-teams"), "num-teams must be included in arena");
        Validate.isTrue(configurationSection.isInt("team-size"), "team-size must be included in arena");
        Validate.isTrue(configurationSection.isInt("placement-y"), "placement-y must be included in arena");
        Validate.isTrue(configurationSection.getInt("config-version") == 1, "Configuration version must be 1");
        ConfigurationSection boundariesSection = configurationSection.getConfigurationSection("boundaries");
        List<?> spawnsObjList = configurationSection.getList("spawns");
        List<SkyPlayerLocation> spawns = new ArrayList<SkyPlayerLocation>(spawnsObjList.size());
        for (Object obj : spawnsObjList) {
            Validate.isTrue(obj instanceof Map, "Invalid spawn found in arena file");
            //noinspection unchecked,ConstantConditions
            SkyPlayerLocation loc = SkyPlayerLocation.deserialize((Map) obj);
            if (loc == null) {
                continue;
            }
            spawns.add(loc);
        }
        int numPlayers = configurationSection.getInt("num-teams");
        int teamSize = configurationSection.getInt("team-size");
        int placementY = configurationSection.getInt("placement-y");
        SkyBoundariesConfig boundaries = SkyBoundariesConfig.deserialize(boundariesSection);
        return new SkyArenaConfig(null, spawns, numPlayers, teamSize, placementY, boundaries);
    }

    public void setFile(final Path file) {
        this.file = file;
    }

    public void setArenaName(final String arenaName) {
        this.arenaName = arenaName;
    }

    public Path getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "SkyArenaConfig{" +
                "rawSpawns=" + spawns +
                ", rawNumTeams=" + numTeams +
                ", rawTeamSize=" + teamSize +
                ", rawPlacementY=" + placementY +
                ", boundaries=" + boundaries +
                ", file=" + file +
                ", arenaName='" + arenaName + '\'' +
                '}';
    }

    @Override
    @SuppressWarnings("RedundantIfStatement")
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SkyArenaConfig)) return false;

        SkyArenaConfig config = (SkyArenaConfig) o;

        if (!boundaries.equals(config.boundaries)) return false;
        if (numTeams != null ? !numTeams.equals(config.numTeams) : config.numTeams != null) return false;
        if (placementY != null ? !placementY.equals(config.placementY) : config.placementY != null)
            return false;
        if (spawns != null ? !spawns.equals(config.spawns) : config.spawns != null) return false;
        if (teamSize != null ? !teamSize.equals(config.teamSize) : config.teamSize != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = spawns != null ? spawns.hashCode() : 0;
        result = 31 * result + (numTeams != null ? numTeams.hashCode() : 0);
        result = 31 * result + (teamSize != null ? teamSize.hashCode() : 0);
        result = 31 * result + (placementY != null ? placementY.hashCode() : 0);
        result = 31 * result + boundaries.hashCode();
        return result;
    }
}
