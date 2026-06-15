# 🏥 Hospital Reservation System

Spring Boot + MyBatis + MySQL + AI 기반 병원 예약 관리 시스템

진료과 · 환자 · 의사 · 예약 · 진료기록 5개 도메인의 RESTful API를 제공합니다.

> 🌐 **[한국어](./README.md)** | **[English](./README_en.md)**

---

## 📌 프로젝트 개요

병원 예약 업무를 모델링한 백엔드 시스템입니다. 환자가 의사에게 예약을 잡고, 진료 후 진료기록이 남는 실제 병원 워크플로우를 데이터베이스 설계와 REST API로 구현했습니다.

| 항목 | 내용 |
|------|------|
| 개발 기간 | 2026.06 ~ (진행 중) |
| 개발 인원 | 1명 (개인 프로젝트) |
| 핵심 목표 | FK 관계 기반 정규화 설계 + 계층형 아키텍처 REST API |

---

## 🛠 기술 스택

**Backend**
- Java 21
- Spring Boot 3.5
- MyBatis 3.0
- Spring Web (REST API)

**Database**
- MySQL / MariaDB
- 5개 테이블 정규화 설계 (FK 관계)

**Tools**
- Maven
- Postman (API 테스트)
- Git / GitHub
- Eclipse

---

## 🗄 데이터베이스 설계 (ERD)

![ERD](./erd.png)

```
department (진료과)
    │
    ├──< doctor (의사)
    │
patient (환자)
    │
    └──< appointment (예약) >──┐
              │                 │
              └──< medical_record (진료기록)
```

**테이블 관계**
- `doctor` → `department` (의사는 하나의 진료과 소속)
- `appointment` → `patient`, `doctor` (예약은 환자-의사 연결)
- `medical_record` → `appointment`, `patient`, `doctor` (진료기록)

**설계 포인트**
- 모든 테이블에 `AUTO_INCREMENT` PK 적용
- `created_at`에 `DEFAULT NOW()`로 생성 시각 자동 기록
- `member_id`에 `UNIQUE` 제약으로 중복 가입 방지
- 진료기록은 예약과 독립적으로 보존 (법적 보관 요건 고려한 비정규화)

---

## 🔗 API 명세

### Department (진료과)
| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/api/departments` | 전체 조회 |
| GET | `/api/departments/{no}` | 단건 조회 |
| POST | `/api/departments` | 등록 |
| PUT | `/api/departments/{no}` | 수정 |
| DELETE | `/api/departments/{no}` | 삭제 |

### Patient (환자)
| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/api/patients` | 전체 조회 |
| GET | `/api/patients/{no}` | 단건 조회 |
| POST | `/api/patients` | 등록 |
| PUT | `/api/patients/{no}` | 수정 |
| DELETE | `/api/patients/{no}` | 삭제 |

### Doctor (의사)
| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/api/doctors` | 전체 조회 |
| GET | `/api/doctors/{no}` | 단건 조회 |
| GET | `/api/doctors/department/{departmentNo}` | 진료과별 조회 |
| POST | `/api/doctors` | 등록 |
| PUT | `/api/doctors/{no}` | 수정 |
| DELETE | `/api/doctors/{no}` | 삭제 |

### Appointment (예약)
| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/api/appointments` | 전체 조회 |
| GET | `/api/appointments/{no}` | 단건 조회 |
| GET | `/api/appointments/patient/{patientNo}` | 환자별 예약 조회 |
| GET | `/api/appointments/doctor/{doctorNo}` | 의사별 예약 조회 |
| POST | `/api/appointments` | 예약 등록 |
| PATCH | `/api/appointments/{no}/status` | 예약 상태 변경 |
| DELETE | `/api/appointments/{no}` | 예약 취소 |

### Medical Record (진료기록)
| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/api/medical-records` | 전체 조회 |
| GET | `/api/medical-records/{no}` | 단건 조회 |
| GET | `/api/medical-records/patient/{patientNo}` | 환자별 진료기록 |
| POST | `/api/medical-records` | 등록 |
| PUT | `/api/medical-records/{no}` | 수정 |
| DELETE | `/api/medical-records/{no}` | 삭제 |

---

## 🏗 아키텍처

```
Controller  →  Service  →  DAO  →  MyBatis Mapper (XML)  →  MySQL
   (REST)     (비즈니스)   (인터페이스)      (SQL)
```

**계층별 역할 분리**
- **Controller**: HTTP 요청/응답 처리, REST 엔드포인트
- **Service**: 비즈니스 로직 (예: 예약 시 기본 상태 "예약" 설정)
- **DAO**: DB 접근 인터페이스 (`@Mapper`)
- **Mapper XML**: 실제 SQL 쿼리

**패키지 구조**
```
com.hospital.reservation
├── controller   # REST 컨트롤러
├── service      # 비즈니스 로직
├── dao          # MyBatis 매퍼 인터페이스
└── vo           # 테이블 매핑 객체

src/main/resources/mapper/   # SQL XML
```

---

## ⚙️ 실행 방법

**1. DB 준비**
```sql
CREATE DATABASE hospital_reservation;
```

**2. application.properties 설정**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_reservation
spring.datasource.username=root
spring.datasource.password=your_password
mybatis.configuration.map-underscore-to-camel-case=true
```

**3. 실행**
```bash
./mvnw spring-boot:run
```

서버는 `http://localhost:8080`에서 실행됩니다.

---

## 🔍 주요 트러블슈팅

**1. MyBatis 카멜케이스 매핑**
- 문제: snake_case 컬럼(`department_name`)이 camelCase 필드에 null로 매핑됨
- 해결: `map-underscore-to-camel-case=true` 설정

**2. FK 제약조건 위반**
- 문제: 부모 테이블에 없는 값을 자식 테이블이 참조하여 INSERT 실패
- 해결: INSERT 순서 준수 (department → patient → doctor → appointment → medical_record)

**3. MariaDB / MySQL 드라이버 호환**
- 문제: 드라이버와 URL 프로토콜 불일치
- 해결: 드라이버와 `jdbc:mysql://` URL 프로토콜 일치

---

## 🚀 향후 계획 (진행 예정)

- [ ] React 기반 프론트엔드 (예약 화면 UI)
- [ ] 예외 처리 고도화 (404, 400 등 상태코드 응답)
- [ ] JOIN 쿼리로 예약 조회 시 환자명·의사명 함께 반환
- [ ] Python Django 기반 AI 챗봇 (예약 문의 자동 응답)
- [ ] 진료 후기 감성 분석 (LSTM/NLP)
- [ ] AWS 또는 Render 클라우드 배포

---

## 👤 개발자

**이지애 (Serena)**
- GitHub: [@serelee08](https://github.com/serelee08)
- Email: serelee08@gmail.com
