package com.bluewhaleyt.common.datasaving.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

/**
 * This utility aims to use [android.preference.PreferenceDataStore] replacing [SharedPrefsUtils] from [android.content.SharedPreferences]
 *
 * <p class="note">
 *     DataStore is a part of Jetpack Compose, but you can use it in non-compose projects.
 *     For Java, you can also use <code>DataStore</code> instead of <code>SharedPreferences</code>, which has better performance using Kotlin coroutine.
 * </p>
 *
 * @see [android.preference.PreferenceDataStore]
 */
class DataStoreUtils(context: Context, prefName: String) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(prefName)
    private val dataStore = context.dataStore

    private val exception = "This data type is not supported."

    /**
     * This property returns a continuation object for a coroutine that returns `Unit`. The returned continuation object
     * has an anonymous implementation of the `Continuation` interface, which has a null context and an empty
     * implementation of the `resumeWith()` function.
     *
     * <p class="note">
     *     For Java use only.
     * </p>
     *
     * @return an instance of [Continuation<Unit>].
     *
     * @see Continuation<Unit>
     * @see CoroutineContext
     */
    val continuation: Continuation<Unit>
        get() {
            return object : Continuation<Unit> {
                override val context: CoroutineContext
                    get() = null!!
                override fun resumeWith(result: Result<Unit>) {}
            }
        }

    /**
     * Writes a value to the DataStore given a key.
     *
     * @param key the key to associate the value with.
     * @param value the value to be written.
     *
     * @throws IllegalArgumentException if the value type is not supported.
     *
     * @see DataStore.edit
     * @see stringPreferencesKey
     * @see booleanPreferencesKey
     * @see intPreferencesKey
     * @see longPreferencesKey
     * @see floatPreferencesKey
     */
    suspend fun write(key: String, value: Any) {
        dataStore.edit { preferences ->
            when (value) {
                is String -> preferences[stringPreferencesKey(key)] = value
                is Boolean -> preferences[booleanPreferencesKey(key)] = value
                is Int -> preferences[intPreferencesKey(key)] = value
                is Long -> preferences[longPreferencesKey(key)] = value
                is Float -> preferences[floatPreferencesKey(key)] = value
                else -> throw IllegalArgumentException(exception)
            }
        }
    }

    /**
     * Returns a preferences key for the given key and default value.
     *
     * @param key the key to associate the value with.
     * @param defaultValue the default value to be used if the key is not present in the preferences.
     *
     * @return a preferences key for the given key and default value.
     * @throws IllegalArgumentException if the default value type is not supported.
     *
     * @see stringPreferencesKey
     * @see booleanPreferencesKey
     * @see intPreferencesKey
     * @see longPreferencesKey
     * @see floatPreferencesKey
     */
    private fun <T: Any> preferencesKey(key: String, defaultValue: T): Preferences.Key<out Any> {
        return when (defaultValue) {
            is String -> stringPreferencesKey(key)
            is Int -> intPreferencesKey(key)
            is Long -> longPreferencesKey(key)
            is Boolean -> booleanPreferencesKey(key)
            is Float -> floatPreferencesKey(key)
            else -> throw IllegalArgumentException(exception)
        }
    }

    /**
     * Retrieves a value from the DataStore given a key, using a coroutine.
     *
     * @param key the key to retrieve the value for.
     * @param defaultValue the default value to be used if the key is not present in the preferences.
     *
     * @return the value associated with the key, or the default value if the key is not present in the preferences.
     *
     * @see preferencesKey
     * @see Flow.first
     * @see withContext
     */
    suspend fun <T: Any> get(key: String, defaultValue: T): Any {
        val preferencesKey = preferencesKey(key, defaultValue)
        return try {
            withContext(Dispatchers.IO) {
                dataStore.data.first()[preferencesKey] ?: defaultValue
            }
        } catch (exception: IOException) {
            withContext(Dispatchers.Main) {
                emptyPreferences()[preferencesKey] ?: defaultValue
            }
        }
    }

    /**
     * Retrieves a value from the DataStore given a key, using a flow.
     *
     * @param key the key to retrieve the value for.
     * @param defaultValue the default value to be used if the key is not present in the preferences.
     *
     * @return a flow of the value associated with the key, or the default value if the key is not present in the preferences.
     *
     * @see preferencesKey
     * @see Flow.catch
     * @see Flow.map
     */
    fun <T: Any> getWithFlow(key: String, defaultValue: T): Flow<Any> {
        val preferencesKey = preferencesKey(key, defaultValue)
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                preferences[preferencesKey] ?: defaultValue
            }
    }

}