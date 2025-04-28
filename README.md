# 🎟️ booking-service

OO-SMS 프로젝트의 공연 예매(Booking) 도메인을 담당하는 서비스입니다.  
고객이 공연을 예약하는 기능을 제공하며, 예약 정보는 문자 발송(sms-service)과 연동되어 활용됩니다.

---

## 🛠 기술 스택

- Java 17
- Spring Boot 3.3
- Spring Data JPA
- H2 Database
- Spring Cloud (Eureka Client, Config Client 적용예정)
- Docker
- Swagger (API 문서 자동 생성)

---

## 🧩 주요 기능

- 공연 예매 등록 API
- 예매 정보 조회 API
- 고객 ID로 예매 리스트 조회 API
- 내부 서비스(view-service 등)와 연동을 위한 예약 데이터 제공
- RESTful 설계 원칙을 준수한 API 작성

---

## 🛢️ 예매 도메인 DB 모델링
예매 서비스는 MSA 구조 내 하나의 통합 데이터베이스를 사용하며,  
다른 도메인과는 약결합(Loose Coupling)된 형태로 서비스 단위만 분리하여 설계되었습니다.

### 📋 ERD

![booking-service-erd](./docs/images/booking-service-erd.png) <!-- ← ERD 이미지 저장 경로 -->



