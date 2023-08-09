package com.bluewhaleyt.network.openai

import com.bluewhaleyt.network.Methods
import com.bluewhaleyt.network.okhttp.OkHttpListener
import com.bluewhaleyt.network.okhttp.OkHttpUtils
import org.json.JSONArray
import org.json.JSONObject

class OpenAIUtils(
    private val builder: OpenAIBuilder
) {

    private val okHttpUtils: OkHttpUtils
    private lateinit var prompt: String

    val url: String get() {
        val temp = when (builder.getModel()) {
            OpenAIModels.TEXT_DAVINCI_003 -> COMPLETION_URL
            OpenAIModels.GPT_3_5 -> CHAT_COMPLETION_URL
            else -> null
        }
        return temp.toString()
    }

    companion object {
        const val COMPLETION_URL = "https://api.openai.com/v1/completions"
        const val CHAT_COMPLETION_URL = "https://api.openai.com/v1/chat/completions"
    }

    init {
        okHttpUtils = OkHttpUtils(url)
    }

    fun setPrompt(prompt: String) {
        this.prompt = prompt
    }

    fun startRequest(listener: OkHttpListener) {
        okHttpUtils.startRequest(
            method = Methods.POST,
            formParameters = applySettings(),
            headers = okHttpUtils.setHeaders(
                "Authorization", "Bearer ${builder.getAPIKey()}",
                "Content-Type", "application/json"
            ),
            listener = listener
        )
    }

    private fun applySettings(): String? {
        return when (builder.getModel()) {
            OpenAIModels.TEXT_DAVINCI_003 -> {
                okHttpUtils.setParameters(
                    OpenAISettings.MODEL, "text-davinci-003",
                    OpenAISettings.PROMPT, prompt,
                    OpenAISettings.TEMPERATURE, builder.getTemperature(),
                    OpenAISettings.MAX_TOKENS, builder.getMaxTokens(),
                    OpenAISettings.ECHO, builder.isEcho()
                )
            }
            OpenAIModels.GPT_3_5 -> {
                val jsonArray = JSONArray()
                val jsonMessageObj = JSONObject()
                jsonMessageObj.put("role", "user")
                jsonMessageObj.put("content", prompt)
                jsonArray.put(jsonMessageObj)

                okHttpUtils.setParameters(
                    OpenAISettings.MODEL, "gpt-3.5-turbo",
                    OpenAISettings.MESSAGE, jsonMessageObj,
                    OpenAISettings.TEMPERATURE, builder.getTemperature(),
                    OpenAISettings.MAX_TOKENS, builder.getMaxTokens(),
                    OpenAISettings.ECHO, builder.isEcho()
                )
            }
            else -> null
        }
    }

    fun getResponse(responseBody: String): String? {
        val jsonObj = JSONObject(responseBody)
        return when (builder.getModel()) {
            OpenAIModels.TEXT_DAVINCI_003 -> {
                val jsonArray = jsonObj.getJSONArray("choices")
                val newJsonObj = jsonArray.getJSONObject(0)
                val text = newJsonObj.getString("text")
                text.trim()
            }
            OpenAIModels.GPT_3_5 -> {
                val jsonArray = jsonObj.getJSONArray("choices")
                val newJsonObj = jsonArray.getJSONObject(0).getJSONObject("message")
                val text = newJsonObj.getString("content")
                text.trim()
            }
            else -> null
        }
    }

    fun getErrorResponse(responseBody: String): String {
        val jsonObj = JSONObject(responseBody)
        val newJsonObj = jsonObj.getJSONObject("error")
        return newJsonObj.getString("message")
    }

}