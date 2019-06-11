package com.github.biancacristina.sgobackend.resources.exceptions

open class StandardError(
    var timeStamp: Long?,
    var status: Int?,
    var error: String?,
    var message: String?,
    var path: String?
)