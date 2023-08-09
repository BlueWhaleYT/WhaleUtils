package com.bluewhaleyt.network.okhttp

import com.bluewhaleyt.network.Methods
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * Utility class for making HTTP requests using [OkHttp](https://square.github.io/okhttp/) library.
 *
 * @param url the base URL of the API to be called
 */
class OkHttpUtils(
    private val url: String
) {

    private lateinit var newUrl: String
    private lateinit var newQueryParams: String

    val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()

    enum class Type {
        PARAMETERS, HEADERS
    }

    private val defaultTimeout = 60
    var readTimeout = defaultTimeout
    var writeTimeout = defaultTimeout
    var connectTimeout = defaultTimeout

    val client = OkHttpClient().newBuilder()
        .readTimeout(readTimeout.toLong(), TimeUnit.SECONDS)
        .writeTimeout(writeTimeout.toLong(), TimeUnit.SECONDS)
        .connectTimeout(connectTimeout.toLong(), TimeUnit.SECONDS)
        .build()

//    private var requestBuilder = Request.Builder().url(url)

    fun setTimeout(timeout: Int) {
        setTimeout(timeout, timeout, timeout)
    }

    fun setTimeout(readTimeout: Int? = null, writeTimeout: Int? = null, connectTimeout: Int? = null) {
        this.readTimeout = readTimeout!!
        this.writeTimeout = writeTimeout!!
        this.connectTimeout = connectTimeout!!
    }

    /**
     * Starts an HTTP request using OkHttp library.
     *
     * @param method the type of HTTP method to be used
     * @param formParameters the JSON data of form parameters to be sent with the POST request (optional, default is `null`)
     * @param queryParameters the JSON data of query parameters to be sent with the POST request (optional, default is `null`)
     * @param headers the JSON data of headers to be added to the request (optional, default is `null`)
     * @param listener the callback listener for the HTTP request
     *
     * @throws RuntimeException if the endpoint type is not supported
     *
     * @see Request
     */
    fun startRequest(
        method: Methods,
        formParameters: String? = null,
        queryParameters: String? = null,
        headers: String? = null,
        listener: OkHttpListener
    ) {

        newQueryParams = queryParameters
            ?.replace("{", "")
            ?.replace("}", "")
            ?.replace("\"", "")
            ?.replace(":", "=")
            ?.replace(",", "&")
            ?: ""
        newUrl = if (newQueryParams.isNotEmpty()) {
            "$url?$newQueryParams"
        } else {
            url
        }

        val requestBuilder = Request.Builder().url(newUrl)
        val body = formParameters?.toRequestBody(mediaType)!!

        headers?.let {
            val headersJson = JSONObject(it)
            headersJson.keys().forEach { key ->
                requestBuilder.addHeader(key, headersJson.getString(key))
            }
        }

        val request: Request = when (method) {
            Methods.DELETE -> {
                requestBuilder.delete(body).build()
            }
            Methods.GET -> {
                requestBuilder.build()
            }
            Methods.POST -> {
                requestBuilder.post(body).build()
            }
            Methods.PUT -> {
                requestBuilder.put(body).build()
            }
            Methods.PATCH -> {
                requestBuilder.patch(body).build()
            }
            else -> throw RuntimeException("Unsupported endpoint.")
        }

        start(request, listener)
    }

    fun setHeaders(vararg headers: Any): String {
        if (headers.size % 2 != 0) {
            throw IllegalArgumentException("Headers must be provided in key-value pairs")
        }

        val jsonObject = JSONObject().apply {
            headers.toList().chunked(2).forEach { (key, value) ->
                put(key.toString(), value.toString())
            }
        }

        return jsonObject.toString()
    }

    /**
     * Converts an array of key-value pairs to a JSON string.
     *
     * @param params the key-value pairs to be converted to JSON
     * @return the JSON string
     *
     * @throws IllegalArgumentException if the number of parameters is odd (i.e., not in key-value pairs)
     */
    fun setParameters(vararg params: Any): String {
        if (params.size % 2 != 0) {
            throw IllegalArgumentException("Params must be provided in key-value pairs")
        }

        val jsonObject = JSONObject().apply {
            for (i in params.indices step 2) {
                val key = params[i].toString()
                val value = params[i + 1]
                put(key, value)
            }
        }

        return jsonObject.toString()
    }

    fun addParameter(jsonObject: JSONObject, key: String, value: Any): JSONObject {
        return jsonObject.put(key, value)
    }

    /**
     * Starts an HTTP request using OkHttp library with the given request and callback listener.
     *
     * @param request the HTTP request to be made
     * @param listener the callback listener for the HTTP request
     */
    private fun start(request: Request, listener: OkHttpListener) {
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()!!
                if(response.isSuccessful) {
                    listener.onSuccess(response, responseBody)
                } else {
                    listener.onFailure(response, responseBody)
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                listener.onFailure(responseBody = e.message!!)
            }
        })
    }

    fun getURL(): String {
        return newUrl
    }

    fun getQueryParameters(): String {
        return newQueryParams
    }

}