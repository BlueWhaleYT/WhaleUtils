package com.bluewhaleyt.common.storage

import android.content.Context
import android.content.SharedPreferences

/**
 * This utility aims to simplify the usage of [SharedPreferences].
 *
 * <p class="note danger">
 *     For Kotlin, using <code>DataStore</code> instead is highly recommended, please refer
 *     <a href="../-data-store-utils/index.html">DataStore</a>.
 * </p>
 *
 * @see SharedPreferences
 */
@Suppress("UNCHECKED_CAST")
@Deprecated(
    message = "SharedPreferences is deprecated because of its performance. Please use DataStore instead.",
    replaceWith = ReplaceWith(
        expression = "",
        imports = [
            "com.bluewhaleyt.common.datasaving.utils.DataStoreUtils"
        ]
    )
)
class SharedPrefsUtils(context: Context, prefName: String) {
    val prefs: SharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    val prefsEditor: SharedPreferences.Editor = prefs.edit()

    private val exception = "This data type is not supported."

    /**
     * Writes a key-value pair to the [SharedPreferences].
     *
     * The value's type should be one of the following:
     * [String], [Set<String>] [Int], [Boolean], [Float], or [Long].
     *
     * @param key The key to associate the value with.
     * @param value The value to store under the given key. The value must be one of the supported types.
     * @param oneTimeOnly If true, the value will be saved as one-time only.
     *
     * @throws IllegalArgumentException if the value is not one of the supported types.
     *
     * @see SharedPreferences.Editor.putStringSet
     * @see SharedPreferences.Editor.putString
     * @see SharedPreferences.Editor.putInt
     * @see SharedPreferences.Editor.putBoolean
     * @see SharedPreferences.Editor.putFloat
     * @see SharedPreferences.Editor.putLong
     */
    fun write(key: String, value: Any, oneTimeOnly: Boolean = false) {
        if (oneTimeOnly && prefs.contains(key)) {
            // The key already exists in the SharedPreferences, so we can't set it as one-time only.
            return
        }
        prefsEditor.apply {
            when (value) {
                is Set<*> -> putStringSet(key, value as Set<String>)
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                else -> throw IllegalArgumentException(exception)
            }
            if (oneTimeOnly) {
                // Mark the key as one-time only
                putBoolean("${key}_one_time_only", true)
            }
            apply()
        }
    }

    /**
     * Retrieves the value associated with the given key from [SharedPreferences]. If the key is not found,
     * the default value is returned.
     *
     * The value's type should be one of the following:
     * [String], [Set<String>] [Int], [Boolean], [Float], or [Long].
     *
     * @param key The key to retrieve the value for.
     * @param defaultValue The default value to return if the key is not found.
     *
     * @return The value associated with the given key, or the default value if the key is not found.
     * @throws IllegalArgumentException if the default value is not one of the supported types.
     *
     * @see SharedPreferences.getStringSet
     * @see SharedPreferences.getString
     * @see SharedPreferences.getInt
     * @see SharedPreferences.getBoolean
     * @see SharedPreferences.getFloat
     * @see SharedPreferences.getLong
     */
    fun <T: Any> get(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is Set<*> -> prefs.getStringSet(key, defaultValue as Set<String>) as T
            is String -> prefs.getString(key, defaultValue) as T
            is Int -> prefs.getInt(key, defaultValue) as T
            is Boolean -> prefs.getBoolean(key, defaultValue) as T
            is Float -> prefs.getFloat(key, defaultValue) as T
            is Long -> prefs.getLong(key, defaultValue) as T
            else -> throw IllegalArgumentException(exception)
        }
    }

    /**
     * Removes the entry for the specified key from this [SharedPreferences].
     *
     * @param key the key of the preference to remove.
     * @see SharedPreferences.Editor.remove
     */
    fun remove(key: String?) {
        prefsEditor.remove(key).commit()
    }

    /**
     * Clears all values from this [SharedPreferences].
     *
     * @see SharedPreferences.Editor.clear
     */
    fun clear() {
        prefsEditor.clear()
    }

    /**
     * Returns a list of keys for all preferences stored in the app.
     *
     * @return a list of Strings representing the keys of all preferences in the app.
     *
     * @see SharedPreferences.getAll
     */
    fun listPreferences(): List<String> {
        return prefs.all.map { it.key }
    }

    /**
     * Returns true if the specified key is marked as one-time only in the [SharedPreferences].
     *
     * @param key The key to check.
     * @return true if the key is marked as one-time only, false otherwise.
     */
    fun isOneTime(key: String): Boolean {
        return prefs.getBoolean("${key}_one_time_only", false)
    }

}