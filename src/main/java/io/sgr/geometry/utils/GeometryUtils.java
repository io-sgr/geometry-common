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
 */
package io.sgr.geometry.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.sgr.geometry.Coordinate;

/**
 * @author SgrAlpha
 *
 */
public class GeometryUtils {

	/**
	 * The maximum longitude
	 */
	public static final int LNG_MAX = 179999999;
	/**
	 * The minimum longitude
	 */
	public static final int LNG_MIN = -180000000;
	/**
	 * The maximum latitude
	 */
	public static final int LAT_MAX = 89999999;
	/**
	 * The minimum latitude
	 */
	public static final int LAT_MIN = -90000000;

	private static final JsonFactory JSON_FACTORY = new JsonFactory();
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(JSON_FACTORY);
	static {
		//		OBJECT_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
	}
	
	/**
	 * @return
	 * 			The default json factory
	 */
	public static JsonFactory getJsonFactory() {
		return JSON_FACTORY;
	}

	/**
	 * @return
	 * 			The default object mapper
	 */
	public static ObjectMapper getObjectMapper() {
		return OBJECT_MAPPER;
	}
	
	private static final CoordinateChecker DEFAULT_COORDINATE_CHECKER = new DefaultCoordinateChecker();
	private static CoordinateChecker COORDINATE_CHECKER = DEFAULT_COORDINATE_CHECKER;
	
	/**
	 * Override the global CoordinateChecker with a customized implementation.
	 * Set to null will go back to default implementation.
	 * 
	 * @param checker
	 * 			The CoordinateChecker to set
	 */
	public static void setCoordinateChecker(CoordinateChecker checker) {
		COORDINATE_CHECKER = checker == null ? DEFAULT_COORDINATE_CHECKER : checker;
	}

	/**
	 * @param lat
	 * 			The latitude
	 * @return
	 * 			If the latitude is in a valid range
	 */
	public static final boolean latInRange(double lat) {
		return lat >= (LAT_MIN / 1e6f) && lat <= (LAT_MAX / 1e6f);
	}

	/**
	 * @param lng
	 * 			The longitude
	 * @return
	 * 			If the longitude is in a valid range
	 */
	public static final boolean lngInRange(final double lng) {
		return lng >= (LNG_MIN / 1e6f) && lng <= (LNG_MAX / 1e6f);
	}

	/**
	 * @param lat
	 * 			The latitude
	 * @param lng
	 * 			The longitude
	 * @return
	 * 			If both latitude and longitude are in valid range
	 */
	public static final boolean isValidCoordinate(final double lat, final double lng) {
		return latInRange(lat) && lngInRange(lng);
	}

	/**
	 * Convert coordinate from earth(WGS-84) to mars(GCJ-02).
	 * 
	 * @param wgs
	 * 			The WGS coordinate
	 * @return
	 * 			An {@link Coordinate} mars coordinate
	 */
	public static Coordinate wgs2gcj(Coordinate wgs) {
		double wgsLat = wgs.getLat();
		double wgsLng = wgs.getLng();
		if (COORDINATE_CHECKER.isOutOfChinaMainland(wgsLat, wgsLng)) {
			return new Coordinate(wgsLat, wgsLng);
		}
		Coordinate d = delta(wgsLat, wgsLng);
		return new Coordinate(wgsLat + d.getLat(), wgsLng + d.getLng());
	}

	/**
	 * Convert coordinate from mars(GCJ-02) to earth(WGS-84) in a more accurate but slower way.
	 * 
	 * @param gcj
	 * 			The GCJ coordinate
	 * @return
	 * 			An {@link Coordinate} earth coordinate
	 */
	public static Coordinate gcj2wgsAccurate(final Coordinate gcj) {
		double gcjLat = gcj.getLat();
		double gcjLng = gcj.getLng();
		if (COORDINATE_CHECKER.isOutOfChinaMainland(gcjLat, gcjLng)) {
			return new Coordinate(gcjLat, gcjLng);
		}
		double initDelta = 0.01;
		double threshold = 0.000001;
		double dLat = initDelta, dLng = initDelta;
		double mLat = gcjLat - dLat, mLng = gcjLng - dLng;
		double pLat = gcjLat + dLat, pLng = gcjLng + dLng;
		Coordinate wgs = new Coordinate(mLat, mLng);
		Coordinate tmp;
		double wgsLat, wgsLng;
		for (int i = 0; i < 30; i++) {
			wgsLat = (mLat + pLat) / 2;
			wgsLng = (mLng + pLng) / 2;
			wgs = new Coordinate(wgsLat, wgsLng);
			tmp = wgs2gcj(wgs);
			dLat = tmp.getLat() - gcjLat;
			dLng = tmp.getLng() - gcjLng;
			if ((Math.abs(dLat) < threshold) && (Math.abs(dLng) < threshold)) {
				return wgs;
			}
			if (dLat > 0) {
				pLat = wgsLat;
			} else {
				mLat = wgsLat;
			}
			if (dLng > 0) {
				pLng = wgsLng;
			} else {
				mLng = wgsLng;
			}
		}
		return wgs;
	}

	/**
	 * Convert coordinate from mars(GCJ-02) to earth(WGS-84).
	 * 
	 * @param gcj
	 * 			The GCJ coordinate
	 * @return
	 * 			An {@link Coordinate} earth coordinate
	 */
	public static Coordinate gcj2wgs(final Coordinate gcj) {
		double gcjLat = gcj.getLat();
		double gcjLng = gcj.getLng();
		if (COORDINATE_CHECKER.isOutOfChinaMainland(gcjLat, gcjLng)) {
			return new Coordinate(gcjLat, gcjLng);
		}
		Coordinate d = delta(gcjLat, gcjLng);
		return new Coordinate(gcjLat - d.getLat(), gcjLng - d.getLng());
	}

	private static Coordinate delta(double lat, double lng) {
		double a = 6378137.0;
		double ee = 0.00669342162296594323;
		double dLat = transformLat(lng - 105.0, lat - 35.0);
		double dLng = transformLng(lng - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * Math.PI;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
		dLng = (dLng * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);
		return new Coordinate(dLat, dLng);
	}

	private static double transformLat(double x, double y) {
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * Math.PI) + 40.0 * Math.sin(y / 3.0 * Math.PI)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * Math.PI) + 320 * Math.sin(y * Math.PI / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	private static double transformLng(double x, double y) {
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * Math.PI) + 40.0 * Math.sin(x / 3.0 * Math.PI)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * Math.PI) + 300.0 * Math.sin(x / 30.0 * Math.PI)) * 2.0 / 3.0;
		return ret;
	}
	
}
