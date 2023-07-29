package com.bluewhaleyt.whaleutils.activity.playground.network

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.view.children
import com.bluewhaleyt.common.animation.AnimationUtils
import com.bluewhaleyt.common.animation.Animations
import com.bluewhaleyt.common.common.getAttributeColor
import com.bluewhaleyt.common.common.toStringResource
import com.bluewhaleyt.common.json.JSONUtils
import com.bluewhaleyt.common.widget.showSnackbar
import com.bluewhaleyt.network.Requests
import com.bluewhaleyt.network.StatusCodeRecognizer
import com.bluewhaleyt.network.okhttp.OkHttpListener
import com.bluewhaleyt.network.okhttp.OkHttpUtils
import com.bluewhaleyt.whaleutils.R
import com.bluewhaleyt.whaleutils.activity.playground.PlaygroundActivity
import com.bluewhaleyt.whaleutils.databinding.ActivityPlaygroundOkhttpBinding
import com.google.android.material.button.MaterialButton
import okhttp3.Response
import org.json.JSONObject

class OkHttpPlaygroundActivity : PlaygroundActivity() {

    private lateinit var binding: ActivityPlaygroundOkhttpBinding
    private val anim = AnimationUtils(this)

    private val COLOR_BLUE = 0xFF3c7df5.toInt()
    private val COLOR_GREEN = 0xFF2cb87b.toInt()
    private val COLOR_YELLOW = 0xFFf79705.toInt()
    private val COLOR_ORANGE = 0xFFfd6c25.toInt()
    private val COLOR_RED = 0xFFf43c56.toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaygroundOkhttpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBinding(binding)

        init()
    }

    private fun init() {
        setupCard1()
        setupCard2()
        setupCard3()
        setupCard4()
        setupCard5()

        binding.btnSendRequest.setOnClickListener {
            if (binding.etApiUrl.text?.isEmpty()!!) showSnackbar("Please enter API url.")
            else sendRequest()
        }
    }

    private fun setupCard1() {
        setupButtonGroup()
    }

    private fun setupCard2() {
        binding.btnAddParam.setOnClickListener { addOption(OkHttpUtils.Type.PARAMETERS) }
    }

    private fun setupCard3() {
        binding.btnAddHeader.setOnClickListener { addOption(OkHttpUtils.Type.HEADERS) }
    }

    private fun setupCard4() {
        binding.jsonViewer.apply {
            setTextColorNumber(COLOR_YELLOW)
            setTextColorString(COLOR_GREEN)
            setTextColorNull(COLOR_RED)
            setTextColorBool(COLOR_BLUE)
        }
    }

    private fun setupCard5() {

    }

    private fun setupButtonGroup() {
        binding.btnGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                val btnChecked = findViewById<MaterialButton>(checkedId)
                val color = when (btnChecked.text) {
                    "GET" -> COLOR_BLUE
                    "POST" -> COLOR_GREEN
                    "PUT" -> COLOR_YELLOW
                    "PATCH" -> COLOR_ORANGE
                    "DELETE" -> COLOR_RED
                    else -> 0
                }
                for (btn in binding.btnGroup.children) {
                    if (btn is MaterialButton) {
                        btn.apply {
                            backgroundTintList = ColorStateList.valueOf(0)
                            strokeColor = ColorStateList.valueOf(getAttributeColor("widgetBackgroundColor"))
                            setTextColor(getAttributeColor("textColor"))
                        }
                    }
                }
                val value = ColorStateList.valueOf(color)
                btnChecked.apply {
                    backgroundTintList = value.withAlpha(50)
                    strokeColor = value
                    setTextColor(value)
                }
            }
            binding.cardParameters.title =
                if (getMethod<String>() == "GET") R.string.network_request_label_query_parameters.toStringResource(this)
                else R.string.network_request_label_parameters.toStringResource(this)
        }
        binding.btnGroup.check(binding.btnGet.id)
    }

    private fun addOption(type: OkHttpUtils.Type) {
        val layout = when (type) {
            OkHttpUtils.Type.PARAMETERS -> binding.layoutParameterItem
            OkHttpUtils.Type.HEADERS -> binding.layoutHeaderItem
        }
        val newRow = layoutInflater.inflate(R.layout.layout_playground_okhttp_parameter, layout, false)
        val btnRemove = newRow.findViewById<Button>(R.id.btn_remove_param)
        btnRemove.setOnClickListener {
            anim.applyAnimation(newRow, Animations.BOUNCE_OUT) {
                layout.removeView(newRow)
            }
        }
        layout.addView(newRow)
        anim.applyAnimation(newRow, Animations.BOUNCE_IN)
    }

    private fun getJSONFromOptions(okhttp: OkHttpUtils, type: OkHttpUtils.Type): String {
        val layout = when (type) {
            OkHttpUtils.Type.PARAMETERS -> binding.layoutParameterItem
            OkHttpUtils.Type.HEADERS -> binding.layoutHeaderItem
        }
        val jsonObject = JSONObject()
        for (i in 0 until layout.childCount) {
            val paramRow = layout.getChildAt(i)
            val keyView = paramRow.findViewById<EditText>(R.id.et_param_key)
            val valueView = paramRow.findViewById<EditText>(R.id.et_param_value)
            val key = keyView?.text?.toString()?.trim()
            val value = valueView?.text?.toString()?.trim()
            if (!key.isNullOrEmpty()) {
                okhttp.addParameter(jsonObject, key, value!!)
            }
        }
        return jsonObject.toString()
    }

    private fun sendRequest() {
        setLoading(true)
        val url = binding.etApiUrl.text.toString()
        val okhttp = OkHttpUtils(url)

        try {
            okhttp.startRequest(
                method = getMethod<Requests>() as Requests,
                formParameters = getJSONFromOptions(okhttp, OkHttpUtils.Type.PARAMETERS),
                queryParameters = getJSONFromOptions(okhttp, OkHttpUtils.Type.PARAMETERS),
                headers  = getJSONFromOptions(okhttp, OkHttpUtils.Type.HEADERS),
                listener = object : OkHttpListener {
                    override fun onSuccess(response: Response, responseBody: String) {
                        runOnUiThread {
                            setLoading(false)
                            try {
                                setResponse(response, responseBody)
                            } catch (e: Exception) {
                                setError(e, "onSuccess() \n\n")
                            }
                        }
                    }
                    override fun onFailure(response: Response?, responseBody: String) {
                        runOnUiThread {
                            setLoading(false)
                            try {
                                setResponse(response!!, responseBody)
                            } catch (e: Exception) {
                                setError(e, "onFailure() \n\n")
                            }
                        }
                    }
                }
            )
        } catch (e: Exception) {
            showSnackbar(e.message.toString())
            setLoading(false)
        }
    }

    private inline fun <reified T> getMethod(): Any {
        val btnChecked = findViewById<Button>(binding.btnGroup.checkedButtonId)
        return when (T::class) {
            String::class -> {
                btnChecked.text.toString()
            }
            Requests::class -> {
                when(btnChecked.text) {
                    "GET" -> Requests.GET
                    "POST" -> Requests.POST
                    "PUT" -> Requests.PUT
                    "PATCH" -> Requests.PATCH
                    "DELETE" -> Requests.DELETE
                    else -> throw IllegalArgumentException("Invalid method")
                }
            }
            else -> {}
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.pbLoading.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun setError(error: Exception, tag: String = "") {
        binding.cardResponse.visibility = View.GONE
        binding.cardError.visibility = View.VISIBLE
        binding.tvError.text = "$tag${error.localizedMessage}"
    }

    private fun setResponse(response: Response, responseBody: String) {
        val json = JSONUtils(responseBody)

        binding.cardError.visibility = View.GONE
        binding.cardResponse.visibility = View.VISIBLE
        binding.jsonViewer.setJson(json.parseJSON(responseBody))
        binding.tvError.text = ""

        val statusCode = response.code
        val statusCodeRecognizer = StatusCodeRecognizer(statusCode)
        val color = statusCodeRecognizer.getColor()

        binding.tvResponseStatus.apply {
            text = "${statusCode} - ${statusCodeRecognizer.getDescription()}"
            setTextColor(color)
        }
        binding.statusDot.backgroundTintList = ColorStateList.valueOf(color)

    }

}