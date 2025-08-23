package com.example.collectionspractice.book

import com.example.collectionspractice.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "books")
class BookEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false) var title: String = "",
    @Column(nullable = false) var author: String = "",
    @Column(nullable = false) var price: Long = 0
) : BaseEntity() { // ← BaseEntity 상속
    protected constructor() : this(null, "", "", 0)
}

