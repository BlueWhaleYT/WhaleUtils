package com.bluewhaleyt.common.datasaving

import android.content.Context
import android.content.SharedPreferences

@Suppress("UNCHECKED_CAST")
class SharedPrefsUtils(context: Context, prefName: String) {
    val prefs: SharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = prefs.edit()

    private val exception = "This data type is not supported."

    /**
     * Writes a key-value pair to the [SharedPreferences].
     *
     * The value's type should be one of the following:
     * [String], [Set<String>] [Int], [Boolean], [Float], or [Long].
     *
     * @param key The key to associate the value with.
     * @param value The value to store under the given key. The value must be one of the supported types.
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
    fun write(key: String, value: Any) {
        when (value) {
            is Set<*> -> editor.putStringSet(key, value as Set<String>)
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
            else -> throw IllegalArgumentException(exception)
        }
        editor.apply()
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
        editor.remove(key).commit()
    }

    /**
     * Clears all values from this [SharedPreferences].
     *
     * @see SharedPreferences.Editor.clear
     */
    fun clear() {
        editor.clear()
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

}