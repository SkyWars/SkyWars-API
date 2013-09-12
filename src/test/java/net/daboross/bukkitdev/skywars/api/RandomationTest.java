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
package net.daboross.bukkitdev.skywars.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dabo Ross <http://www.daboross.net/>
 */
public class RandomationTest
{

    public RandomationTest()
    {
    }

    /**
     * Test of getRandom method, of class Randomation.
     */
    @Test
    public void testGetRandom()
    {
        List<Integer> list = new ArrayList<Integer>();
        list.addAll( Arrays.asList( 1, 0, 02, 320, 43, 21, 123 ) );
        for ( int i = 0 ; i < 10 ; i++ )
        {
            assertEquals( true, list.contains( Randomation.getRandom( list ) ) );
        }
    }
}
