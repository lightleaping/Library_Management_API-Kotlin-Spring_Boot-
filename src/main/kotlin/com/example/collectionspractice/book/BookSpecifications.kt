// src/main/kotlin/com/example/collectionspractice/book/BookSpecifications.kt
package com.example.collectionspractice.book

import org.springframework.data.jpa.domain.Specification
import java.time.LocalDateTime
import jakarta.persistence.criteria.Predicate

object BookSpecifications {

    fun advancedFilter(
        query: String?,
        authorExact: String?,
        priceMin: Long?,
        priceMax: Long?,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?
    ): Specification<BookEntity> {
        return Specification<BookEntity> { root, _, cb ->
            val predicates = mutableListOf<Predicate>()

            // 제목/저자 부분일치
            if (!query.isNullOrBlank()) {
                val titleLike = cb.like(cb.lower(root.get("title")), "%${query.lowercase()}%")
                val authorLike = cb.like(cb.lower(root.get("author")), "%${query.lowercase()}%")
                predicates += cb.or(titleLike, authorLike)
            }

            // 저자 정확 일치
            if (!authorExact.isNullOrBlank()) {
                predicates += cb.equal(root.get<String>("author"), authorExact)
            }

            // 가격 범위
            if (priceMin != null) {
                predicates += cb.ge(root.get("price"), priceMin)
            }
            if (priceMax != null) {
                predicates += cb.le(root.get("price"), priceMax)
            }

            // 생성일 범위
            if (createdFrom != null) {
                predicates += cb.greaterThanOrEqualTo(root.get("createdAt"), createdFrom)
            }
            if (createdTo != null) {
                predicates += cb.lessThanOrEqualTo(root.get("createdAt"), createdTo)
            }

            cb.and(*predicates.toTypedArray())
        }
    }
}
