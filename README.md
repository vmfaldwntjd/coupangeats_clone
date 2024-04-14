# 🍛 Coupangeats Clone Project
*본 프로젝트는 소프트스퀘어드(라이징캠프)에서 주관하였습니다.  
쿠팡이츠 클론앱 프로젝트를 진행함으로써 앱 서비스 개발에 대한 과정을 경험하였습니다.


## 바로가기
[1. 진행기간 및 참여인원](#1-진행기간-및-참여인원)  
[2. 사용 기술](#2-사용-기술)  
[3. ERD 설계](#3-ERD-설계)   
[4. 시스템 아키텍처 소개](#4-시스템-아키텍처-소개)
[5. 핵심 구현 기능 및 결과 영상](#5-핵심-구현-기능-및-결과-영상)    
[6. 깨달은 점](#6-깨달은-점)

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
![System Architecture](https://github.com/vmfaldwntjd/coupangeats_clone/assets/75198221/f99cc02e-b91b-4264-92ba-adabde0afabd)

## 5. 핵심 구현 기능 및 결과 영상
배달 메뉴 선택 및 주문
- 유저 주소 등록
- 즐겨찾기 한 레스토랑
- 쿠폰
- 카드 결제 정보
- 리뷰

**최종 결과 영상**   
[최종 결과물](https://youtu.be/Fi17zpX4gw8)

## 6. 깨달은 점
- 도메인 적용 및 AWS 서버 배포
- Rest API 개념 및 작성법
- Get Method, Post Method의 차이점 이해
- 프록시 서버 개념 이해
- Nginx의 장점
- API 명세서 작성법
- ERD 설계 방법
- application.yml 설정 파일 이해
    - local, dev, product 별로 설정 파일을 분리해서 적용 가능
- MySQL의 내장함수 종류 및 사용법