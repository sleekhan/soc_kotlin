package org.ryan.app.soc.error

import mu.KotlinLogging
import org.hibernate.exception.ConstraintViolationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class DefaultWebExceptionHandler {
    object ErrorResponse {
        const val STATUS_KEY = "status"
        const val ERROR_KEY = "error"
        const val MESSAGE_KEY = "message"
    }

    private val logger = KotlinLogging.logger {}

    @Autowired
    private lateinit var errorAttributes: ErrorAttributes

    @ExceptionHandler
    fun handelException(ex: Exception, request: WebRequest): ResponseEntity<Map<String, Any>> {
        val errorPropertiesMap = errorAttributes?.getErrorAttributes(request, ErrorAttributeOptions.defaults())
        val validationError = errorAttributes?.getError(request)

        logger.error { validationError }

        val httpStatus = when (validationError) {
            is MethodArgumentNotValidException -> HttpStatus.BAD_REQUEST // 400
            is ConstraintViolationException -> HttpStatus.BAD_REQUEST // 400
            is BindException -> HttpStatus.BAD_REQUEST // 400
            is MethodArgumentTypeMismatchException -> HttpStatus.BAD_REQUEST // 400
            is HttpRequestMethodNotSupportedException -> HttpStatus.BAD_REQUEST // 400
            is NoResourceFoundException -> HttpStatus.NOT_FOUND // 404
            else -> HttpStatus.INTERNAL_SERVER_ERROR // 500
        }

        errorPropertiesMap?.set(ErrorResponse.STATUS_KEY, httpStatus.value())
        errorPropertiesMap?.set(ErrorResponse.ERROR_KEY, httpStatus.reasonPhrase)
        errorPropertiesMap?.set(ErrorResponse.MESSAGE_KEY, validationError?.message)

        return ResponseEntity(errorPropertiesMap, httpStatus)
    }

}