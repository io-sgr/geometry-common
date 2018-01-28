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
package io.sgr.geometry;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.sgr.geometry.utils.GeometryUtils;

/**
 * @author SgrAlpha
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Coordinate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 447283454620410061L;
	
	private final double lat;
	private final double lng;
	
	/**
	 * @param lat
	 * 				The latitude
	 * @param lng
	 * 				The longitude
	 * @throws IllegalArgumentException
	 * 			If the latitude or longitude is out of range. Check {@link io.sgr.geometry.utils.GeometryUtils#isValidCoordinate(double, double)} for details.
	 */
	@JsonCreator
	public Coordinate(
			@JsonProperty("lat") double lat,
			@JsonProperty("lng") double lng
			) throws IllegalArgumentException {
		if (!GeometryUtils.isValidCoordinate(lat, lng)) {
			throw new IllegalArgumentException(String.format("Invalid coordinate: { lat: %f, lng: %f }", lat, lng));
		}
		this.lat = lat;
		this.lng = lng;
	}

	/**
	 * Parse a comma separated string to coordinate object
	 * 
	 * @param rawCoordinate
	 * 				A comma separated string, like 0,0
	 * @return
	 * 				The coordinate object.
	 * @throws IllegalArgumentException
	 * 			If failed to construct Coordinate object.
	 */
	public static Coordinate parseCommaSeparatedString(String rawCoordinate) throws IllegalArgumentException {
		if (rawCoordinate == null || rawCoordinate.trim().length() < 1) {
			throw new IllegalArgumentException(String.format("Invalid comma separated coordinate string: %s", rawCoordinate));
		}
		String[] array = rawCoordinate.split(",");
		if (array.length != 2) {
			throw new IllegalArgumentException(String.format("Invalid comma separated coordinate string: %s", rawCoordinate));
		}
		try {
			double lat = Double.parseDouble(array[0]);
			double lng = Double.parseDouble(array[1]);
			return new Coordinate(lat, lng);
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format("Invalid comma separated coordinate string: %s", rawCoordinate));
		}
	}
	
	/**
	 * Parse a comma separated hex string to coordinate object
	 * 
	 * @param rawCoordinate
	 * 				A comma separated coordinate string in hex format, like 02c94442,04f21576
	 * @return
	 * 				The coordinate object.
	 * @throws IllegalArgumentException
	 * 			If failed to construct Coordinate object.
	 */
	public static Coordinate parseCommaSeparatedHexString(String rawCoordinate) {
		if (rawCoordinate == null || rawCoordinate.trim().length() < 1) {
			throw new IllegalArgumentException(String.format("Invalid comma separated coordinate hex string: %s", rawCoordinate));
		}
		String[] tmp = rawCoordinate.split(",");
		if (tmp.length != 2) {
			throw new IllegalArgumentException(String.format("Invalid comma separated coordinate hex string: %s", rawCoordinate));
		}
		long latE6 = (int) Long.parseLong(tmp[0], 16);
		long lngE6 = (int) Long.parseLong(tmp[1], 16);
		return new Coordinate(latE6 / 1e6, lngE6 / 1e6);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		try {
			return GeometryUtils.getObjectMapper().writeValueAsString(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "{}";
	}
	
	/**
	 * @return
	 * 				The latitude
	 */
	public double getLat() {
		return this.lat;
	}

	/**
	 * @return
	 * 				The longitude
	 */
	public double getLng() {
		return this.lng;
	}

	/**
	 * @return
	 * 				The E6 value of latitude
	 */
	@JsonIgnore
	public int getLatE6() {
		return (int) Math.floor(this.lat * 1e6);
	}

	/**
	 * @return
	 * 				The E6 value of longitude
	 */
	@JsonIgnore
	public int getLngE6() {
		return (int) Math.floor(this.lng * 1e6);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.lat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.lng);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (Double.doubleToLongBits(this.lat) != Double.doubleToLongBits(other.lat))
			return false;
		if (Double.doubleToLongBits(this.lng) != Double.doubleToLongBits(other.lng))
			return false;
		return true;
	}

}
