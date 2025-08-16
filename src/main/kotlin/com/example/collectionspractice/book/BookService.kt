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

    fun list(query: String?, page: Int, size: Int): List<BookResponse> {
        require(page >= 0) { "page must be >= 0" }
        require(size > 0) { "size must be > 0" }

        var items = repo.findAll()

        if (!query.isNullOrBlank()){
            val q = query.trim().lowercase()
            items = items.filter { it.title.lowercase().contains(q) || it.author.lowercase().contains(q) }
        }

        val from = page * size
        if (from >= items.size) return emptyList()
        val to = minOf(from + size, items.size)
        return items.subList(from, to)
    }
}