package com.bluewhaleyt.network.openai

/**
 * A builder for configuring and creating an instance of the [OpenAIUtils] client.
 *
 * @see OpenAIUtils
 * @see OpenAIModels
 * @see OpenAISettings
 */
class OpenAIBuilder {

    /**
     * The API key to use for accessing the OpenAI API.
     */
    private var apiKey: String = ""

    /**
     * The OpenAI model to use for generating text or images.
     */
    private var model: OpenAIModels? = null

    /**
     * The temperature to use for generating text.
     */
    private var temperature: Float = 0f

    /**
     * The maximum number of tokens to generate.
     */
    private var maxTokens: Int = 4000

    /**
     * Whether or not to echo the prompt in the generated text.
     */
    private var isEcho = false

    fun setModel(model: OpenAIModels) = apply { this.model = model }
    fun getModel(): OpenAIModels? = model

    fun setAPIKey(apiKey: String) = apply { this.apiKey = apiKey }
    fun getAPIKey(): String = apiKey

    fun setTemperature(temperature: Float) = apply { this.temperature = temperature }
    fun getTemperature(): Float = temperature

    fun setMaxTokens(maxTokens: Int) = apply { this.maxTokens = maxTokens }
    fun getMaxTokens(): Int = maxTokens

    fun setEcho(echo: Boolean) = apply { this.isEcho = echo }
    fun isEcho(): Boolean = isEcho

    fun build(): OpenAIBuilder {
        return this
    }
}