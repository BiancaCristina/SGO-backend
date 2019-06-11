package com.github.biancacristina.sgobackend.resources.exceptions

import java.util.ArrayList

class ValidationError(
        timeStamp: Long?,
        status: Int?,
        error: String,
        message: String,
        path: String
): StandardError(timeStamp, status, error, message, path) {

    private val errors = ArrayList<FieldMessage>()

    fun getErrors(): List<FieldMessage> {
        return errors
    }

    fun addError(fieldName: String, message: String) {
        errors.add(FieldMessage(fieldName, message))
    }
}