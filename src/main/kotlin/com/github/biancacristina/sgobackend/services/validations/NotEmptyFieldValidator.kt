package com.github.biancacristina.sgobackend.services.validations

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class NotEmptyFieldValidator: ConstraintValidator<NotEmptyField, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        return value!!.isNotEmpty()
    }
}