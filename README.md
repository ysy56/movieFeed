# 🎞 영화 뉴스피드 프로그램
프로그램 역할 : 영화 리뷰를 보는 프로그램
* 프로젝트 기간 : 2024.06.04 ~ 2024.06.10 (6일)
* 참여자 : 노상윤(👑), 유균한, 홍준빈, 최영주
<br>

## 프로젝트 소개
<details>
<summary> ⁉ 필수 구현 기능 </summary>

<br>
✅ 공통 조건  

* 예외처리는 아래와 같은 형태로 처리하여 `Response` 합니다.

  | Http Status Code | Message |  
  | :--------------: | :-----: |  
  | 400              | 잘못된 요청입니다. |

* Status Code 분류는 [Link](https://hongong.hanbit.co.kr/http-%EC%83%81%ED%83%9C-%EC%BD%94%EB%93%9C-%ED%91%9C-1xx-5xx-%EC%A0%84%EC%B2%B4-%EC%9A%94%EC%95%BD-%EC%A0%95%EB%A6%AC/)를 참고합니다.
* 모든 엔티티에는 `생성일자`와 `수정일자`가 존재합니다.
* 클라이언트는 Postman이고 프론트엔드는 별도 구현하지 않습니다.
<br>

<details>
<summary> ✅ 사용자 인증 기능   </summary>

* 사용자 entity & status
  * 회원
    * bigint : ID
    * varchar : 사용자ID, 비밀번호, 이름, 이메일, 한 줄 소개, 회원상태코드, refresh token
    * timestamp : 상태변경시간, 생성일자, 수정일자
  * 회원상태코드
    * 정상
    * 탈퇴
    * +이메일 인증
<br>

  * 사용자 인증 기능 공통 조건
    * Spring Security와 JWT를 사용하여 설계 및 구현합니다.
    * JWT는 Access Token, Refresh Token을 구현합니다.
    * Access Token 만료 시 : 유효한 Refresh Token을 통해 새로운 Access Token과 Refresh Token을 발급
    * Refresh Token 만료 시 : 재로그인을 통해 새로운 Access Token과 Refresh Token을 발급
    * API를 요청할 때는 Access Token을 사용합니다.
<br>
 
* 회원가입  
  신규 가입자는 `사용자ID`, `비밀번호`를 입력하여 서비스에 가입할 수 있습니다.
  * 사용자ID
    * 중복된 ID, 탈퇴한 ID로는 회원가입 할 수 없습니다.
    * 대소문자 포함 영문 + 숫자만을 허용합니다.
    * 사용자 ID는 최소 10글자 이상, 최대 20글자 이하여야 합니다.
  * 비밀번호
    * `Bcrypt`로 단방향-인코딩합니다.
    * 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함합니다.
    * 비밀번호는 최소 10글자 이상이어야 합니다.
  * ⚠️ 필수 예외처리
    * 중복된 `사용자 ID`로 가입하는 경우
    * `사용자 ID` 비밀번호 형식이 올바르지 않은 경우
* 회원탈퇴  
  회원탈퇴는 가입된 사용자의 **회원 상태**를 변경하여 탈퇴처리 합니다.  
  탈퇴 처리 시 `비밀번호`를 확인한 후 일치할 때 탈퇴처리 합니다.
  * 조건
    * 탈퇴한 사용자 ID는 재사용할 수 없고, 복구할 수 없습니다.
    * 탈퇴처리된 사용자는 **재탈퇴** 처리가 불가합니다.
  * ⚠️ 필수 예외처리
    * `사용자 ID`와 `비밀번호`가 일치하지 않는 경우
    * 이미 탈퇴한 `사용자 ID`인 경우
* 로그인  
  사용자는 자신의 계정으로 서비스에 **로그인**할 수 있습니다.
  * 조건
    * 로그인 시 클라이언트에게 토큰을 발행합니다.
        
        | 토큰 종류 | 만료기간 |
        | --- | --- |
        | Access Token | 30분 |
        | Refresh Token | 2주 |
    
    * 회원가입된 사용자 ID와 비밀번호가 일치하는 사용자만 로그인할 수 있습니다.
    * 로그인 성공 시, **header**에 토큰을 추가하고 성공 상태코드와 메세지를 반환합니다.
    * 탈퇴했거나 로그아웃을 한 경우, `Refresh Token`이 유효하지 않은 상태가 되어야합니다.
  * ⚠️ 필수 예외처리
    * 유효하지 않은 사용자 정보로 로그인을 시도한 경우
        ex. 회원가입을 하지 않거나 회원 탈퇴한 경우
    * `사용자 ID`와 `비밀번호`가 일치하지 않는 사용자 정보로 로그인을 시도한 경우
* 로그아웃  
  사용자는 로그인 되어 있는 본인의 계정을 **로그아웃** 할 수 있습니다.
  * 조건
    * 로그아웃 시, 발행한 토큰은 **초기화** 합니다.
    * 로그아웃 후 초기화 된 `Refresh Token`은 재사용할 수 없고, 재로그인해야 합니다.
<br>

</details>

<details>
<summary> ✅ 프로필 관리 기능 </summary>

* 프로필 조회
  * **사용자 ID, 이름, 한 줄 소개, 이메일**을 볼 수 있습니다.
  * **ID(사용자 ID X), 비밀번호, 생성일자, 수정일자**와 같은 데이터는 노출하지 않습니다.
* 프로필 수정
  로그인한 사용자는 본인의 사용자 정보를 수정할 수 있습니다.
  * 수정 가능한 사용자 정보 : 이름, 이메일(이메일 인증 기능 구현으로 제외), 한 줄 소개, 비밀번호
  * 비밀번호 수정 조건
    * 비밀번호 수정 시, 본인 확인을 위해 현재 비밀번호를 입력하여 올바른 경우에만 수정할 수 있습니다.
    * 현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다.
  * ⚠️ 필수 예외처리
    * 비밀번호 수정 시, 본인 확인을 위해 입력한 현재 비밀번호가 일치하지 않은 경우
    * 비밀번호 형식이 올바르지 않은 경우
    * 현재 비밀번호와 동일한 비밀번호로 수정하는 경우
<br>

</details>

<details>
<summary> ✅ 뉴스피드 게시물 CRUD 기능 </summary>

* 뉴스피드 entity
  * bigint : ID, 작성자 ID
  * longText : 내용
  * timestamp : 생성일자, 수정일자
<br>

* 게시물 작성, 조회, 수정, 삭제  
  게시물 조회는 모든 사용자가 조회할 수 있습니다.
  * 조건
    * 게시물 작성, 수정, 삭제는 **인가(Authorization)**가 필요합니다.
    * 유효한 JWT 토큰을 가진 작성자 본인만 처리할 수 있습니다.
  * ⚠️ 필수 예외처리
    * 작성자가 아닌 다른 사용자가 게시물 작성, 수정, 삭제를 시도하는 경우
* **뉴스피드 조회 기능**  
  모든 사용자가 전체 뉴스피드 데이터를 조회할 수 있습니다.
  * 조건
    * 모든 사용자는 전체 뉴스피드를 조회할 수 있습니다.
    * 기본 정렬은 **생성일자 기준으로 최신순**으로 정렬합니다.
    * 뉴스피드가 없는 경우, 아래와 같이 반환합니다.

</details>
<br>

</details>

<details>
<summary> ⭐ 추가 구현 기능(일부) </summary>

* ❌ 뉴스피드 추가 구현
  * 페이지 네이션
    * 10개씩 페이지네이션하여, 각 페이지 당 뉴스피드 데이터가 10개씩 나오게 합니다.
  * 정렬 기능
    * 생성일자 기준 최신순
    * 좋아요 많은 순
  * 기간별 검색 기능
    * 예) 2024.05.01 ~ 2024.05.27 동안 작성된 뉴스피드 게시물 검색
* ✅ 댓글 CRUD 기능
  * 댓글 entity
    * bigint : ID, 뉴스피드ID, 작성자ID, 좋아요 수
    * varchar : 내용
    * timestamp : 생성일자, 수정일자
  * **댓글 작성, 조회, 수정, 삭제 기능**
    * 사용자는 게시물에 댓글을 작성할 수 있고, 본인의 댓글은 **수정 및 삭제**를 할 수 있습니다.
    * **내용**만 수정이 가능합니다.
    * 댓글 작성, 수정, 삭제는 **인가(Authorization)**가 필요합니다.
    * 유효한 JWT 토큰을 가진 작성자 본인만 처리할 수 있습니다.
      * 예) 본인이 작성한 댓글 외엔 수정 및 삭제 불가
* ✅ 이메일 가입 및 인증 기능
  * 이메일 가입 시, **이메일 인증 기능**을 추가
    * Step 1 : 사용자가 가입한 이메일 주소로 인증번호 발송
    * Step 2 : 발송한 인증번호와 입력란의 인증번호가 일치하는 지 확인
    * Step 3 : 이메일 인증이 완료되지 않은 회원들의 `회원상태코드`를 ‘인증 전’ 으로 설정
* ✅ 좋아요 기능
  * 좋아요 entity
    * bigint : ID, 사용자ID, 콘텐츠ID
    * varchar : 콘텐츠 유형(댓글, 게시물)
    * timestamp : 생성일자, 수정일자
  * 게시물 및 댓글 좋아요/ 좋아요 취소 기능
    * 사용자가 게시물이나 댓글에 좋아요를 남기거나 취소할 수 있습니다.
    * 본인이 작성한 게시물과 댓글에 좋아요를 남길 수 없습니다.
    * 같은 게시물에는 사용자당 한 번만 좋아요가 가능합니다.
* ❌ Swagger 적용
  * 라이브러리 적용 후 Swagger에서 제공되는 기능들은 사용하지 않습니다.
  * localhost:8080/swagger-ui/index.html  주소로 접근시 접속이 가능해야 합니다.
<br>

</details>

<details>
<summary> 🏆 명예의 전당 기능(일부) </summary>

* ✅ 이메일 가입 및 인증 추가 구현
  * 이메일 가입 시 이메일 인증 기능을 포함하는 것이 좋습니다.
    * 인증번호 입력을 180초 안에 하지 않으면 유효하지 않음.
    * 회원 테이블에 인증 메일이 발송된 시간 컬럼을 추가하여 제한시간을 넘는 지 확인.
* ❌소셜 로그인 기능 구현
  * 소셜 로그인에 필요한 **테이블(entity)**을 설계해서 **ERD**에 추가합니다.
  * [네이버 로그인 개발가이드](https://developers.naver.com/docs/login/devguide/devguide.md)를 참고하여 네이버 로그인을 구현해보세요.
  * [카카오 로그인 개발가이드](https://developers.kakao.com/docs/latest/ko/kakaologin/common)를 참고하여 카카오 로그인을 구현해보세요.
* ❌ 프로필에 사진 업로드 기능 구현
  * 프로필 사진을 저장할 때는 반드시 **AWS S3**를 이용합니다.
* ❌ 게시물에 멀티미디어 지원 기능 구현
  * 사진 업로드 기능과 동일하게 **AWS S3**를 이용합니다.
  * 게시물 본문에 사진이나 영상 등의 미디어를 포함하는 기능을 추가합니다.
    * 적절한 용량과 특정 파일 형식만을 업로드할 수 있도록 구현합니다.
      * 여러 장 가능합니다.
      * 한 게시물에 대해 최대 5개 제한
      * `JPG`, `PNG`, `JPEG` → 최대 10MB
      * `MP4`, `AVI`, `GIF` → 최대 200MB
  * 게시물 수정시에 첨부된 미디어를 수정할 수 있습니다.
  * 게시물 삭제시에 첨부된 미디어도 함께 삭제합니다.
  * 댓글에는 추가하지 않습니다.
* ❌ 팔로우 기능 구현
  * 특정 사용자를 팔로우 / 언팔로우를 할 수 있습니다.
  * 팔로우 기능이 구현되었다면, 뉴스피드에 팔로우하는 사용자의 게시물을 볼 수 있습니다.
  * 팔로우를 하고 있는 사람들이 작성한 게시물을 볼 때 정렬 기준은 최신순입니다.
* ❌ HTTP를 HTTPS로 업그레이드 하기
  * HTTPS를 적용하여 보안이 강화된 웹 페이지를 제공해봅시다.
  
</details>

<br>

## 👩‍💻👨‍💻 팀원 구성
| 노상윤 | 유균한 | 홍준빈 | 최영주 |
|:---:|:---:|:---:|:---:|
| <img src="https://ca.slack-edge.com/T06B9PCLY1E-U06S0N8HRJ8-6a09948d54c8-512" height="220"/> | <img src="https://ca.slack-edge.com/T06B9PCLY1E-U06RE49BU12-b4cbb22f8fe5-512" height="220"/> | <img src="https://ca.slack-edge.com/T06B9PCLY1E-U06SF9P0MM3-2cadc303ee44-512" height="220"/> | <img src="https://ca.slack-edge.com/T06B9PCLY1E-U06KADG3X1P-7d806be1d793-512" height="220"/> |
| [@rohtable](https://github.com/rohtable) | [@ryurbsgks5114](https://github.com/ryurbsgks5114) | [@Hongjunbin](https://github.com/Hongjunbin) | [@ysy56](https://github.com/ysy56) | 
| [코드 굽는 오븐](https://makeroh.tistory.com/) | [Github TIL](https://github.com/ryurbsgks5114/TIL) | [sangnamja](https://bin2dev.tistory.com/) | [컴공생의 발자취](https://moonnight0.tistory.com/) |

<br>


## 🤝 역할 분담
* 노상윤 : 댓글 추가/ 수정 / 삭제, 댓글 전체 조회
* 유균한 : 회원가입, 회원탈퇴, 로그인, 로그아웃( 인증 / 인가 )
* 홍준빈 : 게시글 작성 / 조회 / 수정 / 삭제, 게시글 전체 조회, 좋아요 등록 / 취소
* 최영주 : 마이페이지 조회 / 수정, 비밀번호 수정, 이메일 가입 및 인증 기능
<br>

## ☁ ERD 다이어그램
![ERD](https://github.com/ysy56/movieFeed/assets/78634780/f64018a9-cf5e-49cf-ae76-47ec74c18e70)
<br>

## 📑 API 명세서
[🌈Figma Link: API 명세서 + ERD 설계](https://www.figma.com/board/tSMVD3wg5zSrPHbTzjvtWW/13%EC%9D%BC%EC%9D%98-%EA%B8%88%EC%9A%94%EC%9D%BC?node-id=0-1&t=Bl9s2WWjCFgGyOtZ-0)

![1](https://github.com/ysy56/movieFeed/assets/78634780/7571d7cd-95e3-4848-9a97-32217c0782cf)
![2](https://github.com/ysy56/movieFeed/assets/78634780/f4e674a3-ed99-420d-84d1-969b8086477a)
![3](https://github.com/ysy56/movieFeed/assets/78634780/da56a035-68f5-48d4-bf96-cf05c649e7d1)
![4](https://github.com/ysy56/movieFeed/assets/78634780/8776c481-f74a-40a5-8a12-75c74a559e59)
![5](https://github.com/ysy56/movieFeed/assets/78634780/8a843377-ef4a-455d-ad92-6710df614de8)
![6](https://github.com/ysy56/movieFeed/assets/78634780/b3b4d48c-410d-4e87-a67b-bc49096b83e0)

## 🏗 프로젝트 구조
```
├── .github/ISSUE_TEMPLATE
│    └── -task--이슈카드-제목-설정하기.md
├── gradle/wrapper 
│    ├── gradle-wrapper.jar
│    └── gradle-wrapper.properties
|    ├── main
|    |    ├── java/com/sprata/moviefeed
|    |    |    ├── config
|    |    |    |    ├── BcryptConfig.java
|    |    |    |    ├── EmailConfig.java
|    |    |    |    ├── JwtConfig.java
|    |    |    |    └── WebSecurityConfig.java
|    |    |    ├── controller
|    |    |    |    ├── BoardController.java
|    |    |    |    ├── CommentController.java
|    |    |    |    ├── EmailController.java
|    |    |    |    ├── MypageController.java
|    |    |    |    └── UserController.java
|    |    |    ├── dto
|    |    |    |    ├── requestdto
|    |    |    |    |    ├── BoardRequestDto.java
|    |    |    |    |    ├── CommentRequestDto.java
|    |    |    |    |    ├── EmailCheckRequestDto.java
|    |    |    |    |    ├── MypageRequestDto.java
|    |    |    |    |    ├── PasswordRequestDto.java
|    |    |    |    |    ├── UserLoginRequestDto.java
|    |    |    |    |    ├── UserSignupRequestDto.java
|    |    |    |    |    └── UserWithdrawalRequestDto.java
|    |    |    |    ├── responsedto
|    |    |    |    |    ├── BoardResponseDto.java
|    |    |    |    |    ├── CommentResponseDto.java
|    |    |    |    |    ├── CommonResponseDto.java
|    |    |    |    └──  └── MypageResponseDto.java
|    |    |    ├── entity
|    |    |    |    ├── Board.java
|    |    |    |    ├── Comment.java
|    |    |    |    ├── Like.java
|    |    |    |    ├── Timestamped.java
|    |    |    |    └── User.java
|    |    |    ├── enumeration
|    |    |    |    ├── LikeType.java
|    |    |    |    └── UserStatus.java
|    |    |    ├── exception
|    |    |    |    ├── BadRequestException.java
|    |    |    |    ├── ConfictException.java
|    |    |    |    ├── DataNotFoundException.java
|    |    |    |    ├── ForbiddenException.java
|    |    |    |    ├── GlobalExceptionHandler.java
|    |    |    |    ├── TokenExpiredException.java
|    |    |    |    ├── UnauthorizedException.java
|    |    |    |    └── ViolatedLikeException.java
|    |    |    ├── filter
|    |    |    |    ├── JwtAuthenticationFilter.java
|    |    |    |    └── JwtAuthorizationFilter.java
|    |    |    ├── repository
|    |    |    |    ├── BoardRepository.java
|    |    |    |    ├── CommentRepository.java
|    |    |    |    ├── LikeRepository.java
|    |    |    |    └── UserRepository.java
|    |    |    ├── security
|    |    |    |    ├── CustomAuthenticationEntryPoint.java
|    |    |    |    ├── UserDetailsImpl.java
|    |    |    |    └── UserDetailsServiceImpl.java
|    |    |    ├── service
|    |    |    |    ├── BoardService.java
|    |    |    |    ├── CommentService.java
|    |    |    |    ├── EmailService.java
|    |    |    |    ├── MypageService.java
|    |    |    |    ├── RedisUtil.java
|    |    |    |    └── UserService.java
|    |    |    ├── util
|    |    |    |    └── JwtUtil.java
|    |    └──  └── MovieFeedApplication.java
|    ├── resources
|    |    └── application.properties
|    ├── test/java/com/sprata/moviefeed
|    └──  └── MovieFeedApplicationTests.java
├── .gitignore
├── build.gradle
├── gradlew
├── gradlew.bat
└── settings.gradle
```
<br>

## 🙄 신경쓴 부분
* 개인 역량 최대한 발휘하기
* 팀원 간의 원활한 소통과 협업을 통해 서로 배우고 성장하기
* Git Commit & Code Convention 논의 후 잘 지키기
* Issue 사용하기

<br>

## 😫 트러블 슈팅
<details>
<summary> git push 및 .env 최신화 문제 </summary>

* 문제 상황  
  * 의도했던 기능 : 각 팀원의 기능 구현으로 PR 후 git pull 했을 때 정상 작동하는 것
  * 발생한 현상(트러블) : 프로그램을 동작시켰을 때 정상적으로 빌드되지 않고 error 발생

* 트러블 원인 추론  
1. email 관련 라이브러리가 없다는 error 발생
   
```
Cannot resolve symbol 'JavaMailSender'
Cannot resolve 'JavaMailSenderImpl'
```   
   ![git push error](https://github.com/ysy56/movieFeed/assets/78634780/4d11c5fb-0540-49ba-970d-d7be077ec2e9)

2. .env의 jwt 관련 변수 설정이 없어서 error 발생
```
org.springframework.context.ApplicationContextException: Unable to start web server
Caused by: org.springframework.boot.web.server.WebServerException: Unable to start embedded Tomcat
Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'webSecurityConfig' defined in file [C:\Users\ysy56\IdeaProjects\movieFeed\build\classes\java\main\com\sparta\moviefeed\config\WebSecurityConfig.class]: Unsatisfied dependency expressed through constructor parameter 0: Error creating bean with name 'jwtUtil': Injection of autowired dependencies failed
Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'jwtUtil': Injection of autowired dependencies failed
Caused by: java.lang.IllegalArgumentException: Could not resolve placeholder 'ACCESS_TOKEN_TIME' in value "${ACCESS_TOKEN_TIME}"
```
   ![env error](https://github.com/ysy56/movieFeed/assets/78634780/7e5bcb96-e9d3-4f10-9b07-763cc977e949)


* 해결방법  
1. 처음엔 다른 클래스의 라이브러리를 import하지 못하는 문제인 줄 알고 블로그를 따라 해보았다. [다른 클래스 라이브러리 import 문제 해결](https://zzang9ha.tistory.com/352#google_vignette)
하지만, 문제는 해결 되지 않았고 라이브러리 문제이므로 build.gradle을 살펴보았다. 그 결과 build.gradle이 commit에 포함되지 않고 push를 했다는 사실을 발견했다. 결국 build.gradle에 없는 email 라이브러리를 추가하여 문제를 해결했다.
2. email 라이브러리를 추가했음에도 문제가 발생했다. 팀원분이 .env 파일을 작성하는 법을 잘 모르셔서 name.env 이런 식으로 env를 작성하셨고 이 과정에서 .env 적용이 되지 않아 properties에 환경변수를 넣어서 사용하고 계셨다. 이게 문제인가 싶어서 .env 파일이 사용되도록 고쳤음에도 문제는 계속해 발생했다. 그러던 와중에 error의 내용을 잘 읽어보며 jwt관련해서 문제가 있다는 걸 발견한 후 인증/인가를 맡으셨던 팀원분이 .env에 추가된 환경변수가 있다는 걸 알려주지 않으셔서 발생했던 문제였다. 결국 jwt 관련 환경변수를 추가하여 문제를 해결했다.

</details>

<details>
<summary> security 주석 문제 </summary>

* 문제 상황  
  * 의도했던 기능 : 게시글, 마이페이지 등의 정상적인 CRUD 기능 동작을 통한 200번대 HTTP 상태코드 반환
  * 발생한 현상(트러블) : 게시글, 마이페이지 등에서 CRUD 기능을 시도했을 때 401 인가 관련 error가 발생
  
* 트러블 원인 추론  
spring security을 사용하여 작성한 코드가 없음에도 라이브러리를 사용하고 있던 것
![401](https://github.com/ysy56/movieFeed/assets/78634780/c401b983-7976-4af3-9534-794c9ebbac15)

* 해결방법  
build.gradle에서 spring security를 주석 처리하고 재빌드하여 문제를 해결했다.
 
</details>

<details>
<summary> UserStatus 문제 </summary>

* 문제 상황  
  * 의도했던 기능 : 이메일 인증 시, 인증이 완료된 후 회원상태 코드를 `이메일 인증`으로 변경
  * 발생한 현상(트러블) : 이메일 인증 시, header에 jwt를 넣었음에도 불구하고 '로그인 후 이용해주세요'라는 문구 표시

* 트러블 원인 추론
user_status에 `이메일 인증`을 추가하여 이메일 인증 후 회원상태 코드 변경 시 다음과 같은 문제가 발생
```
java.sql.SQLException: Data truncated for column 'user_status' at row 1
```

* 해결방법
처음 에러 문구를 확인 후 user_status의 코드가 문제가 있는지 확인해봤지만 문제는 없었다. 그래서 DB의 문제일 경우 보통 코드를 변경하게 되면 DB의 입력 제한과 다르기 때문에 문제가 발생했었다. 이를 기반으로 하여 DB의 테이블을 전부 지운 후 다시 회원가입하여 실행해서 문제를 해결했다.

</details>

<details>
<summary> return 문제 </summary>

* 문제 상황  
  * 의도했던 기능 : 내용 작성  
  * 발생한 현상(트러블) : 내용 작성  

* 트러블 원인 추론  
// 내용 작성  

* 해결방법  
// 내용 작성  
</details>

<br>

## 📮 프로젝트 후기

#### 🧡 노상윤

#### 💙 유균한

#### 🖤 홍준빈

#### 💚 최영주
