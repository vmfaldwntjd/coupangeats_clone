# 🍛 Coupangeats Clone Project
*본 프로젝트는 소프트스퀘어드(라이징캠프)에서 주관하였습니다.  
쿠팡이츠 클론앱 프로젝트를 진행함으로써 앱 서비스 개발에 대한 과정을 경험하였습니다.


## 바로가기
[1. 진행기간 및 참여인원](#1-진행기간-및-참여인원)  
[2. 사용 기술](#2-사용-기술)  
[3. ERD 설계](#3-ERD-설계)   
[4. 시스템 아키텍처 소개](#4-시스템-아키텍처-소개)
[5. 핵심 구현 기능 및 결과 영상](#5-핵심-구현-기능-및-결과-영상)    
[6. 회고](#6-회고)

## 1. 진행기간 및 참여인원
2022-05-01 ~ 2022-06-02   
**Server(API)** - [Dona](https://github.com/YeJinHong), [Core](https://github.com/vmfaldwntjd) (2명)   
**Client(Android)** - Nova (1명)

## 2. 사용 기술
- Java 11
- Spring Boot 2.4.2
- Gradle
- MySQL 8
- Lombok

## 3. ERD 설계
![ERD Diagram](https://user-images.githubusercontent.com/33932851/198862318-f803146c-5b69-4b04-b1d2-582668198680.png)

## 4. 시스템 아키텍처 소개
![System Architecture](https://github.com/vmfaldwntjd/coupangeats_clone/assets/75198221/f296b27b-3a1e-42c0-9ea0-ccb0b5ffe88c)

## 5. 핵심 구현 기능 및 결과 영상
배달 메뉴 선택 및 주문
- 유저 주소 등록
- 즐겨찾기 한 레스토랑
- 쿠폰
- 카드 결제 정보
- 리뷰

**최종 결과 영상**   
[최종 결과물](https://youtu.be/Fi17zpX4gw8)

## 6. 회고
### 배운것, 경험한 것
- 도메인 적용 및 AWS 서버 배포 경험
- Rest API 개념 및 작성 가이드 숙지
- Postman - GET, POST 데이터 전송하는 법
- API 명세서 작성법
- ERD 도구(aquery) 사용법
- DB 외부 접속 방법
- 계층 관계를 가지는 DB 구현 경험
- application.yml 설정 파일 이해
    - dev, product 별로 설정 파일을 분리해서 적용할 수 있다는 것을 이해했습니다.
- MySQL의 내장함수 종류 및 사용법