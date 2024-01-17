# <img width="150" src="https://github.com/zwundzwzig/sokuri/assets/104782275/fdbf6776-0b1c-4549-865d-73438c07aac6" /> <img width="25" src="https://github.com/zwundzwzig/sokuri/assets/104782275/1b2c54d5-1b20-4d23-8f78-fdc1263493a6" />

### 소쿠리 - 플로깅 행사 및 쓰레기통 위치 정보 제공 서비스
- 인기 플로깅 행사 정보를 알려줘요!
- 플로깅이 처음이면 크루 활동을 통해 함께 걷고 주워요!
- 주변 쓰레기통 위치 정보를 알려줘요!
---
## ERD
<img width="825" src="https://github.com/zwundzwzig/sokuri/assets/104782275/db97311d-d550-40bf-972d-c43076024f1c">

#### 주요 도메인
- User : 사용자 정보
- Event : 행사 정보
- Community : 커뮤니티 정보
- Feed : 사용자가 올린 피드 정보
- Image : 사진 정보, 모든 엔티티마다 갖고 있는 사진 정보 저장 엔티티
- TrashCan : 쓰레기통 위치 및 타입 정보

## 기능 소개
### 🚀 현재 진행 중인 플로깅 행사 정보를 확인하고 신청해봐요!
### 👟 플로깅 크루를 직접 모집하거나 다른 크루에 가입할 수 있어요!
### 🚽 주변에 있는 쓰레기통 위치 정보를 알 수 있어요!
### 👍 간편 로그인 기능을 제공해요!

----
## 주요 기능

**더미 데이터 생성 위한 구글 데이터 시트 연동**

[ 개요 ]

- 초기 더미 데이터 생성 시, 서버 개발자가 아닌 구성원 (프론트엔드, 디자이너)도 자유롭게 더미 데이터 생성하기 위해 구현

[ 과정 ]

- 구글 시트 값 불러오는 클래스 `GoogleApiUtil` 를 구현하고, `google-api, client, oauth` 활용해 인증/인가 및 시트 데이터 가져오도록 구현
- resource 디렉토리 안에 구글 접근 인증 정보를 .json으로 확보하고 해당 파일을 읽을 수 있는 지 테스트 케이스용 `SheetsUtils` 클래스 구현

**소셜 로그인 시 jwt 값 전달**

[ 개요 ]

- 카카오 공식 문서에 기재된 flow로 진행, 인가 코드 및 토큰 받는 로직 모두 백엔드 서버에서 구현

[ 과정 ]

- `DefaultOAuth2UserService` 를 상속 받는 `CustomOAuth2UserService` 구현 후 사용자 정보 서버에 저장
- 저장 후 JWT 토큰 발급해 쿠키로 전달

**[ 성과 및 배운 점 ]**

- [OAuth 2.0](https://tools.ietf.org/html/rfc6749) 기반의 소셜 로그인 과정에서 사용자 정보를 어떻게 저장하는 지 보다 어떻게 클라이언트로 응답하는 지에 초점을 맞춰 구현했습니다. 처음엔 jwt 응답을 header에 담는 방식으로 구현했습니다. 하지만 민감한 정보를 헤더에 저장할 시 클라이언트에서 javascript를 통해 접근할 수 있다고 파악해 cookie에 담아 리턴값을 보내고자 `HttpCookieOAuth2AuthorizationRequestRepository` 를 구현했습니다. 이를 통해 XSS, CSRF에 대응할 수 있다고 파악했습니다.

[ 추후 구현 방향 ]

- 더 높은 수준의 보안을 위해 HTTPS를 도입할 예정입니다.

**위도/경도와 도로명 주소 호환 작업 구현**

[ 개요 ]

- 소쿠리는 쓰레기통 위치 정보를 제공해주는 서비스입니다. [서울시 공공 데이터 API](https://data.seoul.go.kr/dataList/OA-15069/F/1/datasetView.do) 에서 엑셀 데이터 형식으로 각 쓰레기통의 도로명 주소를 제공합니다.

[ 과정 ]

- 이 정보를 개인 스프레드 시트에 옮기고, 앞서 구현한 `GoogleApiUtil` 클래스를 활용해 해당 데이터를 불러오도록 구현했습니다.
- `RoadNameAddressToCoordinateConverter` 클래스를 구현했습니다. 또한 도로명 주소를 위도/경도 값으로 치환할 수 있는 라이브러리로 NaverAPI의 `geocode` 를 사용하기로 했습니다. 이때 부분적으로 `WebClient` 를 도입해 도로명 주소를 `queryString` 에 담아 요청을 보내고 그 반환값인 위도/경도 값을 바로 DB에 넣도록 구현했습니다.

[ 추후 구현 방향 ]

- 공공DB에서 제공하는 쓰레기통 위치 도로명 주소의 표기법이 구마다 다르기 때문에 이를 한 번에 처리할 수 있는 방안을 모색할 것입니다.
- 사용자들도 누구나 도로명 주소를 통해 쓰레기통 위치를 서비스에 등록할 수 있도록 구현할 예정입니다.

**전역 로그 관리**

[ 개요 ]

- 에러 발생할 때마다 log 를 찍는 불편함 해소

[ 과정 ]

- `LogAspect` 클래스 구현, `aspectj` 활용해 전역 에러 확인하도록 구현

---
## 개발 환경
- JDK-17.0.5
- SpringBoot 2.7.13 -> 3.1.4 (2023.11.07)
- Gradle-8.0.2
- Junit-5.8.2
- MariaDB-10.6.14

## 협업 환경
- [figma](https://www.figma.com/file/rfpNLaebQz5w88r06vZD1l/Sokuri?type=design&node-id=0-1&mode=design&t=WhaHjrrUwobF1442-0)
- [notion](https://www.notion.so/Plog-b210015b6bfd4a5b946ef4b9261d96e3)
- [github](https://github.com/sokuri-team)
