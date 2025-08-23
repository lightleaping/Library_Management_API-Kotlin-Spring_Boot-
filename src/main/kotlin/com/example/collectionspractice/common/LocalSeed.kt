// src/main/kotlin/com/example/collectionspractice/common/LocalSeed.kt
package com.example.collectionspractice.common

import com.example.collectionspractice.book.BookEntity
import com.example.collectionspractice.book.BookRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("local")
class LocalSeed {
    @Bean
    fun seed(repo: BookRepository) = CommandLineRunner {
        if (repo.count() == 0L) {
            val items = (1..20).map { i ->
                BookEntity(title = "Sample Book $i", author = "Author $i", price = 1000L * i)
            }
            repo.saveAll(items)
        }
    }
}