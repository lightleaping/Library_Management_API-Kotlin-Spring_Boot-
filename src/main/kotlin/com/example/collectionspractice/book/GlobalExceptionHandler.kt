package com.example.collectionspractice.book

import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.format.DateTimeParseException

@RestControllerAdvice
class GlobalExceptionHandler {

    data class ErrorDetail(val field: String?, val message: String)
    data class ErrorResponse(
        val error: String,
        val message: String,
        val details: List<ErrorDetail> = emptyList()
    )

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(e: NotFoundException) =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorResponse(
                error = "NOT_FOUND",
                message = e.message ?: "resource not found"
            )
        )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val details = e.bindingResult.fieldErrors.map {
            ErrorDetail(field = it.field, message = it.defaultMessage ?: "invalid value")
        }
        return ResponseEntity.badRequest().body(
            ErrorResponse(
                error = "VALIDATION_ERROR",
                message = "Invalid request",
                details = details
            )
        )
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(e: ConstraintViolationException) =
        ResponseEntity.badRequest().body(
            ErrorResponse(
                error = "CONSTRAINT_VIOLATION",
                message = "Invalid request",
                details = e.constraintViolations.map {
                    ErrorDetail(field = it.propertyPath?.toString(), message = it.message)
                }
            )
        )

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArg(e: IllegalArgumentException): ResponseEntity<ErrorResponse> =
        ResponseEntity.badRequest().body(
            ErrorResponse(
                error = "VALIDATION_ERROR",
                message = e.message ?: "Invalid parameter",
                details = listOf(ErrorDetail(field = "sort", message = e.message ?: "Invalid sort"))
            )
        )

    @ExceptionHandler(Exception::class)
    fun handleOthers(e: Exception) =
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ErrorResponse(
                error = "INTERNAL_ERROR",
                message = e.message ?: "internal server error"
            )
        )

    @ExceptionHandler(DateTimeParseException::class)
    fun handleDateParse(e: DateTimeParseException) =
        ResponseEntity.badRequest().body(
            ErrorResponse(
                error = "VALIDATION_ERROR",
                message = "Invalid date format (use ISO-8601, e.g., 2025-08-01T00:00:00)",
                details = listOf(ErrorDetail(field = "date", message = e.message ?: "Invalid date"))
            )
        )
}
