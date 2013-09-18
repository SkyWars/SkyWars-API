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

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.daboross.bukkitdev.skywars.api.parent.Parentable;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;

/**
 *
 * @author Dabo Ross <http://www.daboross.net/>
 */
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class SkyPlacementConfig extends Parentable<SkyPlacementConfig> implements SkyPlacement {

    @Setter
    private Integer placementY;
    @Setter
    private Integer distanceApart;

    public SkyPlacementConfig( SkyPlacementConfig parent ) {
        super( parent );
    }

    public SkyPlacementConfig( Integer placementY, Integer distanceApart, SkyPlacementConfig parent ) {
        super( parent );
        this.placementY = placementY;
        this.distanceApart = distanceApart;
    }

    public void copyDataFrom( SkyPlacementConfig placement ) {
        this.placementY = placement.placementY;
        this.distanceApart = placement.distanceApart;
    }

    @Override
    public boolean definesAnything() {
        return placementY != null || distanceApart != null;
    }

    @Override
    public int getPlacementY() {
        if ( placementY == null ) {
            if ( parent != null ) {
                return parent.getPlacementY( this );
            } else {
                throw new IllegalStateException( "Original does not define placementY" );
            }
        } else {
            return placementY;
        }
    }

    public int getPlacementYInternal() {
        return placementY;
    }

    public int getPlacementY( SkyPlacementConfig original ) {
        if ( placementY == null ) {
            if ( parent != null ) {
                return parent.getPlacementY( original );
            } else {
                throw new IllegalStateException( "Ultimate parent does not define placementY; original=" + original.toIndentedString( 2 ) );
            }
        } else {
            return placementY;
        }
    }

    @Override
    public int getDistanceApart() {
        if ( distanceApart == null ) {
            if ( parent != null ) {
                return parent.getDistanceApart( this );
            } else {
                throw new IllegalArgumentException( "Original does not define distanceApart" );
            }
        } else {
            return distanceApart;
        }
    }

    public int getDistanceApartInternal() {
        return distanceApart;
    }

    private int getDistanceApart( SkyPlacementConfig original ) {
        if ( distanceApart == null ) {
            if ( parent != null ) {
                return parent.getDistanceApart( original );
            } else {
                throw new IllegalArgumentException( "Ultimate parent does not define distanceApart; original=" + original.toIndentedString( 2 ) );
            }
        } else {
            return distanceApart;
        }
    }

    public void serialize( ConfigurationSection section ) {
        section.set( "placement-y", placementY );
        section.set( "distance-apart", distanceApart );
    }

    public static SkyPlacementConfig deserialize( ConfigurationSection section ) {
        return new SkyPlacementConfig( section.getInt( "placement-y" ), section.getInt( "distance-apart" ) );
    }

    public String toIndentedString( int indentAmount ) {
        return "SkyBoundariesConfig{\n"
                + ( parent == null ? "" : getIndent( indentAmount + 1 ) + "parent=" + parent.toIndentedString( indentAmount + 1 ) + ",\n" )
                + ( placementY == null ? "" : getIndent( indentAmount + 1 ) + "placementY=" + placementY + ",\n" )
                + ( distanceApart == null ? "" : getIndent( indentAmount + 1 ) + "distanceApart=" + distanceApart + ",\n" )
                + getIndent( indentAmount ) + "}";
    }

    private String getIndent( int indentAmount ) {
        return StringUtils.repeat( "\t", indentAmount );
    }
}
