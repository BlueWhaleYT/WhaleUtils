package com.bluewhaleyt.common.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

typealias action<T> = suspend () -> T
typealias coroutineErrorListener = (throwable: Throwable) -> Unit

class CustomCoroutineScope(context: CoroutineContext, errorHandler: coroutineErrorListener?=null) : CoroutineScope,
    Closeable {

    override val coroutineContext: CoroutineContext = SupervisorJob() + context + UncaughtCoroutineExceptionHandler(errorHandler)

    override fun close() {
        coroutineContext.cancelChildren()
    }
}