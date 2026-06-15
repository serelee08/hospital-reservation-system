# 🏥 Hospital Reservation System

A hospital appointment management system built with Spring Boot + MyBatis + MySQL with AI.

Provides RESTful APIs across 5 domains: department, patient, doctor, appointment, and medical record.

> 🌐 **[한국어](./README.md)** | **[English](./README_en.md)**

---

## 📌 Overview

A backend system modeling hospital appointment workflows. It implements the real-world hospital flow — patients book appointments with doctors, and medical records are created after treatment — through database design and REST APIs.

| Item | Detail |
|------|--------|
| Duration | 2026.06 ~ (in progress) |
| Team Size | 1 (personal project) |
| Core Goal | Normalized DB design with FK relations + layered REST API architecture |

---

## 🛠 Tech Stack

**Backend**
- Java 21
- Spring Boot 3.5
- MyBatis 3.0
- Spring Web (REST API)

**Database**
- MySQL / MariaDB
- 5-table normalized design (FK relations)

**Tools**
- Maven
- Postman (API testing)
- Git / GitHub
- Eclipse

---

## 🗄 Database Design (ERD)

![ERD](./erd.png)

```
department
    │
    ├──< doctor
    │
patient
    │
    └──< appointment >──┐
              │          │
              └──< medical_record
```

**Table Relations**
- `doctor` → `department` (a doctor belongs to one department)
- `appointment` → `patient`, `doctor` (links patient and doctor)
- `medical_record` → `appointment`, `patient`, `doctor`

**Design Highlights**
- `AUTO_INCREMENT` PK on all tables
- `DEFAULT NOW()` on `created_at` for automatic timestamp
- `UNIQUE` constraint on `member_id` to prevent duplicate registration
- Medical records preserved independently from appointments (denormalization for legal retention requirements)

---

## 🔗 API Specification

### Department
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/departments` | Get all |
| GET | `/api/departments/{no}` | Get one |
| POST | `/api/departments` | Create |
| PUT | `/api/departments/{no}` | Update |
| DELETE | `/api/departments/{no}` | Delete |

### Patient
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/patients` | Get all |
| GET | `/api/patients/{no}` | Get one |
| POST | `/api/patients` | Create |
| PUT | `/api/patients/{no}` | Update |
| DELETE | `/api/patients/{no}` | Delete |

### Doctor
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/doctors` | Get all |
| GET | `/api/doctors/{no}` | Get one |
| GET | `/api/doctors/department/{departmentNo}` | Get by department |
| POST | `/api/doctors` | Create |
| PUT | `/api/doctors/{no}` | Update |
| DELETE | `/api/doctors/{no}` | Delete |

### Appointment
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/appointments` | Get all |
| GET | `/api/appointments/{no}` | Get one |
| GET | `/api/appointments/patient/{patientNo}` | Get by patient |
| GET | `/api/appointments/doctor/{doctorNo}` | Get by doctor |
| POST | `/api/appointments` | Create appointment |
| PATCH | `/api/appointments/{no}/status` | Change status |
| DELETE | `/api/appointments/{no}` | Cancel appointment |

### Medical Record
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/medical-records` | Get all |
| GET | `/api/medical-records/{no}` | Get one |
| GET | `/api/medical-records/patient/{patientNo}` | Get by patient |
| POST | `/api/medical-records` | Create |
| PUT | `/api/medical-records/{no}` | Update |
| DELETE | `/api/medical-records/{no}` | Delete |

---

## 🏗 Architecture

```
Controller  →  Service  →  DAO  →  MyBatis Mapper (XML)  →  MySQL
   (REST)    (business)  (interface)     (SQL)
```

**Layered Separation of Concerns**
- **Controller**: Handles HTTP requests/responses, REST endpoints
- **Service**: Business logic (e.g., sets default "예약" status on booking)
- **DAO**: DB access interface (`@Mapper`)
- **Mapper XML**: Actual SQL queries

**Package Structure**
```
com.hospital.reservation
├── controller   # REST controllers
├── service      # Business logic
├── dao          # MyBatis mapper interfaces
└── vo           # Table mapping objects

src/main/resources/mapper/   # SQL XML
```

---

## ⚙️ How to Run

**1. Prepare DB**
```sql
CREATE DATABASE hospital_reservation;
```

**2. Configure application.properties**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_reservation
spring.datasource.username=root
spring.datasource.password=your_password
mybatis.configuration.map-underscore-to-camel-case=true
```

**3. Run**
```bash
./mvnw spring-boot:run
```

The server runs at `http://localhost:8080`.

---

## 🔍 Key Troubleshooting

**1. MyBatis Camel-Case Mapping**
- Issue: snake_case columns (`department_name`) mapped as null to camelCase fields
- Fix: Enabled `map-underscore-to-camel-case=true`

**2. FK Constraint Violation**
- Issue: INSERT failed when a child table referenced a non-existent parent value
- Fix: Followed INSERT order (department → patient → doctor → appointment → medical_record)

**3. MariaDB / MySQL Driver Compatibility**
- Issue: Driver and URL protocol mismatch
- Fix: Matched driver with `jdbc:mysql://` URL protocol

---

## 🚀 Roadmap (Planned)

- [ ] React-based frontend (appointment UI)
- [ ] Enhanced exception handling (404, 400 status responses)
- [ ] JOIN queries to return patient/doctor names with appointments
- [ ] Python Django AI chatbot (automated appointment inquiry)
- [ ] Sentiment analysis on patient reviews (LSTM/NLP)
- [ ] Cloud deployment (AWS or Render)

---

## 👤 Author

**Serena Lee (이지애)**
- GitHub: [@serelee08](https://github.com/serelee08)
- Email: serelee08@gmail.com
