// src/main/kotlin/com/example/collectionspractice/book/BookController.kt
package com.example.collectionspractice.book

import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/books")
class BookController(
    private val service: BookService
) {
    @PostMapping
    fun create(@RequestBody @Valid req: BookRequest): ResponseEntity<BookResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(service.create(req))

    @PutMapping("/{id}")
    fun replace(@PathVariable id: Long, @RequestBody @Valid req: BookRequest): ResponseEntity<BookResponse> =
        ResponseEntity.ok(service.replace(id, req))

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ResponseEntity<BookResponse> =
        ResponseEntity.ok(service.getOne(id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }

    /**
     * ✅ 기본 목록/검색 엔드포인트
     *  - BookService.search(...) 를 호출하도록 수정 (기존 service.list(...) 호출로 인한 500 에러 해결)
     *  - 응답은 PageResponse<BookResponse> 형태(이미 프로젝트에 있는 DTO를 사용)
     */
    @GetMapping
    fun list(
        @Parameter(description = "검색 키워드(제목/저자 부분일치). 비우면 전체")
        @RequestParam(required = false) query: String?,
        @Parameter(description = "페이지 번호(0부터)")
        @RequestParam(defaultValue = "0") page: Int,
        @Parameter(description = "페이지 크기(1~100)")
        @RequestParam(defaultValue = "10") size: Int,
        @Parameter(description = "정렬: createdAt,desc / price,asc ...")
        @RequestParam(required = false) sort: String?
    ): ResponseEntity<PageResponse<BookResponse>> {
        val p = service.search(query, page, size, sort)
        val body = PageResponse(
            content = p.content.map { it.toDto() },
            page = p.number,
            size = p.size,
            totalElements = p.totalElements,
            totalPages = p.totalPages,
            sort = sort ?: "createdAt,desc"
        )
        return ResponseEntity.ok(body)
    }

    /**
     * 🔵 고급 검색 엔드포인트 (선택 파라미터 다수)
     */
    @GetMapping("/advanced")
    fun listAdvanced(
        @Parameter(description = "부분일치(제목/저자)")
        @RequestParam(required = false) query: String?,
        @Parameter(description = "저자 정확 일치")
        @RequestParam(required = false) authorExact: String?,
        @Parameter(description = "최소 가격")
        @RequestParam(required = false) priceMin: Long?,
        @Parameter(description = "최대 가격")
        @RequestParam(required = false) priceMax: Long?,
        @Parameter(description = "생성일 시작 (ISO-8601, 예: 2025-08-01T00:00:00)")
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        createdFrom: LocalDateTime?,
        @Parameter(description = "생성일 종료 (ISO-8601)")
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        createdTo: LocalDateTime?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) sort: String?
    ): ResponseEntity<PageResponse<BookResponse>> {
        val p = service.searchAdvanced(
            query = query,
            authorExact = authorExact,
            priceMin = priceMin,
            priceMax = priceMax,
            createdFrom = createdFrom,
            createdTo = createdTo,
            page = page,
            size = size,
            sort = sort
        )
        val body = PageResponse(
            content = p.content.map { it.toDto() },
            page = p.number,
            size = p.size,
            totalElements = p.totalElements,
            totalPages = p.totalPages,
            sort = sort ?: "createdAt,desc"
        )
        return ResponseEntity.ok(body)
    }
}