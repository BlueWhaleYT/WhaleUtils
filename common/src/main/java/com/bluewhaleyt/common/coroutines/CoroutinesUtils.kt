package com.bluewhaleyt.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

/**
 * This class is completely for Kotlin.
 * For Java, please see
 * @see JCoroutinesUtils
 */
open class CoroutinesUtils {
    companion object {
        private val UI: CoroutineDispatcher = Dispatchers.Main
        private val IO: CoroutineDispatcher = Dispatchers.IO
        private val Default: CoroutineDispatcher = Dispatchers.Default
        private val Unconfined: CoroutineDispatcher = Dispatchers.Unconfined

        val continuation: Continuation<Unit>
            get() {
                return object : Continuation<Unit> {
                    override val context: CoroutineContext
                        get() = null!!
                    override fun resumeWith(result: Result<Unit>) {}
                }
            }

        fun runOnUI(block: suspend CoroutineScope.() -> Unit) = uiScope().launch(UI, block = block)
        fun runInBackground(block: suspend CoroutineScope.() -> Unit) = ioScope().launch(IO, block = block)
        fun asyncOnUI(block: suspend CoroutineScope.() -> Any?) = uiScope().async(UI, block = block)
        fun asyncInBackground(block: suspend CoroutineScope.() -> Any?) = ioScope().async(IO, block = block)

        suspend fun <T> withUI(block: action<T>): T = withContext(UI) { block() }
        suspend fun <T> withBackground(block: action<T>): T = withContext(IO) { block() }
        suspend fun <T> withDefault(block: action<T>): T = withContext(Default) { block() }
        suspend fun <T> withUnconfined(block: action<T>): T = withContext(Unconfined) { block() }

        protected fun cancel(job: Job) { job.cancel() }

        fun ioScope(errorHandler: coroutineErrorListener? = null) = CustomCoroutineScope(IO, errorHandler)
        fun uiScope(errorHandler: coroutineErrorListener? = null) = CustomCoroutineScope(UI, errorHandler)
        fun defaultScope(errorHandler: coroutineErrorListener? = null) = CustomCoroutineScope(Default, errorHandler)
        fun customScope(dispatcher: CoroutineDispatcher, errorHandler: coroutineErrorListener? = null) = CustomCoroutineScope(dispatcher,  errorHandler)


    }
}

@JvmSynthetic
fun <T> runOnUI(block: action<T>) = CoroutinesUtils.runOnUI { block() }
@JvmSynthetic
fun <T> runInBackground(block: action<T>) = CoroutinesUtils.runInBackground { block() }
@JvmSynthetic
fun <T> asyncOnUI(block: action<T>) = CoroutinesUtils.asyncOnUI { block() }
@JvmSynthetic
fun <T> asyncInBackground(block: action<T>) = CoroutinesUtils.asyncInBackground { block() }
@JvmSynthetic
suspend fun <T> withUI(block: action<T>) = CoroutinesUtils.withUI(block)
@JvmSynthetic
suspend fun <T> withBackground(block: action<T>) = CoroutinesUtils.withBackground(block)
@JvmSynthetic
suspend fun <T> withDefault(block: action<T>) = CoroutinesUtils.withDefault(block)
@JvmSynthetic
suspend fun <T> withUnconfined(block: action<T>) = CoroutinesUtils.withUnconfined(block)