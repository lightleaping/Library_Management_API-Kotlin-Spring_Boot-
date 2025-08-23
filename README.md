# 📚 Library Management API (Kotlin + Spring Boot)

## 📌 프로젝트 개요
이 프로젝트는 **도서 관리 REST API**입니다.  
Kotlin과 Spring Boot를 기반으로 설계되었으며, **Controller → Service → Repository → Entity** 구조를 따릅니다.  

## ✨ 주요 기능
- 도서 등록 (Create)
- 도서 조회 (Read, 전체/단일)
- 도서 수정 (Update)
- 도서 삭제 (Delete)
- Swagger UI를 통한 API 문서 및 실행

## ⚙️ 기술 스택
- **Kotlin**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **H2 Database (in-memory)**
- **Swagger / OpenAPI**

## 🚀 실행 방법
### 1. H2 프로파일 실행
```bash
./gradlew bootRun --args='--spring.profiles.active=h2'
```

### 2. Swagger UI 접속
[http://localhost:8080/](http://localhost:8080/)  

### 3. H2 Console 접속
[http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
- JDBC URL: `jdbc:h2:mem:library`  
- User: `sa`  
- Password: (비움)

## 📖 API 문서 (Swagger)
### 📌 Books API
- **POST /api/books** : 도서 등록  
- **GET /api/books** : 전체 도서 조회  
- **GET /api/books/{id}** : 단일 도서 조회  
- **PUT /api/books/{id}** : 도서 수정  
- **DELETE /api/books/{id}** : 도서 삭제  

## 📸 실행 화면
> 여기에는 Swagger UI 캡처 화면과 H2 Console 캡처 화면을 첨부하세요.

예시:
1. Swagger에서 `POST /api/books` 실행 성공 화면
2. Swagger에서 `GET /api/books` 조회 결과 화면
3. H2 Console에서 `SELECT * FROM BOOKS;` 실행 결과 화면

---

## 📌 향후 발전 방향
- MySQL DB 연동 (실제 운영 DB 경험)
- 검색 API (제목/저자 키워드 검색)
- 페이징 API (대량 데이터 처리)
