package com.github.biancacristina.sgobackend.resources.exceptions

import com.github.biancacristina.sgobackend.services.exceptions.ObjectNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest
import com.github.biancacristina.sgobackend.services.exceptions.DataIntegrityException

@ControllerAdvice
class ResourceExceptionHandler {
    @ExceptionHandler(ObjectNotFoundException::class)
    fun objectNotFound(
            e: ObjectNotFoundException,
            request: HttpServletRequest
    ): ResponseEntity<StandardError> {
        // Handle the exception of kind 404 (Not Found)

        val err = StandardError(
                System.currentTimeMillis(),
                HttpStatus.NOT_FOUND.value(),
                "Não encontrado",
                e.message,
                request.requestURI)

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err)
    }

    @ExceptionHandler(DataIntegrityException::class)
    fun dataIntegrity(
            e: DataIntegrityException,
            request: HttpServletRequest
    ): ResponseEntity<StandardError> {
        // Handle the exception of kind 400 (Bad Request)
        val err = StandardError(
                System.currentTimeMillis(),
                HttpStatus.BAD_REQUEST.value(),
                "Integridade de dados",
                e.message,
                request.requestURI)

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validation(
            e: MethodArgumentNotValidException,
            request: HttpServletRequest
    ): ResponseEntity<StandardError> {
        // Handle the validations error of kind 422 (Unprocessable entity)

        val err = ValidationError(
                System.currentTimeMillis(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação",
                e.message,
                request.requestURI)

        for (error in e.bindingResult.fieldErrors) {
            err.addError(error.field, error.defaultMessage!!)
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err)
    }
}