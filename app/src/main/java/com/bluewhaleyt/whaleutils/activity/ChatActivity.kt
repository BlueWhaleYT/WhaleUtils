package com.bluewhaleyt.whaleutils.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bluewhaleyt.design.widget.recyclerview.adapter.AdapterCallback
import com.bluewhaleyt.design.widget.recyclerview.adapter.CustomAdapter
import com.bluewhaleyt.whaleutils.R
import com.bluewhaleyt.whaleutils.databinding.ActivityChatBinding
import java.util.Date

data class Message(val message: String, val sender: String)
data class Timestamp(val time: Long)

object ViewType {
    const val MESSAGE: Int = 0
    const val TIMESTAMP: Int = 1
}

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CustomAdapter<Any>(this)
        adapter.addViewType(ViewType.MESSAGE, R.layout.layout_list_item_1)
        adapter.addViewType(ViewType.TIMESTAMP, R.layout.layout_chat_timestamp)

        adapter.setCallback(object : AdapterCallback<Any> {
            override fun getItemViewType(data: Any, itemIndex: Int): Int {
                return when (data) {
                    is Message -> ViewType.MESSAGE
                    is Timestamp -> ViewType.TIMESTAMP
                    else -> throw IllegalArgumentException("Invalid item type")
                }
            }

            override fun onCreateView(itemView: View, data: Any, itemIndex: Int) {
                when (data) {
                    is Message -> {
                        val tvSender = itemView.findViewById<TextView>(R.id.tv_text_1)
                        val tvMessage = itemView.findViewById<TextView>(R.id.tv_text_2)
                        tvSender.text = data.sender
                        tvMessage.text = data.message
                    }
                    is Timestamp -> {
                        val tvTime = itemView.findViewById<TextView>(R.id.tv_timestamp)
                        tvTime.apply {
                            val stamp = Timestamp(data.time)
                            val date = Date(stamp.time)

                            text = date.toString()
                            setTextColor(Color.GRAY)
                        }
                    }
                }
            }
        })

        val chatItems: List<Any> = listOf(
            Timestamp(1085347200L),
            Message("Hello", "John"),
            Timestamp(1685347200L),
            Message("How are you?", "John"),
            Message("I'm good. Thanks!", "Alice"),
        )

        binding.rvChat.setAdapter(adapter, chatItems)

    }
}