package com.example.collectionspractice.book

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class BookRequest(
    @field:NotBlank val title: String,
    @field:NotBlank val author: String,
    @field:Min(0) val price: Int
)

data class BookResponse(
    val id: Long,
    val title: String,
    val author: String,
    val price: Int
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
