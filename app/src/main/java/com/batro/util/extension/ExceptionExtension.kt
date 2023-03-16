package com.batro.util.extension

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

fun Throwable.isNetworkException(): Boolean {
    var cause: Throwable? = this
    while (cause != null) {
        if (cause is ConnectException ||
            cause is UnknownHostException ||
            cause is SocketTimeoutException ||
            cause is SSLException
        ) {
            return true
        }
        cause = cause.cause
    }
    return false
}