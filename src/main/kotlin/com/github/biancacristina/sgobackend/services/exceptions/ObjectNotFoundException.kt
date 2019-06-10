package com.github.biancacristina.sgobackend.services.exceptions

class ObjectNotFoundException : RuntimeException {

    constructor(msg: String) : super(msg) {}

    constructor(msg: String, cause: Throwable) : super(msg, cause) {}

    companion object {
        private val serialVersionUID = 1L
    }
}