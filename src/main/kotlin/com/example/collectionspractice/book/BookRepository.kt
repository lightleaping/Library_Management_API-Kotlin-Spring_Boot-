package com.example.collectionspractice.book

import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<BookEntity, Long> {
    fun findByTitleContainingIgnoreCase(q: String): List<BookEntity>
    fun findByAuthorContainingIgnoreCase(q: String): List<BookEntity>
}