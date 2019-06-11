package com.github.biancacristina.sgobackend.resources.exceptions

class FieldMessage{
    var fieldName: String? = null
    var message: String? = null

    constructor(fieldName: String, message: String) : super() {
        this.fieldName = fieldName
        this.message = message
    }
}