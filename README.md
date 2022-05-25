#coupangeats_server_core_dona

# 개발일지

## Dona 개발 일지
### 2022-05-21
1. 기획서 변동 사항
  - 개인 스케줄에 의해 환경 구축 22일 11시로 마감일 수정
2. ERD 설계

### 2022-05-22

#### 위클리 스크럼 1차 진행
진행 상황 및 다음 목표 공유
- 목표
  - 도나 : 회원가입/로그인 API 구현 및 ERD 설계 완료
  - 노바 : 홈 Fragment UI 완성 및 회원가입/로그인 API 연동
  - 코어 :  dev/prod서버 구축 완료

1. 기획서 변동 사항
  - 회원가입 스크린 샷 추가

2. ERD 설계 (약 50% 구축)
  - 주문 기능 관련 테이블 추가
  - user 관련 테이블 추가 및 수정(필드 추가, 이용약관 테이블, 유저 주소 테이블 등 추가)
  - 이미지 테이블 추가

3. 환경 구축 완료 80%
  - EC2, RDS 생성 및 서브도메인 생성, 기존 메인 도메인에 springboot 템플릿 적용.

4. 개발 이슈
  1. 환경 구축 중 mysql_secure_installation의 MySQL Failed! Error: SET PASSWORD has no significance for user ‘root’@’localhost’ as the authentication method used doesn’t store authentication data in the MySQL server. 에러 발생
      - 원인은 mysql 정책 변경에 의한 기본 password 설정으로 추정 중.
      - 새로운 putty session으로 mysql_secure_installation을 kill, 이후 sudo로 mysql 접속하여 root 계정의 password를 변경, 설치를 다시 할 수 있었음.


### 2022-05-23
1. dev/prod 환경 구축 완료
    - dev : 9000포트 사용. 같은 RDS의 donaDB 스키마 사용.
    - prod : 3000포트 사용. 같은 RDS의 coupangeats 스키마 사용

2. 기획서 변동사항
    - 메인 화면 구성 협의
    - 서버 개발 역할 분담 변경(개발 환경 및 user API 구축 담당 도나로 변경)

3. ERD 설계 (90%)
    - aquerytool 최대 테이블 수 제한으로 인해 일부 기능 제약. 우선순위에 따라 기능 제외 예정.
    - review, review_image, account 테이블 추가 및 기존 테이블 구성 추가

4. API 구현
    - 회원가입 API, 이메일 유저 조회 API, 핸드폰 유저 조회 API 구현
    - 유저 조회의 경우 하나의 Response 객체를 공유하고 있어 필요하지 않은 정보는 null 반환 -> 타당성 재고 필요

5. 개발 이슈
  #### 5.1. 환경 구축 이슈
    - dev 서버 구축 확인 중 404 Not Found 출력
      > 프록시 서버 설정 없이 80포트로 접속, 해당 url에 매핑된 결과가 없어 404 error가 출력되었음.
      > application.yml에 설정된 9000번 포트로 접속으로 문제 없음 확인. 이후 프록시 서버 설정으로 80포트 접속도 무사히 확인.
    - dev 서버 구축 확인 중 connect ECONNREFUSED 발생
      > 서버 실행 없이 접속 시도로 인한 접속요청 반려였음. 서버 실행 후 무사 접속되는 것 확인.
    - 프록시 서버 설정 이후 502 Bad Gateway 발생
      > springboot 서버 실행 없이 접속 시도. nginx 서버는 실행중이었기 때문에 오류 메시지를 받을 수 있었다. 서버 실행 후 무사 접속 확인.
  #### 5.2. spring boot 빌드 이슈
    - Could not create connection to database server 에러
    - 데이터 베이스 접속이 불가능하다는 오류로, db driver 버전 문제, 접속 username, password 문제 등 원인이 다양하다.
    - 관련 정보를 모두 확인했으나 실행이 되지 않아 어려움을 느꼈으며 콘솔의 에러 메시지에서 timezone에 대한 정보를 확인 후 datasource url의 마지막에 &serverTimezone=UTC  추가하여 해결함. mysql connector 8.0부터 기본 타임존 설정이 지정되지 않아 생긴 문제라는 듯.
  #### 5.3 API 서버 구현중 UserService의 @Transactional 이슈
    - 사용자 정보를 올바르게 삽입하고 나서 jwt를 발급, 저장해야한다고 생각하기때문에 createUser의 경우 userDao의 메소드가 두가지가 실행됨.
    - 따라서 createUser 메소드의 상단에 @Transactional을 기입해줬으나 유저 정보 기입단계에서 refresh_token NOT NULL 설정에 의한 DB 에러가 반환, 유저정보는 들어갔지만 결과 출력 창이 오류메시지를 보이는 이슈가 발생했다.
    - @Transactional 어노테이션에 대한 이해도 부족으로 해결되진 않았으며 관련된 원인인 refresh_token을 nullable로 설정.


### 2022-05-24
1. API 명세서 핵심기능 위주의 26개 API list-up 완료.
2. ERD 수정사항
  - advertisement 테이블명 event테이블로 변경
  - event 테이블 이벤트 시작/종료 날짜 추가
  - user_address 테이블 선택 주소 여부 필드 추가
  - restaurant 치타 배달 여부, 블루 리본 여부 필드 is_selected 추가
  - 자주 묻는 질문 관련 테이블  frequently_asked_questions,   main_inquiry, order_receipt 테이블 전부 삭제.
    -  static 정보는 html으로 출력한다.
  - cart, cart_menu 테이블 추가
    - cart_menu 테이블은 계층형 테이블 조회시 셀프조인이 필요함
    - cart는 결제를 넘기기 전 임시 데이터 저장공간같은 개념이라 nullable의 필드가 많다.
  - res_category 테이블
    - 카테고리별 설명 description 필드 추가
  - 광고 테이블 필드명 수정
  - PK 설정 수정(1차 피드백 반영, 모든 테이블에 id 필드 설정)
  - 가게 관련 테이블 접두사 res로 통일 (사유 : 통일성과 철자상 오타발생 방지)
  - 가게 내 메뉴 카테고리 테이블 명 res_category에서 res_kinds로 변경 (사유 : 가게가 속한 카테고리 res_category와 이름 중복)

3. API 구현 상황
  - 로그인 API 개발 완료 및 2,3번 API response 수정 완료.
  - 개발 환경에 따른 설정 분리를 위한 applcation-local.yml, application-dev.yml, application-prod.yml 생성.
  - git 업로드 완료 및 prod 서버 반영 완료.

4. 개발 이슈
 - 깃허브 master branch 생성, There isn’t anything to compare 으로 인한 pull request 불가 오류
    - push 전 세번의 commit으로, 비교하는 것은 마지막 commit의 readme 삭제 사항 뿐으로 main에 master branch를 병합할 수 없었습니다.
    -  There isn’t anything to compare 를 키워드로 검색한 블로그 https://jeongkyun-it.tistory.com/128 의 내용에 따라 git 명령어를 수행, main에 프로젝트 내용을 push 하는 것은 가능하였으나 기존에 존재하던 Readme가 삭제되는 이슈가 발생했습니다.
 - 깃허브 Readme 삭제 이슈
    - 상위에 기재된 이슈에 따라 기존에 작성한 개발일지 Readme와 그 commit 기록이 삭제되었습니다.
    - git push 취소 방법을 알아보았으나 로컬에 저장된 기록의 마지막 결과를 강제로 가져오는 방법뿐으로 이전의 readme는 복구할 수 없었습니다.
    - 미리 복사해둔 readme 텍스트 파일을 붙여넣는 것으로 임시 해결하였습니다.

### 2022-05-25
1. 개발 이슈
- 인텔리제이 깃허브 연동 오류 Incorrect credentials. Insufficient scopes granted to token 이슈 발생.
  - 깃 push를 위해 token으로 로그인하려 했으나 반려됨. Jetbrain에서 리다이렉트하는 Log in via github는 인증 화면으로 넘어가지 않는 오류가 발생.
  - 생성한 토큰의 scope를 업데이트 해주는 것으로 해결.
- push rejected 이슈 발생 Repository not found
  -  깃의 readme.md가 업데이트되었으나 이를 pull 하지 않은 채로 push를 시도해서 일어난 오류. update를 시도하였으나 Can't update main has no tracked branch 발생.
  -  정확한 원인은 아직도 알 수 없었으나 인텔리제이가 당시 인식하는 local branch와 remote branch가 어딘가 잘못되어 있었던 것으로 추정된다.
  -  인텔리제이 하단의 git 단락에서 이것저것 클릭하다가 얼떨결에 해결됨(..). 존재하는 올바른 main branch에서 update를 받은것으로 추정된다.
- 인텔리제이 실행 안됨
  - 프로젝트 로딩 도중 cancel 클릭으로 비정상적 종료됨. 작업관리자의 백그라운드 프로세스를 확인, 종료함으로 해결.
- .gitignore 미설정 오류
  - 초기 프로젝트 업로드시 .gitignore을 txt 파일로 생성하는 실수로 .gitignore이 반영되지 않는 오류가 발생.
  - git은 한번 추적을 시작한 파일은 계속해서 추적하기때문에 다음의 두가지 방법이 가능하다.
    - git 추적 중단 명령
    > git update-index --assume-unchanged {filename}   
    - 삭제 & 복구를 통한 .gitignore 설정
    > rm {filename}   
    git add . && git commit -m "delete file" // 이후 파일 복구
  - 따라서 reame.md와 .git 폴더를 제외한 모든 프로젝트 파일을 옮겨서 commit, 복구 후 commit을 실행, class, jar 등 gitignore에 등록된 파일이 제외되었다.
- prod 서버 build 시도 중 could not be found or load main class org.gradle.wrapper.GradleWrapperMain 에러 발생
  - .gitignore에 의해 gradle 실행 파일(.jar)가 제외된 것으로 판단. 간단한 검색으로 gradle wrapper를 실행하면 된다는 글을 찾음.
  - apt 명령어를 통해 gradle 설치. 4.4.1 버전 설치. 로컬 환경은 6번대로 역시 빌드시 "Spring boot plugin requires Gradle 5 ~"의 상위 버전을 요구하는 에러 메시지와 함께 build faild.
  - 상위 6번 버전 재설치후 build wrap으로 해결.
- prod 서버 build 시도 중 Permission denied 발생.
  - 실행 명령어에 대한 권한 부여 chmod +x ./gradlew를 해결책으로 찾았지만 이 역시 Operation not permitted 에러 발생
  - 프로젝트 폴더 전체에 대한 권한 설정 sudo chmod -R 777 coupangeats-server-core-dona 실행으로 해결.

* * *

## Core 개발 일지
### 2022-05-21
1. 기획 작성 -> 원래는 서버 구축도 완료할 예정이었으나 ERD 작성을 마무리하고 서버 구축 및 rds,
  도메인 생성 완료 예정
2. ERD 설계 진행 30%

### 2022-05-22
1. erd 테이블 추가(res_info 테이블, user_address 테이블, res_operating_time테이블)
2. ec2 인스턴스 생성 100%
3. rds 구축 95% → vpc관련 설정 따로 해야할 듯
4. 스프링 부트에 ssl적용 완료
5. 위클리 스크럼 진행
 -> 진행 목표 설정
- 도나 : 회원가입/로그인 API 구현 및 ERD 설계 완료
- 노바 : 홈 Fragment UI 완성 및 회원가입/로그인 API 연동
- 코어 :  dev/prod서버 구축 완료
6. 이슈: 스프링템플릿 폴더에 대한 git rository ssh인증 clone 이슈 해결 (mac 환경)(22.05.22)
로컬에서 변경한 스프링템플릿의 내용을 git repository에 올리고 그 변경된 내용을 서버 쪽으로 끌어오기 위해서 git pull명령어를 사용해야 합니다. 그 전에 git clone으로 git repository에 있는 내용들을 먼저 가져와야 하는데 mac에서는 ssh인증 방식을 사용합니다. 그래서 ssh-keygen 명령을 통해서 ssh key를 생성하고 github에 저장까지 완료한 뒤 ssh에 관한 git clone을 사용했는데 여전히 권한이 거부되었다는 이슈가 발생하였습니다. (딱히 해당 레퍼지토리에 대한 설정 변경도 없었습니다.)
 구글링을 해본 결과 이것은 github 문제인 것으로 생각되어 다시 스프링템플릿에 대한 git repository를 다시 만들고 ssh clone을 성공하였습니다.

### 2022-05-23
1. erd 테이블에 대한 설명 영상 제작
2. 이슈: dev/prod의 서브 도메인을 스프링 부트에 적용하려는 순간 nginx를 재시작 하는 명령어가
실행이 안 된다. -> 구글링을 해도 답이 나오지 않자 spring 지식-in에 질문을 게시한 상태.
3. 피드백을 받으면서 일단 작업을 로컬에서 작동하기로 하였다.
4. rds를 통해서 datagrip을 통한 쿼리 작성 시작.

### 2022-05-24
1. 가비아에서 만든 도메인 사이트 및 prod 서브 도메인에 https 적용 완료
2. rds에서 db파라미터 그룹 추가하고 시간 등 각종 설정 완료
3. datagrip으로 erd 설계에 따른 모든 테이블 생성완료
4. 쿼리 작성을 위해서 기획에 작성된 각 화면에서 필요한 테이블 column이 무엇인지 분석

이슈발생
1. 이슈: aquery erd 사이트에서 만든 테이블들을 datagrip으로 import 하려는 데 오류 발생
    → order 테이블 쿼리문 오류가 생겼다.(SQL syntax error)
    해결: 확인해본 결과 Mysql에 order라는 예약어가 존재해서 이름이 겹친 것이었다. order의 이름을 \``order`\`로 변경해서 해결

2. 이슈: datagrip에서 테이블을 import하는 중 다음 에러 문구를 접하였다.: Incorrect table definition; there can be only one auto column and it must be defined as a key
    해결: ERD테이블에서 AI(Auto Increment)로 지정된 키는 반드시 PK로 지정이 되야한다고 한다. res_category_id에 pk를 추가

### 2022-05-25
1. API 명세서 17개 리스트업 진행
-> 고객 지원, 자주 묻는 질문 등의 API 리스트도 새로 만들고 싶었으나 aquery tool에서 만들 수 있는
ERD의 개수 제한으로 인해서 일단 여기까지만 제작
2. erd 에서 res_delivery_fee 테이블에서 restaurant_id가 fk처라기 되어 있지 않아 fk처리
3. 기획서의 있는 각 화면 당 erd에서의 필요한 칼럼(column)을 명세서를 참고하여 spread sheet
에 작성 완료 -> 추후에 추가 필요
