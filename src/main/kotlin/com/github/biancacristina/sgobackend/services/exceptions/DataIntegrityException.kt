package com.github.biancacristina.sgobackend.services.exceptions

class DataIntegrityException : RuntimeException {
    constructor(msg: String) : super(msg)
    constructor(msg: String, cause: Throwable) : super(msg, cause)
}