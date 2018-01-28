/*
 * Copyright 2017-2018 SgrAlpha
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
 *
 */
package io.sgr.geometry.utils;

/**
 * The default implementation of CoordinateChecker, which will treat Hong Kong, Macau and Taiwan out of China mainland.
 * This implementation use box boundaries to check the location, useful but not accurate.
 * 
 * @author SgrAlpha
 *
 */
public class DefaultCoordinateChecker implements CoordinateChecker {

	/* (non-Javadoc)
	 * @see io.sgr.geometry.utils.GeometryUtils.CoordinateChecker#isOutOfChinaMainland(double, double)
	 */
	@Override
	public boolean isOutOfChinaMainland(double lat, double lng) {
		return (lat < 22.446195 && lng > 113.678580 && lng < 114.427582)
				|| (lat < 22.217493 && lat > 22.177243 && lng > 113.528421 && lng < 113.563058)
				|| (lat < 25.401950 && lng < 125.502319 && lat > 21.675348 && lng > 119.827835)
				|| (lng < 72.004) || (lng > 137.8347) || (lat < 0.8293) || (lat > 55.8271);
	}

}
