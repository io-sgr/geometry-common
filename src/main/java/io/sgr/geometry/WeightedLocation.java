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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author SgrAlpha
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeightedLocation {

    private final Coordinate location;
    private float weight;

    /**
     * @param location
     *         The location
     * @param weight
     *         The weight
     * @throws IllegalArgumentException
     *         If the location is null or weight is less or equal to 0
     */
    @JsonCreator
    public WeightedLocation(
            @JsonProperty("location") Coordinate location,
            @JsonProperty("weight") float weight
    ) throws IllegalArgumentException {
        if (location == null) {
            throw new IllegalArgumentException("Location should be provided");
        }
        this.location = location;
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight should be greater than 0");
        }
        this.weight = weight;
    }

    /**
     * @return The location
     */
    @JsonProperty("location")
    public Coordinate getLocation() {
        return this.location;
    }

    /**
     * @return The weight
     */
    @JsonProperty("weight")
    public float getWeight() {
        return this.weight;
    }

}
