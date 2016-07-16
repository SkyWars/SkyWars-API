/*
 * Copyright (C) 2013-2016 Dabo Ross <http://www.daboross.net/>
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocation;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;

public class SkyArenaConfig implements SkyArena {

    private List<SkyPlayerLocation> spawns;
    private int numTeams;
    private int teamSize;
    private int minPlayers;
    private int placementY;
    private final SkyBoundariesConfig boundaries;
    private List<SkyArenaChest> chests;
    private Path file;
    private String arenaName = "null";

    public SkyArenaConfig(String arenaName, List<SkyPlayerLocation> spawns, int numTeams, int teamSize, final int minPlayers, int placementY, SkyBoundariesConfig boundaries, final List<SkyArenaChest> chests) {
        Validate.isTrue(numTeams >= 2, "Num teams can't be smaller than 2");
        Validate.isTrue(teamSize >= 1, "Team size can't be smaller than 1");
        Validate.isTrue(minPlayers <= numTeams * teamSize, "Min players can't be smaller tha max players (num teams * team size)");
        Validate.isTrue(placementY >= 0, "placement-y can't be smaller than 0");
        Validate.notNull(boundaries);
        Validate.notNull(spawns);
        Validate.isTrue(spawns.size() >= numTeams, "Number of spawns needs to be at least as big as numTeams");
        this.arenaName = arenaName;
        this.spawns = new ArrayList<>(spawns.size());
        for (SkyPlayerLocation l : spawns) {
            this.spawns.add(l.changeWorld(null));
        }
        this.numTeams = numTeams;
        this.teamSize = teamSize;
        this.minPlayers = minPlayers;
        this.placementY = placementY;
        this.boundaries = boundaries;
        this.chests = chests;
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
        return numTeams * teamSize;
    }

    @Override
    public int getMinPlayers() {
        return minPlayers;
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
        section.set("config-version", 3);
        List<Map> spawnsList = new ArrayList<>(spawns.size());
        for (SkyPlayerLocation loc : spawns) {
            spawnsList.add(loc.changeWorld(null).serialize());
        }
        section.set("spawns", spawnsList);
        section.set("num-teams", numTeams);
        section.set("team-size", teamSize);
        section.set("min-players", minPlayers);
        boundaries.serialize(section.createSection("boundaries"));
        section.set("placement-y", placementY);
        if (chests != null) {
            List<Map> chestsList = new ArrayList<>(chests.size());
            for (SkyArenaChest chest : chests) {
                chestsList.add(chest.serialize());
            }
            section.set("chests", chestsList);
        }
    }

    @Override
    public List<SkyArenaChest> getChests() {
        return chests == null ? Collections.<SkyArenaChest>emptyList() : Collections.unmodifiableList(chests);
    }

    @Override
    public List<SkyArenaChest> getChestConfiguration() {
        return chests == null ? null : Collections.unmodifiableList(chests);
    }

    @Override
    public void setChests(final List<SkyArenaChest> chests) {
        this.chests = chests;
    }

    public static SkyArenaConfig deserialize(ConfigurationSection configurationSection) {
        Validate.isTrue(configurationSection.isConfigurationSection("boundaries"), "boundries must be included in arena");
        Validate.isTrue(configurationSection.isList("spawns"), "Spawns must be included in arena");
        Validate.isTrue(configurationSection.isInt("num-teams"), "num-teams must be included in arena");
        Validate.isTrue(configurationSection.isInt("team-size"), "team-size must be included in arena");
        Validate.isTrue(configurationSection.isInt("min-players"), "min-players must be included in arena");
        Validate.isTrue(configurationSection.isInt("placement-y"), "placement-y must be included in arena");
        Validate.isTrue(configurationSection.getInt("config-version") == 3, "Configuration version must be 3");
        ConfigurationSection boundariesSection = configurationSection.getConfigurationSection("boundaries");
        List<?> spawnsObjList = configurationSection.getList("spawns");
        List<SkyPlayerLocation> spawns = new ArrayList<>(spawnsObjList.size());
        for (Object obj : spawnsObjList) {
            Validate.isTrue(obj instanceof Map, "Invalid spawn found in arena file");

            @SuppressWarnings({"unchecked", "ConstantConditions"})
            SkyPlayerLocation loc = SkyPlayerLocation.deserialize((Map<String, Object>) obj);
            if (loc == null) {
                continue;
            }
            spawns.add(loc);
        }
        int numPlayers = configurationSection.getInt("num-teams");
        int teamSize = configurationSection.getInt("team-size");
        int minPlayers = configurationSection.getInt("min-players");
        int placementY = configurationSection.getInt("placement-y");
        SkyBoundariesConfig boundaries = SkyBoundariesConfig.deserialize(boundariesSection);
        List<SkyArenaChest> chests;
        if (configurationSection.isList("chests")) {
            chests = new ArrayList<>();

            List<?> chestsObjList = configurationSection.getList("chests");
            for (Object obj : chestsObjList) {
                Validate.isTrue(obj instanceof Map, "Invalid chest found in arena file");
                @SuppressWarnings({"unchecked", "ConstantConditions"})
                SkyArenaChest chest = SkyArenaChestConfig.deserialize((Map<String, Object>) obj);
                if (chest == null) {
                    continue;
                }
                chests.add(chest);
            }
        } else {
            chests = null;
        }
        return new SkyArenaConfig(null, spawns, numPlayers, teamSize, minPlayers, placementY, boundaries, chests);
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
                "spawns=" + spawns +
                ", numTeams=" + numTeams +
                ", minPlayers=" + minPlayers +
                ", teamSize=" + teamSize +
                ", placementY=" + placementY +
                ", boundaries=" + boundaries +
                ", chests=" + chests +
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

        if (numTeams != config.numTeams) return false;
        if (minPlayers != config.minPlayers) return false;
        if (teamSize != config.teamSize) return false;
        if (placementY != config.placementY) return false;
        if (!spawns.equals(config.spawns)) return false;
        if (!boundaries.equals(config.boundaries)) return false;
        if (chests != null ? !chests.equals(config.chests) : config.chests != null) return false;
        if (file != null ? !file.equals(config.file) : config.file != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = spawns.hashCode();
        result = 31 * result + numTeams;
        result = 31 * result + minPlayers;
        result = 31 * result + teamSize;
        result = 31 * result + placementY;
        result = 31 * result + boundaries.hashCode();
        result = 31 * result + (chests != null ? chests.hashCode() : 0);
        result = 31 * result + (file != null ? file.hashCode() : 0);
        result = 31 * result + (arenaName != null ? arenaName.hashCode() : 0);
        return result;
    }
}
