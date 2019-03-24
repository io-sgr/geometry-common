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

package io.sgr.geometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * @author SgrAlpha
 */
public class WeightedLocationTest {

    @Test
    public void testConstruct() {
        final WeightedLocation loc = new WeightedLocation(new Coordinate(0, 0), 99);
        assertNotNull(loc.getLocation());
        assertEquals(99, loc.getWeight(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingCoordinate() {
        new WeightedLocation(null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidWeight() {
        new WeightedLocation(new Coordinate(0, 0), -1);
    }

}
