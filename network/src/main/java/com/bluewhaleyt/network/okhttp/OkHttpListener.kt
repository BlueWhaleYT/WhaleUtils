package com.bluewhaleyt.network.okhttp

import okhttp3.Response

/**
 * Interface to define callbacks for OkHttp requests.
 */
interface OkHttpListener {

    /**
     * Called when the HTTP request is successful.
     *
     * @param response the response returned from the server
     * @param responseBody the response body string returned from the server
     */
    fun onSuccess(response: Response, responseBody: String)

    /**
     * Called when the HTTP request fails.
     *
     * @param response the response returned from the server
     * @param responseBody the response body string returned from the server
     */
    fun onFailure(response: Response? = null, responseBody: String)
}