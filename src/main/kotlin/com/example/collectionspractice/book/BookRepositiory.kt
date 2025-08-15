package com.example.collectionspractice.book

import org.springframework.stereotype.Repository
import java.awt.print.Book
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Repository
class BookRepositiory {
    private val seq = AtomicLong(1)
    private val store = ConcurrentHashMap<Long, BookResponse>()

    fun findAll(): List<BookResponse> = store.values.sortedBy { it.id }
    fun findById(id: Long): BookResponse? = store[id]

    fun save(req: BookRequest): BookResponse {
        val id = seq.getAndIncrement()
        val saved = BookResponse(id, req.title, req.author, req.price)
        store[id] = saved
        return saved
    }

    fun update(id: Long, req: BookRequest): BookResponse? {
        if(!store.containsKey(id)) return null
        val updated = BookResponse(id, req.title, req.author, req.price)
        store[id] = updated
        return updated
    }

    fun delete(id: Long): Boolean = store.remove(id) != null
}