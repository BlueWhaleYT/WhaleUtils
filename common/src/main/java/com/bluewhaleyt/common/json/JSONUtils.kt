package com.bluewhaleyt.common.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.RuntimeException

class JSONUtils(
    private val jsonString: String
) {

    /**
     * Parses a JSON string and returns either a JSONObject or JSONArray based on its structure.
     *
     * @param jsonString the JSON string to parse
     * @return a JSONObject or JSONArray if the string can be parsed, or null if it cannot be parsed as valid JSON
     *
     * @see JSONObject
     * @see JSONArray
     */
    fun parseJSON(jsonString: String): Any? {
        return try {
            JSONObject(jsonString)
        } catch (e: JSONException) {
            try {
                JSONArray(jsonString)
            } catch (e: JSONException) {
                throw RuntimeException("Invalid response, unable to be parsed as JSON.")
            }
        }
    }

    /**
     * Converts a JSON string to a pretty-printed JSON string.
     *
     * @return the pretty-printed JSON string
     *
     * @see GsonBuilder.setPrettyPrinting
     */
    fun prettifyJSON(): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonElement = JsonParser.parseString(jsonString)
        return gson.toJson(jsonElement)
    }

    /**
     * Extracts a map of key-value pairs from a JSON string.
     *
     * @return a map of key-value pairs extracted from the JSON response
     *
     * @see Gson.fromJson
     */
    fun getValueFromKey(key: String): Any? {
        val map = Gson().fromJson<Map<String, Any>>(jsonString, object : TypeToken<HashMap<String, Any>>() {}.type)
        return map[key]
    }
}