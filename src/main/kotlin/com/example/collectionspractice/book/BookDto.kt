// src/main/kotlin/com/example/collectionspractice/book/BookDto.kt
package com.example.collectionspractice.book

import jakarta.validation.constraints.*

data class BookRequest(
    @field:NotBlank(message = "{book.title.notBlank}")
    @field:Size(max = 100, message = "{book.title.size}")
    val title: String,

    @field:NotBlank(message = "{book.author.notBlank}")
    @field:Size(max = 60, message = "{book.author.size}")
    val author: String,

    @field:PositiveOrZero(message = "{book.price.positiveOrZero}")
    @field:Max(value = 1_000_000, message = "{book.price.max}")
    val price: Long
)


data class BookResponse(
    val id: Long,
    val title: String,
    val author: String,
    val price: Long
)

fun BookEntity.toDto() = BookResponse(
    id = this.id!!,
    title = this.title,
    author = this.author,
    price = this.price
)

fun BookRequest.toEntity() = BookEntity(
    title = this.title,
    author = this.author,
    price = this.price
)
