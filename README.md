# libMcServerProxy For Android
[![](https://jitpack.io/v/hari01584/libMcServerProxy.svg)](https://jitpack.io/#hari01584/libMcServerProxy)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/hari01584/libMcServerProxy)
![Lines of code](https://img.shields.io/tokei/lines/github/hari01584/libMcServerProxy?color=red)
![GitHub Sponsors](https://img.shields.io/github/sponsors/hari01584?color=violet)
![GitHub issues](https://img.shields.io/github/issues/hari01584/libMcServerProxy)

libMcServerProxy is an *Android* library for brodcasting Minecraft servers to your lan without going through hassles of manually doing so! All been performed programmatically!
## Installation
Add to your build.gradle(root level):



```
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
Add the dependency
```
implementation 'com.github.hari01584:libMcServerProxy:x.x'
```

## Usage

Simplest Code!
```java
MConnector.with(getContext())
  .setTargetServer(<Target ServerIp>, <Target ServerPort>)
  .start();
```
Note: The class throws multiple exceptions so be sure to catch them! Also it needs VPN Bind permission to work, or it will throw exception!

*Code it like*
```
try {
  MConnector.with(getContext())
    .setTargetServer("gh", 1235)
    .start();
} catch (NoVPNException e) {
  e.printStackTrace();
} catch (ServerNotSetException e) {
  e.printStackTrace();
} catch (MinecraftNotFoundException e) {
  e.printStackTrace();
}
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## Credits
libMcServerProxy would not be possible without these open source repos! Huge thanks to them :D

[hexene-LocalVPN](https://github.com/hexene/LocalVPN) - Backbone VPN Service, Core of program!!

## License
GPLv3

    Copyright (C) 2021 Harishankar Kumar
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.


**Free Software, Hell Yeah!**
