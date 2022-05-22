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

2. 개발 이슈
  1. 환경 구축 이슈
    - dev 서버 구축 확인 중 404 Not Found 출력
      - 프록시 서버 설정 없이 80포트로 접속, 해당 url에 매핑된 결과가 없어 404 error가 출력되었음.
      - application.yml에 설정된 9000번 포트로 접속으로 문제 없음 확인. 이후 프록시 서버 설정으로 80포트 접속도 무사히 확인.
    - dev 서버 구축 확인 중 connect ECONNREFUSED 발생
      - 서버 실행 없이 접속 시도로 인한 접속요청 반려였음. 서버 실행 후 무사 접속되는 것 확인.
    - 프록시 서버 설정 이후 502 Bad Gateway 발생
      - springboot 서버 실행 없이 접속 시도. nginx 서버는 실행중이었기 때문에 오류 메시지를 받을 수 있었다. 서버 실행 후 무사 접속 확인.



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
