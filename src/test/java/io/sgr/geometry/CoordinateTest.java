/*
 * Copyright 2017-2019 SgrAlpha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.sgr.geometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author SgrAlpha
 */
public class CoordinateTest {

    @Test
    public void testToString() {
        Coordinate earth = new Coordinate(39.980945, 116.348120);
        assertNotEquals("{}", earth.toString());
    }

    @Test
    public void testHashCodeAndEquals() {
        Coordinate earth = new Coordinate(39.980945, 116.348120);
        Coordinate earth2 = new Coordinate(39.980945, 116.348120);
        Coordinate earth3 = new Coordinate(41.340123, 120.157832);
        Coordinate earth4 = new Coordinate(39.980945, 120.157832);
        Set<Coordinate> coordinates = new HashSet<>(Arrays.asList(earth, earth2, earth3));
        assertEquals(2, coordinates.size());
        assertFalse(earth.equals(null));
        assertEquals(earth, earth);
        assertNotEquals(earth, Coordinate.class);
        assertNotEquals(earth, earth3);
        assertNotEquals(earth, earth4);
    }

    @Test
    public void testCoordinate() {
        Coordinate earth = new Coordinate(39.980945, 116.348120);
        assertEquals(39.980945, earth.getLat(), 0);
        assertEquals(116.348120, earth.getLng(), 0);
        assertEquals(39980945, earth.getLatE6());
        assertEquals(116348120, earth.getLngE6());
        try {
            earth = new Coordinate(100.000, 200.000);
            fail("There should be an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Ignored
        }
        Coordinate earth2 = new Coordinate(39.980945, 116.348120);
        assertEquals(earth, earth2);

        Coordinate earth3 = Coordinate.parseCommaSeparatedString("39.918763,116.478398");
        assertEquals(39918763, earth3.getLatE6());
        assertEquals(116478398, earth3.getLngE6());
        assertEquals(39.918763, earth3.getLat(), 0);
        assertEquals(116.478398, earth3.getLng(), 0);

        Coordinate coordinate = Coordinate.parseCommaSeparatedHexString("02c94442,04f21576");
        assertEquals(46.744642, coordinate.getLat(), 0);
        assertEquals(82.97407, coordinate.getLng(), 0);
        assertEquals(46744642, coordinate.getLatE6());
        assertEquals(82974070, coordinate.getLngE6());

        coordinate = Coordinate.parseCommaSeparatedHexString("02efe6e4,f8a92474");
        assertEquals(49.276644, coordinate.getLat(), 0);
        assertEquals(-123.132812, coordinate.getLng(), 0);
        assertEquals(49276644, coordinate.getLatE6());
        assertEquals(-123132812, coordinate.getLngE6());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseFromNull() {
        Coordinate.parseCommaSeparatedString(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseFromBlank() {
        Coordinate.parseCommaSeparatedString("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseFromInvalid() {
        Coordinate.parseCommaSeparatedString("a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseFromNotNumber() {
        Coordinate.parseCommaSeparatedString("a,b");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseFromNullHex() {
        Coordinate.parseCommaSeparatedHexString(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testParseFromBlankHex() {
        Coordinate.parseCommaSeparatedHexString("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseFromInvalidHex() {
        Coordinate.parseCommaSeparatedHexString("a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseFromNotNumberHex() {
        Coordinate.parseCommaSeparatedHexString("g,g");
    }
}
