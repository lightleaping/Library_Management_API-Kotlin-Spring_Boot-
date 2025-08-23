// src/main/kotlin/com/example/collectionspractice/book/BookService.kt
package com.example.collectionspractice.book

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDateTime

@Service
class BookService(private val repo: BookRepository) {

    fun create(req: BookRequest): BookResponse =
        repo.save(req.toEntity()).toDto()

    fun getOne(id: Long): BookResponse =
        repo.findById(id).orElseThrow { NotFoundException("Book $id not found") }.toDto()

    // 기존 간단 검색(호환 유지)
    fun search(query: String?, page: Int, size: Int, sort: String?): Page<BookEntity> {
        val pageable = buildPageable(page, size, sort)
        return if (query.isNullOrBlank()) {
            repo.findAll(pageable)
        } else {
            repo.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query, pageable)
        }
    }

    // 🔵 고급 검색(새로 추가)
    fun searchAdvanced(
        query: String?,
        authorExact: String?,
        priceMin: Long?,
        priceMax: Long?,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?,
        page: Int,
        size: Int,
        sort: String?
    ): Page<BookEntity> {
        val pageable = buildPageable(page, size, sort)
        val spec: Specification<BookEntity> = BookSpecifications.advancedFilter(
            query = query,
            authorExact = authorExact,
            priceMin = priceMin,
            priceMax = priceMax,
            createdFrom = createdFrom,
            createdTo = createdTo
        )
        return repo.findAll(spec, pageable)
    }

    fun replace(id: Long, req: BookRequest): BookResponse {
        val e = repo.findById(id).orElseThrow { NotFoundException("Book $id not found") }
        e.title = req.title
        e.author = req.author
        e.price = req.price
        return repo.save(e).toDto()
    }

    fun delete(id: Long) {
        if (!repo.existsById(id)) throw NotFoundException("Book $id not found")
        repo.deleteById(id)
    }

    private fun buildPageable(page: Int, size: Int, sort: String?): Pageable {
        require(page >= 0) { "page must be >= 0" }
        require(size in 1..100) { "size must be 1..100" }

        val defaultSort = Sort.by("createdAt").descending()
        if (sort.isNullOrBlank()) return PageRequest.of(page, size, defaultSort)

        val parts = sort.split(",")
        val rawProp = parts.getOrNull(0)?.trim()
        val dirStr = parts.getOrNull(1)?.trim()?.lowercase() ?: "desc"

        val key = SortKey.fromParam(rawProp)
            ?: throw IllegalArgumentException("unsupported sort property: $rawProp")
        val direction = when (dirStr) {
            "asc" -> Sort.Direction.ASC
            "desc" -> Sort.Direction.DESC
            else -> throw IllegalArgumentException("unsupported sort direction: $dirStr")
        }
        return PageRequest.of(page, size, Sort.by(direction, key.prop))
    }
}
