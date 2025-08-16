package com.example.collectionspractice.book

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    // 404 for domain "not found"
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(e: NotFoundException): ResponseEntity<Map<String, Any?>> =
        ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(mapOf("error" to (e.message ?: "not_found")))

    // 400 for @Valid body errors
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(e: MethodArgumentNotValidException): ResponseEntity<Map<String, Any?>> {
        val details = e.bindingResult.fieldErrors
            .groupBy({ it.field }) { it.defaultMessage ?: "invalid" }
            .mapValues { (_, msgs) -> msgs.first() } // keep first message per field
        return ResponseEntity.badRequest()
            .body(mapOf("error" to "validation_failed", "details" to details))
    }
}