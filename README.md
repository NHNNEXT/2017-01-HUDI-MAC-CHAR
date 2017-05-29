# Mafia Online 
[![GitHub issues](https://img.shields.io/github/issues/yandex/gixy.svg?style=flat-square)](https://github.com/NHNNEXT/2017-01-HUDI-MAC-CHAR/issues)
[![GitHub pull requests](https://img.shields.io/github/issues-pr/yandex/gixy.svg?style=flat-square)](https://github.com/NHNNEXT/2017-01-HUDI-MAC-CHAR/pulls)
### TEAM "MAC-CHAR"  *2017 1학기 _HUMAN-DESIGN-PROJECT (웹, 모바일)*
2017년도 1학기 휴먼 디자인 프로젝트 Repository입니다. 'Mapia'라는 게임을 웹과 모바일에서 구현했습니다.
>"밤이 되었습니다!"  
야심한 밤의 단골 게임, 마피아를 온라인으로 가져왔습니다.  
웹, 모바일 환경에서 밤의 지배자가 되어보세요.

![main_icon](https://cloud.githubusercontent.com/assets/3432994/24234224/61b62e0a-0fda-11e7-980b-47a5ebd1c29f.jpg)

## Set up
#### Server running
```bash
# in your empty directroy
$ git clone https://github.com/NHNNEXT/2017-01-HUDI-MAC-CHAR
$ cd WEB-MAC-CHAR
$ npm install
$ npm run dev
$ ./gradlew build
$ ./gradlew bootRun
```
and, enter http://localhost:8080/ on browser.

## Development Environment
* Front-End Function
  * HTML5/CSS3/VanilaJS (ES6)
  * WebPack2
  * BootStrap(partial)
  * Template Engine: Handlebars(partial)
  * SockJS, StompJS
  
* Server Function
  * Spring WebSocket
  * Stomp
  * Framework: SpringBoot framework with Java8(partial)
  * BuildTool: Gradle
  * WAS: Tomcat
  * Database: MySQL
  
* IOS
    * Objective-C
    
* Android
    * Android Studio 

### Contributors
* WEB: @JaeYeopHan, @bbq923, @wkddngus5
* Android: @wlals822
* iOS: @Yongai
