package com.example.collectionspractice.book

enum class SortKey(val prop: String) {
    CREATED_AT("createdAt"),
    PRICE("price"),
    TITLE("title"),
    AUTHOR("author"),
    ID("id");

    companion object {
        private val byParam = mapOf(
            "createdAt" to CREATED_AT,
            "created_at" to CREATED_AT,
            "price" to PRICE,
            "title" to TITLE,
            "author" to AUTHOR,
            "id" to ID
        )
        fun fromParam(param: String?): SortKey? = param?.trim()?.let { byParam[it] }
    }
}
