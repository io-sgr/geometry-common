/*
 * Copyright 2017-2019 SgrAlpha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.sgr.geometry.utils;

import static org.junit.Assert.assertEquals;

import io.sgr.geometry.Coordinate;

import org.junit.Test;

/**
 * @author SgrAlpha
 */
public class GeometryUtilsTest {

    @Test
    public void testEarthToMars() {
        Coordinate mars = GeometryUtils.wgs2gcj(new Coordinate(39.980945, 116.348120));
        assertEquals(39.982296901199675, mars.getLat(), 0.000001);
        assertEquals(116.35430834755688, mars.getLng(), 0.000001);
        assertEquals(39982296, mars.getLatE6());
        assertEquals(116354308, mars.getLngE6());
        Coordinate outOfChina = GeometryUtils.wgs2gcj(new Coordinate(41.558182, -73.915166));
        assertEquals(41.558182, outOfChina.getLat(), 0.000001);
        assertEquals(-73.915166, outOfChina.getLng(), 0.000001);
        assertEquals(41558182, outOfChina.getLatE6());
        assertEquals(-73915166, outOfChina.getLngE6());
    }

    @Test
    public void testMarsToEarth() {
        Coordinate earth = GeometryUtils.gcj2wgsAccurate(new Coordinate(39.98229687830853, 116.35430824277233));
        assertEquals(39.980945, earth.getLat(), 0.000001);
        assertEquals(116.348120, earth.getLng(), 0.000001);
        assertEquals(39980945, earth.getLatE6());
        assertEquals(116348120, earth.getLngE6());
        Coordinate outOfChina = GeometryUtils.gcj2wgsAccurate(new Coordinate(41.558182, -73.915166));
        assertEquals(41.558182, outOfChina.getLat(), 0.000001);
        assertEquals(-73.915166, outOfChina.getLng(), 0.000001);
        assertEquals(41558182, outOfChina.getLatE6());
        assertEquals(-73915166, outOfChina.getLngE6());

        earth = GeometryUtils.gcj2wgs(new Coordinate(39.98229687830853, 116.35430824277233));
        assertEquals(39.980934, earth.getLat(), 0.000001);
        assertEquals(116.348106, earth.getLng(), 0.000001);
        assertEquals(39980934, earth.getLatE6());
        assertEquals(116348106, earth.getLngE6());
        outOfChina = GeometryUtils.gcj2wgs(new Coordinate(41.558182, -73.915166));
        assertEquals(41.558182, outOfChina.getLat(), 0.000001);
        assertEquals(-73.915166, outOfChina.getLng(), 0.000001);
        assertEquals(41558182, outOfChina.getLatE6());
        assertEquals(-73915166, outOfChina.getLngE6());
    }

}
