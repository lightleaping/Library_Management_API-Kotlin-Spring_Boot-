# 📚 CollectionsPractice – Library Management API  

## 🚀 Overview
This project is a **Library Management REST API** built with **Kotlin** and **Spring Boot**.  
It demonstrates clean software design using a **three-layer architecture** (Controller → Service → Repository),  
with input validation, global error handling, and additional features like search and pagination.  

The project is designed as a **beginner-friendly portfolio piece** for demonstrating REST API skills.

---

## 🏗️ Features
- **CRUD API** for books (`POST`, `GET`, `PUT`, `DELETE`)  
- **Validation** using `@NotBlank` and `@Min`  
- **Global exception handling** with consistent JSON errors  
- **Search & Pagination** for scalable data retrieval  
- **Layered architecture** (Controller, Service, Repository)  
- **In-memory data store** (`ConcurrentHashMap`), easy to swap with DB later  

---

## 📂 Project Structure
```
src/main/kotlin/com/example/collectionspractice/book
 ├── BookController.kt          # REST endpoints
 ├── BookDto.kt                 # Request/Response DTOs
 ├── BookService.kt             # Business logic
 ├── BookRepository.kt          # In-memory repository
 ├── GlobalExceptionHandler.kt  # JSON error responses
 └── NotFoundException.kt       # Custom exception
```

---

## ⚙️ Requirements
- JDK 17+
- Gradle 8+
- Kotlin 1.9+
- Spring Boot 3.5+

Dependencies:
```kotlin
implementation("org.springframework.boot:spring-boot-starter-web")
implementation("org.springframework.boot:spring-boot-starter-validation")
```

---

## ▶️ How to Run
```bash
./gradlew bootRun
```
Server runs at: `http://localhost:8080`

---

## 🧪 API Test (curl Examples)

### 1. Create a Book
```bash
curl -X POST http://localhost:8080/api/books  -H "Content-Type: application/json"  -d '{"title":"Clean Code","author":"Robert Martin","price":42000}'
```

### 2. Get All Books
```bash
curl http://localhost:8080/api/books
```

### 3. Get One Book
```bash
curl http://localhost:8080/api/books/1
```

### 4. Update a Book
```bash
curl -X PUT http://localhost:8080/api/books/1  -H "Content-Type: application/json"  -d '{"title":"Clean Code (2nd)","author":"Robert Martin","price":45000}'
```

### 5. Delete a Book
```bash
curl -X DELETE http://localhost:8080/api/books/1 -i
```

---

## 📊 Example Error Responses
- **400 Bad Request (Validation failed)**
```json
{
  "error": "validation_failed",
  "details": {
    "title": "must not be blank",
    "price": "must be >= 0"
  }
}
```

- **404 Not Found**
```json
{
  "error": "Book 99 not found"
}
```

---

## 🌱 Next Steps (for portfolio enhancement)
- Replace in-memory store with **Spring Data JPA + H2/MySQL**
- Add **Swagger/OpenAPI** for auto-generated API docs
- Write **unit tests** and **integration tests**
- Provide **Dockerfile** for deployment
