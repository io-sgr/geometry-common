# Geometry Utilities
本地理位置工具库是一个非常小巧的支持 Java 及 Android 的库，主要致力于实现 地球坐标（WGS-84）到火星坐标（GCJ-02）的转换。

*用其他语言阅读: [English](README.md), [简体中文](README.zh-cn.md).*

本工具需要运行在 Java 7 或者 Android 2.3 以上的环境中。

## 使用方式
将地球坐标（WGS-84）转换成火星坐标（GCJ-02）：
```java
Coordinate mars = GeometryUtils.wgs2gcj(39.980945, 116.348120);
```
将火星坐标（GCJ-02）转换成地球坐标（WGS-84）：
```java
Coordinate earth = GeometryUtils.gcj2wgs(39.982296, 116.354308);
```

## 高级
想要实现地球坐标（WGS-84）和火星坐标（GCJ-02）的相互转换，首先你需要知道这个坐标是否位于**中国大陆**境内。

网上常见的大部分实现都是使用一个**巨大**的覆盖中国全境以及一些周边地区的矩形来判定区域，如果坐标落在这一矩形中，则认为坐标需要被转换。快速简单直接粗暴，但弊端也可想而知，比如像**香港**、**澳门**以及**台湾**这些本不应该**被偏移**和**被纠偏**的地区也一并覆盖了。

试想一下如果你的基于地理位置信息的应用在这些国家和地区有用户或者信息的话，他们都会被定位到一个错误的位置上，这是绝对不可接受的。

为了避免这一问题，并给用户带来最高的精度及可自定义性，我们提供了两种解决问题的办法：

### 默认实现
默认情况下，[GeometryUtils](src/main/java/io/sgr/geometry/utils/GeometryUtils.java) 会使用 [DefaultCoordinateChecker](src/main/java/io/sgr/geometry/utils/DefaultCoordinateChecker.java) 来进行地理位置信息的校验。

**DefaultCoordinateChecker** 跟其他大部分实现一样，采用了矩形边界的模式，但已经将**香港**、**澳门**以及**台湾**地区识别为在中国大陆范围之外，这个基本上可以覆盖绝大多数使用场景。

使用者不需要添加额外的代码。

### 自定义实现
如果你对精度还是不满意，你也可以调用自己的实现，代码如下：
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
在你自己的实现中，你可以尽可能的提高精度，也可以调用其他服务来实现坐标或者国家及行政区的判定。

# 许可协议

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
