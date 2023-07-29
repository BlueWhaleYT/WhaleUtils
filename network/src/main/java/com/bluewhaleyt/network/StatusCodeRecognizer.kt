package com.bluewhaleyt.network

import java.lang.RuntimeException

/**
 * A class that recognizes the status code of an HTTP response and provides the corresponding color and description.
 *
 * @property statusCode the status code of the HTTP response.
 * @property colorInformational the color to be used for informational status codes.
 * @property colorSuccess the color to be used for success status codes.
 * @property colorRedirectional the color to be used for redirection status codes.
 * @property colorClientError the color to be used for client error status codes.
 * @property colorServerError the color to be used for server error status codes.
 */
class StatusCodeRecognizer(
    private val statusCode: Int,
    private val colorInformational: Int = 0xFF2b3f57.toInt(),
    private val colorSuccess: Int = 0xFF2cb87b.toInt(),
    private val colorRedirectional: Int = 0xFF3c7df5.toInt(),
    private val colorClientError: Int = 0xFFf79705.toInt(),
    private val colorServerError: Int = 0xFFf43c56.toInt(),
) {

    /**
     * Returns the color corresponding to the status code of the HTTP response.
     *
     * @return the color corresponding to the status code of the HTTP response.
     * @throws RuntimeException if the status code is invalid.
     */
    fun getColor(): Int {
        return when (statusCode) {
            in 100 .. 103 -> colorInformational

            in 200 .. 208, 226 -> colorSuccess

            in 300 .. 308 -> colorRedirectional

            in 400 .. 418 -> colorClientError
            in 421 .. 424 -> colorClientError
            in 426 .. 428 -> colorClientError
            429, 431, 444, 451, 499 -> colorClientError

            in 500 .. 511 -> colorServerError
            599 -> colorServerError

            else -> throw RuntimeException("Invalid status code: $statusCode")
        }
    }

    /**
     * Returns the description corresponding to the status code of the HTTP response.
     *
     * @return the description corresponding to the status code of the HTTP response.
     * @throws RuntimeException if the status code is invalid.
     */
    fun getDescription(): String {
        return when (statusCode) {
            100 -> "Continue"
            101 -> "Switching Protocols"
            102 -> "Processing"
            103 -> "Early Hints"

            200 -> "OK"
            201 -> "Created"
            202 -> "Accepted"
            203 -> "Non-authoritative Information"
            204 -> "No Content"
            205 -> "Reset Content"
            206 -> "Partial Content"
            207 -> "Multi-Status"
            208 -> "Already Reported"
            226 -> "IM Used"

            300 -> "Multiple Choices"
            301 -> "Moved Permanently"
            302 -> "Found"
            303 -> "See Other"
            304 -> "Not Modified"
            305 -> "Use Proxy"
            307 -> "Temporary Redirect"
            308 -> "Permanent Redirect"

            400 -> "Bad Request"
            401 -> "Unauthorized"
            402 -> "Payment Required"
            403 -> "Forbidden"
            404 -> "Not Found"
            405 -> "Method Not Allowed"
            406 -> "Not Acceptable"
            407 -> "Proxy Authentication Required"
            408 -> "Request Timeout"
            409 -> "Conflict"
            410 -> "Gone"
            411 -> "Length Required"
            412 -> "Precondition Failed"
            413 -> "Payload Too Large"
            414 -> "URI Too Long"
            415 -> "Unsupported Media Type"
            416 -> "Range Not Satisfiable"
            417 -> "Expectation Failed"
            418 -> "I'm a teapot"
            421 -> "Misdirected Request"
            422 -> "Unprocessable Entity"
            423 -> "Locked"
            424 -> "Failed Dependency"
            425 -> "Too Early"
            426 -> "Upgrade Required"
            428 -> "Precondition Required"
            429 -> "Too Many Requests"
            431 -> "Request Header Fields Too Large"
            444 -> "Connection Closed Without Response"
            451 -> "Unavailable For Legal Reasons"
            499 -> "Client Closed Request"

            500 -> "Internal Server Error"
            501 -> "Not Implemented"
            502 -> "Bad Gateway"
            503 -> "Service Unavailable"
            504 -> "Gateway Timeout"
            505 -> "HTTP Version Not Supported"
            506 -> "Variant Also Negotiates"
            507 -> "Insufficient Storage"
            508 -> "Loop Detected"
            510 -> "Not Extended"
            511 -> "Network Authentication Required"
            599 -> "Network Connect Timeout Error"

            else -> throw RuntimeException("Invalid status code: $statusCode")
        }
    }
}