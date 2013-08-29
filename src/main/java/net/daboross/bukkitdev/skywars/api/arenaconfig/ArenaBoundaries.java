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

import net.daboross.bukkitdev.skywars.api.Parentable;
import net.daboross.bukkitdev.skywars.api.location.SkyBlockLocationRange;

/**
 *
 * @author daboross
 */
public class ArenaBoundaries extends Parentable<ArenaBoundaries> {

    private SkyBlockLocationRange origin;
    private SkyBlockLocationRange building;
    private SkyBlockLocationRange clearing;

    public ArenaBoundaries() {
    }

    public ArenaBoundaries(ArenaBoundaries parent) {
        super(parent);
    }

    public ArenaBoundaries(SkyBlockLocationRange origin, SkyBlockLocationRange building, SkyBlockLocationRange clearing) {
        this.origin = origin;
        this.building = building;
        this.clearing = clearing;
    }

    public ArenaBoundaries(ArenaBoundaries parent, SkyBlockLocationRange origin, SkyBlockLocationRange building, SkyBlockLocationRange clearing) {
        super(parent);
        this.origin = origin;
        this.building = building;
        this.clearing = clearing;
    }

    public void copyDataFrom(ArenaBoundaries boundaries) {
        this.origin = boundaries.origin;
        this.building = boundaries.building;
        this.clearing = boundaries.clearing;
    }

    public SkyBlockLocationRange getOrigin() {
        if (this.origin == null) {
            if (parent == null) {
                throw new IllegalStateException("Ultimate parent origin boundary not found.");
            } else {
                return parent.getOrigin();
            }
        }
        return origin;
    }

    public SkyBlockLocationRange getBuilding() {
        if (this.origin == null) {
            if (parent == null) {
                throw new IllegalStateException("Ultimate parent building boundary not found.");
            } else {
                return parent.getOrigin();
            }
        }
        return building;
    }

    public SkyBlockLocationRange getClearing() {
        if (this.clearing == null) {
            if (parent == null) {
                throw new IllegalStateException("Ultimate parent clearing boundary not found.");
            } else {
                return parent.getClearing();
            }
        }
        return clearing;
    }

    public void setOrigin(SkyBlockLocationRange origin) {
        this.origin = origin;
    }

    public void setBuilding(SkyBlockLocationRange building) {
        this.building = building;
    }

    public void setClearing(SkyBlockLocationRange clearing) {
        this.clearing = clearing;
    }

    @Override
    public String toString() {
        return "ArenaBoundaries{parent=" + parent + ",origin=" + origin + ",building=" + building + ",clearing=" + clearing + "}";
    }
}
