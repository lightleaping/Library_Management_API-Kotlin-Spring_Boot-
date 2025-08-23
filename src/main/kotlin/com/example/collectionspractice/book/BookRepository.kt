// src/main/kotlin/com/example/collectionspractice/book/BookRepository.kt
package com.example.collectionspractice.book

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface BookRepository :
    JpaRepository<BookEntity, Long>,
    JpaSpecificationExecutor<BookEntity> {

    fun findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
        title: String,
        author: String,
        pageable: Pageable
    ): Page<BookEntity>
}
