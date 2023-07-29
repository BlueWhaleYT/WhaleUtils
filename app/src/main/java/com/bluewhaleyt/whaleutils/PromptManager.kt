package com.bluewhaleyt.whaleutils

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.bluewhaleyt.common.common.getAttributeColor
import com.bluewhaleyt.common.common.getResourceFont
import com.bluewhaleyt.common.common.toStringResource
import com.bluewhaleyt.common.common.waitFor
import com.bluewhaleyt.common.widget.showSnackbar
import com.bluewhaleyt.whaleutils.databinding.ActivityPlaygroundOkhttpBinding
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt

class PromptManager(
    private val activity: Activity,
    private val binding: ViewBinding
) {
    fun set() {
        activity.waitFor(500) {
            setToBinding1()
        }
    }

    private fun setToBinding1() {
        if (binding is ActivityPlaygroundOkhttpBinding) {
            showPrompt(
                target = binding.etApiUrl,
                primaryText = R.string.network_request_prompt_title_api_url.toStringResource(activity),
                secondaryText = R.string.network_request_prompt_message_api_url.toStringResource(activity)
            ) {
                showPrompt(
                    target = binding.btnAddHeader,
                    primaryText = R.string.network_request_prompt_title_header.toStringResource(activity),
                    secondaryText = R.string.network_request_prompt_message_header.toStringResource(activity)
                ) {
                    showPrompt(
                        target = binding.btnAddParam,
                        primaryText = R.string.network_request_prompt_title_parameter.toStringResource(activity),
                        secondaryText = R.string.network_request_prompt_message_parameter.toStringResource(activity)
                    ) {
                        showPrompt(
                            target = binding.btnSendRequest,
                            primaryText = R.string.network_request_prompt_title_send_request.toStringResource(activity),
                            secondaryText = R.string.network_request_prompt_message_send_request.toStringResource(activity)
                        ) {

                        }
                    }
                }
            }
        }

        else activity.showSnackbar("No guides currently")
    }

    private fun showPrompt(
        target: View,
        primaryText: String = "",
        secondaryText: String = "",
        state: Int = MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED,
        click: () -> Unit
    ) {
        MaterialTapTargetPrompt.Builder(activity).apply {
            primaryTextColour = activity.getAttributeColor("textColor")
            secondaryTextColour = activity.getAttributeColor("textColorSecondary")
            secondaryTextSize = 32f

            primaryTextTypeface = activity.getResourceFont(R.font.quicksand_semibold)
            secondaryTextTypeface = activity.getResourceFont(R.font.quicksand_medium)

            backgroundColour = activity.getAttributeColor("widgetBackgroundColor", 0.8f)
            focalColour = 0x00000000

            val text = when (target) {
                is EditText -> target.hint
                is TextView -> target.text
                else -> {}
            }

            setTarget(target)

            if (secondaryText.isEmpty()) {
                setSecondaryText("""
                    ${activity.resources.getString(R.string.prompt_hint_continue)}
                    ${activity.resources.getString(R.string.prompt_hint_skip, text)}
                """.trimIndent())
            } else {
                setSecondaryText("""
                    $secondaryText

                    ${activity.resources.getString(R.string.prompt_hint_continue)}
                    ${activity.resources.getString(R.string.prompt_hint_skip, text)}
                """.trimIndent())
            }
            setPrimaryText(primaryText)
            setPromptStateChangeListener { _, _state ->
                if (_state == state) click()
            }
            show()
        }
    }
}