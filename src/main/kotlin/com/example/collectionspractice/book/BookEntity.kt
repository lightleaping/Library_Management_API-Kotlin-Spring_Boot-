package com.example.collectionspractice.book

import jakarta.persistence.*

@Entity
@Table(name = "books")
class BookEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false) var title: String = "",
    @Column(nullable = false) var author: String = "",
    @Column(nullable = false) var price: Int = 0
) {
    protected constructor() : this(null, "", "", 0)
}