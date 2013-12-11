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
package net.daboross.bukkitdev.skywars.api.parent;

import org.junit.Test;
import static org.junit.Assert.*;

public class ParentableTest {

    @Test
    public void testSetGetParent() {
        DummyParentable dummy = new DummyParentable();
        DummyParentable parent = new DummyParentable();
        assertEquals(null, dummy.getParent());
        dummy.setParent(parent);
        assertEquals(parent, dummy.getParent());
    }

    /**
     * Test of checkParentCycle method, of class Parentable.
     */
    @Test
    public void testCheckParentCycle() {
        DummyParentable dummy = new DummyParentable();
        DummyParentable parent = new DummyParentable();
        DummyParentable parent2 = new DummyParentable();
        dummy.setParent(parent);
        parent.setParent(parent2);
        try {
            parent2.setParent(dummy);
            fail("IllegalArgumentException not called for parent inheritance loop");
        } catch (IllegalArgumentException unused) {
        }
    }

    public static class DummyParentable extends Parentable<DummyParentable> {
    }
}
