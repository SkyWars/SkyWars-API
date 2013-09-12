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

import java.util.HashMap;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.daboross.bukkitdev.skywars.api.parent.Parentable;
import net.daboross.bukkitdev.skywars.api.location.SkyBlockLocationRange;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

/**
 *
 * @author Dabo Ross <http://www.daboross.net/>
 */
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true)
@SerializableAs("SkyBoundaries")
public class SkyBoundariesConfig extends Parentable<SkyBoundariesConfig> implements ConfigurationSerializable, SkyBoundaries
{

    private SkyBlockLocationRange origin;
    private SkyBlockLocationRange building;
    private SkyBlockLocationRange clearing;

    public SkyBoundariesConfig()
    {
    }

    public SkyBoundariesConfig( SkyBoundariesConfig parent )
    {
        super( parent );
    }

    public SkyBoundariesConfig( SkyBlockLocationRange origin, SkyBlockLocationRange building, SkyBlockLocationRange clearing )
    {
        this.origin = origin;
        this.building = building;
        this.clearing = clearing;
    }

    public SkyBoundariesConfig( SkyBlockLocationRange origin, SkyBlockLocationRange building, SkyBlockLocationRange clearing, SkyBoundariesConfig parent )
    {
        super( parent );
        this.origin = origin;
        this.building = building;
        this.clearing = clearing;
    }

    public void copyDataFrom( SkyBoundariesConfig boundaries )
    {
        this.origin = boundaries.origin;
        this.building = boundaries.building;
        this.clearing = boundaries.clearing;
    }

    @Override
    public boolean definesAnything()
    {
        return origin != null || building != null || clearing != null;
    }

    @Override
    public SkyBlockLocationRange getOrigin()
    {
        if ( this.origin == null )
        {
            if ( parent == null )
            {
                throw new IllegalStateException( "Ultimate parent origin boundary not found." );
            } else
            {
                return parent.getOrigin();
            }
        }
        return origin;
    }

    @Override
    public SkyBlockLocationRange getBuilding()
    {
        if ( this.origin == null )
        {
            if ( parent == null )
            {
                throw new IllegalStateException( "Ultimate parent building boundary not found." );
            } else
            {
                return parent.getBuilding();
            }
        }
        return building;
    }

    @Override
    public SkyBlockLocationRange getClearing()
    {
        if ( this.clearing == null )
        {
            if ( parent == null )
            {
                throw new IllegalStateException( "Ultimate parent clearing boundary not found." );
            } else
            {
                return parent.getClearing();
            }
        }
        return clearing;
    }

    @Override
    public void setOrigin( SkyBlockLocationRange origin )
    {
        this.origin = origin;
    }

    @Override
    public void setBuilding( SkyBlockLocationRange building )
    {
        this.building = building;
    }

    @Override
    public void setClearing( SkyBlockLocationRange clearing )
    {
        this.clearing = clearing;
    }

    public SkyBlockLocationRange getOriginInternal()
    {
        return origin;
    }

    public SkyBlockLocationRange getBuildingInternal()
    {
        return building;
    }

    public SkyBlockLocationRange getClearingInternal()
    {
        return clearing;
    }

    @Override
    public Map<String, Object> serialize()
    {
        HashMap<String, Object> map = new HashMap<String, Object>( 3 );
        map.put( "origin", origin );
        map.put( "building", building );
        map.put( "clearing", clearing );
        return map;
    }

    public void serialize( ConfigurationSection section )
    {
        if ( origin != null )
        {
            origin.serialize( section.createSection( "origin" ) );
        }
        if ( building != null )
        {
            building.serialize( section.createSection( "building" ) );
        }
        if ( clearing != null )
        {
            clearing.serialize( section.createSection( "clearing" ) );
        }
    }

    public static SkyBoundariesConfig deserialize( Map<String, Object> map )
    {
        Object originObj = map.get( "origin" ),
                buildingObj = map.get( "building" ),
                clearingObj = map.get( "clearing" );
        SkyBlockLocationRange origin = originObj instanceof SkyBlockLocationRange ? (SkyBlockLocationRange) originObj : null,
                building = buildingObj instanceof SkyBlockLocationRange ? (SkyBlockLocationRange) buildingObj : null,
                clearing = clearingObj instanceof SkyBlockLocationRange ? (SkyBlockLocationRange) clearingObj : null;
        return new SkyBoundariesConfig( origin, building, clearing );
    }

    public static SkyBoundariesConfig deserialize( ConfigurationSection configurationSection )
    {
        SkyBlockLocationRange origin = SkyBlockLocationRange.deserialize( configurationSection.getConfigurationSection( "origin" ) ),
                building = SkyBlockLocationRange.deserialize( configurationSection.getConfigurationSection( "building" ) ),
                clearing = SkyBlockLocationRange.deserialize( configurationSection.getConfigurationSection( "clearing" ) );
        return new SkyBoundariesConfig( origin, building, clearing );
    }

    public String toIndentedString( int indentAmount )
    {
        return "SkyBoundariesConfig{\n"
                + ( parent == null ? "" : getIndent( indentAmount + 1 ) + "parent=" + parent.toIndentedString( indentAmount + 1 ) + ",\n" )
                + ( origin == null ? "" : getIndent( indentAmount + 1 ) + "origin=" + origin.toIndentedString( indentAmount + 1 ) + ",\n" )
                + ( building == null ? "" : getIndent( indentAmount + 1 ) + "building=" + building.toIndentedString( indentAmount + 1 ) + ",\n" )
                + ( clearing == null ? "" : getIndent( indentAmount + 1 ) + "clearing=" + clearing.toIndentedString( indentAmount + 1 ) + ",\n" )
                + getIndent( indentAmount ) + "}";
    }

    private String getIndent( int indentAmount )
    {
        return StringUtils.repeat( "\t", indentAmount );
    }
}
