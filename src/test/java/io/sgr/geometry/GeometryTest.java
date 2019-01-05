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

import org.junit.Assert;
import org.junit.Test;

import io.sgr.geometry.Coordinate;
import io.sgr.geometry.utils.GeometryUtils;

/**
 * @author SgrAlpha
 *
 */
public class GeometryTest {
	
	@Test
	public void testCoordinate() {
		Coordinate earth = new Coordinate(39.980945, 116.348120);
		Assert.assertEquals(39.980945, earth.getLat(), 0);
		Assert.assertEquals(116.348120, earth.getLng(), 0);
		Assert.assertEquals(39980945, earth.getLatE6());
		Assert.assertEquals(116348120, earth.getLngE6());
		try {
			earth = new Coordinate(100.000, 200.000);
			Assert.fail("There should be an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
		Coordinate earth2 = new Coordinate(39.980945, 116.348120);
		Assert.assertEquals(earth, earth2);
		
		Coordinate earth3 = Coordinate.parseCommaSeparatedString("39.918763,116.478398");
		Assert.assertEquals(39918763, earth3.getLatE6());
		Assert.assertEquals(116478398, earth3.getLngE6());
		Assert.assertEquals(39.918763, earth3.getLat(), 0);
		Assert.assertEquals(116.478398, earth3.getLng(), 0);

		Coordinate coordinate = Coordinate.parseCommaSeparatedHexString("02c94442,04f21576");
		Assert.assertEquals(46.744642, coordinate.getLat(), 0);
		Assert.assertEquals(82.97407, coordinate.getLng(), 0);
		Assert.assertEquals(46744642, coordinate.getLatE6());
		Assert.assertEquals(82974070, coordinate.getLngE6());
		
		coordinate = Coordinate.parseCommaSeparatedHexString("02efe6e4,f8a92474");
		Assert.assertEquals(49.276644, coordinate.getLat(), 0);
		Assert.assertEquals(-123.132812, coordinate.getLng(), 0);
		Assert.assertEquals(49276644, coordinate.getLatE6());
		Assert.assertEquals(-123132812, coordinate.getLngE6());
	}
	
	@Test
	public void testEarthToMars() {
		Coordinate mars = GeometryUtils.wgs2gcj(new Coordinate(39.980945, 116.348120));
		Assert.assertEquals(39.982296901199675, mars.getLat(), 0.000001);
		Assert.assertEquals(116.35430834755688, mars.getLng(), 0.000001);
		Assert.assertEquals(39982296, mars.getLatE6());
		Assert.assertEquals(116354308, mars.getLngE6());
	}
	
	@Test
	public void testMarsToEarth() {
		Coordinate earth = GeometryUtils.gcj2wgsAccurate(new Coordinate(39.98229687830853, 116.35430824277233));
		Assert.assertEquals(39.980945, earth.getLat(), 0.000001);
		Assert.assertEquals(116.348120, earth.getLng(), 0.000001);
		Assert.assertEquals(39980945, earth.getLatE6());
		Assert.assertEquals(116348120, earth.getLngE6());
		Coordinate outOfChina = GeometryUtils.gcj2wgsAccurate(new Coordinate(41.558182, -73.915166));
		Assert.assertEquals(41.558182, outOfChina.getLat(), 0.000001);
		Assert.assertEquals(-73.915166, outOfChina.getLng(), 0.000001);
		Assert.assertEquals(41558182, outOfChina.getLatE6());
		Assert.assertEquals(-73915166, outOfChina.getLngE6());
	}
	
}
