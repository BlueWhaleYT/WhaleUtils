package com.bluewhaleyt.common.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

class UncaughtCoroutineExceptionHandler(private val errorHandler: coroutineErrorListener?=null)  :
    CoroutineExceptionHandler, AbstractCoroutineContextElement(CoroutineExceptionHandler.Key) {

    override fun handleException(context: CoroutineContext, throwable: Throwable) {
        throwable.printStackTrace()

        errorHandler?.let {
            it(throwable)
        }
    }
}