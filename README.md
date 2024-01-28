## 프로젝트 소개

- Unsplash API를 통한 이미지 검색, 좋아요 기능을 포함한 Android App 입니다.

## 프로젝트 구조

![프로젝트 구조](https://github.com/raindragonn/FavoriteImage/assets/48205909/16c31dac-0fc4-462d-ad80-4e17310cc1ef)

- App: Android Component, UI 요소
- Data: Api(네트워크 작업), 로컬 데이터 베이스 등의 데이터 요소
- Domain: 앱에서 사용하는 비즈니스 로직
- 페이지네이션: Paging3를 참고해서 만들었습니다.

### 주요 버전

|                |          Version          |
|----------------|:-------------------------:|
| MinSdk         |            26             |
| TargetSdk      |            33             |
| Kotlin         |          1.9.22           |
| Android Studio | Hedgehog 2023.1.1 Patch 1 |

## 기술 스택

| 분야          | 기술 스택                      |
|-------------|----------------------------|
| **언어**      | `Kotlin`                   |
| **UI**      | `Xml, ViewBinding`         |
| **의존성 주입**  | `Hilt`                     |
| **구조**      | `Clean Architecture, MVVM` |
| **jetpack** | `Room,Navigation,Hilt`     |
| **Network** | `Retrofit2, OkHttp3`       |
| **이미지**     | `Glide`                    |
| **비동기 처리**  | `Flow, Coroutine`          |