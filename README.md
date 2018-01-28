# Geometry Utilities
This library is a simple JAVA / Android library that helps you transform coordinate between earth(WGS-84) and mars in China(GCJ-02).

*Read this in other languages: [English](README.md), [简体中文](README.zh-cn.md).*

Geometry Utilities requires at minimum Java 7 or Android 2.3.

## Usage
Convert coordinate from earth(WGS-84) to mars(GCJ-02):
```java
Coordinate mars = GeometryUtils.wgs2gcj(39.980945, 116.348120);
```
Convert coordinate from mars(GCJ-02) to earth(WGS-84):
```java
Coordinate earth = GeometryUtils.gcj2wgs(39.982296, 116.354308);
```

## Advanced
To convert coordinates from WGS to GCJ or other way back, you need to know if the coordinate is inside **China mainland** or not.

Common implementations use a **HUGE** box boundary which covers entire **China Mainland** and several regions like **Taiwan**, **Hong Kong** and **Macau** along with it to check the coordinate, which is not perfect.

To make sure you have the maximum accuracy and flexibility, Geometry Utilities provided two ways for you to achieve that goal:

### Default Implementation
By default, [GeometryUtils](src/main/java/io/sgr/geometry/utils/GeometryUtils.java) uses a [DefaultCoordinateChecker](src/main/java/io/sgr/geometry/utils/DefaultCoordinateChecker.java) to perform the check.

The **DefaultCoordinateChecker** also uses a box boundary like other implementations but with **Taiwan**, **Hong Kong** and **Macau** excluded, which can cover most of the cases.

No additional code changes needed.

### Customized Implementation
If you are still not satisfied, you can write your own implementation very easily:
```java
public class CustomizedCoordinateChecker implements CoordinateChecker {
	@Override
	public boolean isOutOfChinaMainland(double lat, double lng) {
		// Add your own logic here
		return false;
	}
}
GeometryUtils.setCoordinateChecker(new CustomizedCoordinateChecker());
```
You're all set!

# License

    Copyright 2017-2018 SgrAlpha
   
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
   
       http://www.apache.org/licenses/LICENSE-2.0
   
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
