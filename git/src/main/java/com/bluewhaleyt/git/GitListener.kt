package com.bluewhaleyt.git

import java.io.File

/**
 * Interface for listening to Git operations.
 */
interface GitListener {
    /**
     * Called when the Git operation is successful.
     */
    fun onSuccess()

    /**
     * Called when the Git operation fails.
     * @param error The error message.
     */
    fun onFailure(error: String)

    /**
     * Called to update the progress of the Git operation.
     * @param file The file that is currently being processed.
     * @param progress The progress of the operation as a percentage.
     */
    fun onProgress(file: File, progress: Double)
}