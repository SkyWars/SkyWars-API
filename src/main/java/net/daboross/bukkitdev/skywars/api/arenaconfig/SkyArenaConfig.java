/*
 * Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.daboross.bukkitdev.skywars.api.SkyStatic;
import net.daboross.bukkitdev.skywars.api.parent.Parentable;
import net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocation;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;

/**
 *
 * @author Dabo Ross <http://www.daboross.net/>
 */
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true, exclude = {"arenaName", "file"}, callSuper = false)
@NoArgsConstructor
public class SkyArenaConfig extends Parentable<SkyArenaConfig> implements SkyArena {

    @Getter
    private List<SkyPlayerLocation> rawSpawns;
    @Getter
    private Integer rawNumTeams;
    @Getter
    private Integer rawTeamSize;
    private final SkyBoundariesConfig boundaries = new SkyBoundariesConfig();
    private final SkyMessagesConfig messages = new SkyMessagesConfig();
    private final SkyPlacementConfig placement = new SkyPlacementConfig();
    @Setter
    @Getter
    private File file;
    @Setter
    @Getter
    private String arenaName = "null";

    public SkyArenaConfig( SkyArenaConfig parent, String arenaName, List<SkyPlayerLocation> spawns, Integer numTeams, Integer teamSize, SkyBoundariesConfig boundaries, SkyPlacementConfig placement, SkyMessagesConfig messages ) {
        super( parent );
        if ( numTeams != null && numTeams < 2 ) {
            throw new IllegalArgumentException( "Num teams can't be smaller than 2" );
        }
        if ( teamSize != null && teamSize < 1 ) {
            throw new IllegalArgumentException( "Team size can't be smaller than 1" );
        }
        this.arenaName = arenaName;
        this.rawSpawns = spawns;
        this.rawNumTeams = numTeams;
        this.rawTeamSize = teamSize;
        if ( parent != null ) {
            this.boundaries.setParent( parent.getBoundaries() );
        }
        if ( boundaries != null ) {
            this.boundaries.copyDataFrom( boundaries );
        }
        if ( placement != null ) {
            this.placement.copyDataFrom( placement );
        }
        if ( messages != null ) {
            this.messages.copyDataFrom( messages );
        }
    }

    public SkyArenaConfig( String arenaName, List<SkyPlayerLocation> spawns, Integer numTeams, Integer teamSize, SkyBoundariesConfig boundaries, SkyPlacementConfig placement, SkyMessagesConfig messages ) {
        if ( numTeams != null && numTeams < 2 ) {
            throw new IllegalArgumentException( "Num teams can't be smaller than 2" );
        }
        this.arenaName = arenaName;
        this.rawSpawns = spawns;
        this.rawNumTeams = numTeams;
        if ( parent != null ) {
            this.boundaries.setParent( parent.getBoundaries() );
        }
        if ( boundaries != null ) {
            this.boundaries.copyDataFrom( boundaries );
        }
        if ( placement != null ) {
            this.placement.copyDataFrom( placement );
        }
        if ( messages != null ) {
            this.messages.copyDataFrom( messages );
        }
    }

    @Override
    public void setParent( SkyArenaConfig parent ) {
        super.setParent( parent );
        if ( parent != null ) {
            messages.setParent( parent.getMessages() );
            placement.setParent( parent.getPlacement() );
            boundaries.setParent( parent.getBoundaries() );
        } else {
            messages.setParent( null );
            placement.setParent( null );
            boundaries.setParent( null );
        }
    }

    @Override
    public List<SkyPlayerLocation> getSpawns() {
        if ( rawSpawns == null ) {
            if ( parent == null ) {
                throw new IllegalStateException( "Ultimate parent spawns not found." );
            } else {
                return parent.getSpawns();
            }
        } else {
            return rawSpawns;
        }
    }

    @Override
    public void setSpawns( List<SkyPlayerLocation> spawns ) {
        this.rawSpawns = spawns;
    }

    @Override
    public int getNumTeams() {
        if ( rawNumTeams == null ) {
            if ( parent == null ) {
                throw new IllegalStateException( "Ultimate parent numTeams not found." );
            } else {
                return parent.getNumTeams();
            }
        } else {
            return rawNumTeams.intValue();
        }
    }

    @Override
    public void setNumTeams( Integer numTeams ) {
        if ( numTeams != null && numTeams < 2 ) {
            throw new IllegalArgumentException( "Num teams can't be smaller than 2" );
        }
        this.rawNumTeams = numTeams;
    }

    @Deprecated
    @Override
    public int getNumPlayers() {
        return getNumTeams() * getTeamSize();
    }

    @Deprecated
    @Override
    public void setNumPlayers( Integer numPlayers ) {
        if ( numPlayers == null ) {
            rawNumTeams = null;
        } else {
            if ( rawTeamSize == null ) {
                rawTeamSize = 1;
            } else if ( numPlayers % rawTeamSize != 0 ) {
                throw new IllegalArgumentException( "numPlayers must be divisible by teamSize, which is " + rawTeamSize );
            }
            rawNumTeams = numPlayers / rawTeamSize;
        }
    }

    @Deprecated
    @Override
    public Integer getRawNumPlayers() {
        return null;
    }

    @Override
    public int getTeamSize() {
        if ( rawTeamSize == null ) {
            if ( parent == null ) {
                throw new IllegalStateException( "Ultimate parent numPlayers not found." );
            } else {
                return parent.getTeamSize();
            }
        } else {
            return rawTeamSize.intValue();
        }
    }

    @Override
    public void setTeamSize( Integer teamSize ) {
        if ( teamSize != null && teamSize < 1 ) {
            throw new IllegalArgumentException( "Team size can't be smaller than 1" );
        }
    }

    @Override
    public SkyBoundariesConfig getBoundaries() {
        return boundaries;
    }

    @Override
    public SkyPlacementConfig getPlacement() {
        return placement;
    }

    @Override
    public SkyMessagesConfig getMessages() {
        return messages;
    }

    public void serialize( ConfigurationSection section ) {
        section.set( "config-version", 1 );
        if ( rawSpawns != null ) {
            List<Map> spawnsList = new ArrayList<Map>( rawSpawns.size() );
            for ( SkyPlayerLocation loc : rawSpawns ) {
                spawnsList.add( loc.serialize() );
            }
            section.set( "spawns", spawnsList );
        }
        section.set( "num-teams", rawNumTeams );
        section.set( "team-size", rawTeamSize );
        if ( boundaries.definesAnything() ) {
            boundaries.serialize( section.createSection( "boundaries" ) );
        }
        if ( placement.definesAnything() ) {
            placement.serialize( section.createSection( "placement" ) );
        }
        if ( messages.definesAnything() ) {
            messages.serialize( section.createSection( "messages" ) );
        }
    }

    public static SkyArenaConfig deserialize( ConfigurationSection configurationSection ) {
        if ( configurationSection.getInt( "config-version" ) != 1 ) {
            throw new IllegalArgumentException( "Config version not 1" );
        }
        ConfigurationSection boundariesSection = configurationSection.getConfigurationSection( "boundaries" ),
                placementSection = configurationSection.getConfigurationSection( "placement" ),
                messagesSection = configurationSection.getConfigurationSection( "messages" );
        List<?> spawnsObjList = configurationSection.getList( "spawns" );
        List<SkyPlayerLocation> spawns = null;
        if ( spawnsObjList != null ) {
            spawns = new ArrayList<SkyPlayerLocation>( spawnsObjList.size() );
            for ( Object obj : spawnsObjList ) {
                if ( obj instanceof Map ) {
                    SkyPlayerLocation loc = SkyPlayerLocation.deserialize( (Map) obj );
                    if ( loc == null ) {
                        continue;
                    }
                    spawns.add( loc );
                } else {
                    SkyStatic.getLogger().log( Level.WARNING, "[SkyArenaConfig] Non-Map object {0} found in arena configuration spawn list. Ignoring it", obj );
                }
            }
        }
        Integer numPlayers = configurationSection.isInt( "num-teams" ) ? configurationSection.getInt( "num-teams" ) : null;
        Integer teamSize = configurationSection.isInt( "team-size" ) ? configurationSection.getInt( "team-size" ) : null;
        SkyBoundariesConfig boundaries = boundariesSection != null ? SkyBoundariesConfig.deserialize( boundariesSection ) : null;
        SkyPlacementConfig placement = placementSection != null ? SkyPlacementConfig.deserialize( placementSection ) : null;
        SkyMessagesConfig messages = messagesSection != null ? SkyMessagesConfig.deserialize( messagesSection ) : null;
        return new SkyArenaConfig( null, spawns, numPlayers, teamSize, boundaries, placement, messages );
    }

    public String toIndentedString( int indentAmount ) {
        return "SkyArenaConfig{\n"
                + ( parent == null ? "" : getIndent( indentAmount + 1 ) + "parent=" + parent.toIndentedString( indentAmount + 1 ) + ",\n" )
                + ( rawSpawns == null ? "" : getIndent( indentAmount + 1 ) + "spawns=" + rawSpawns + ",\n" )
                + ( rawNumTeams == null ? "" : getIndent( indentAmount + 1 ) + "numTeams=" + rawNumTeams + ",\n" )
                + ( rawTeamSize == null ? "" : getIndent( indentAmount + 1 ) + "teamSize=" + rawTeamSize + ",\n" )
                + ( boundaries == null ? "" : getIndent( indentAmount + 1 ) + "boundaries=" + boundaries.toIndentedString( indentAmount + 1 ) + ",\n" )
                + ( placement == null ? "" : getIndent( indentAmount + 1 ) + "placement=" + placement.toIndentedString( indentAmount + 1 ) + ",\n" )
                + ( messages == null ? "" : getIndent( indentAmount + 1 ) + "messages=" + messages.toIndentedString( indentAmount + 1 ) + ",\n" )
                + getIndent( indentAmount ) + "}";
    }

    private String getIndent( int indentAmount ) {
        return StringUtils.repeat( "\t", indentAmount );
    }
}
