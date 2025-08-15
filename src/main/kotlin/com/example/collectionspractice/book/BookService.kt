package com.example.collectionspractice.book

import org.springframework.stereotype.Service

@Service
class BookService(private val repo: BookRepository) {

    /** Return all books, already sorted by id in the repository. */
    fun getAll(): List<BookResponse> = repo.findAll()

    /** Return one book or throw when missing. */
    fun getOne(id: Long): BookResponse =
        repo.findById(id) ?: throw NotFoundException("Book $id not found")

    /** Create a new book and return the created record with its generated id. */
    fun create(req: BookRequest): BookResponse =
        repo.save(req)

    /** Full replace (PUT semantics). Throw when the id does not exist. */
    fun replace(id: Long, req: BookRequest): BookResponse =
        repo.update(id, req) ?: throw NotFoundException("Book $id not found")

    /** Delete by id. Throw when the id does not exist. */
    fun delete(id: Long) {
        if (!repo.delete(id)) throw NotFoundException("Book $id not found")
    }
}
