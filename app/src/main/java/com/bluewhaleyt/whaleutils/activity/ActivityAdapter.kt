package com.bluewhaleyt.whaleutils.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bluewhaleyt.common.common.getAttributeColor
import com.bluewhaleyt.whaleutils.R
import com.bluewhaleyt.whaleutils.databinding.LayoutListCardItem1Binding

class ActivityAdapter(private val activities: List<ActivityInfo>) : RecyclerView.Adapter<ActivityViewHolder>() {

    private lateinit var binding: LayoutListCardItem1Binding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        binding = LayoutListCardItem1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val context = holder.itemView.context
        val activity = activities[position]
        val packageManager = context.packageManager

        val label = if (activity.loadLabel(packageManager) == context.resources.getString(R.string.app_name)) {
            "Unnamed"
        } else activity.loadLabel(packageManager)
        val fullPackage = activity.name.substringBeforeLast(".") + "."
        val onlyClassName = activity.name.substringAfterLast(".")

        val finalSpannable = concatSpannableStrings(
            fullPackage to context.getAttributeColor("textColorSecondary"),
            onlyClassName to context.getAttributeColor("primaryColor")
        )

        binding.card.title = (label.toString())
        binding.tvText1.text = finalSpannable

        holder.itemView.setOnClickListener {
            val intent = Intent().apply {
                setClassName(activity.packageName, activity.name)
            }
            if (!activity.name.equals("${activity.packageName}.activity.MainActivity")) {
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    fun concatSpannableStrings(vararg spans: Pair<String, Int>): SpannableString {
        val builder = SpannableStringBuilder()
        spans.forEach { (span, color) ->
            builder.append(span)
            builder.setSpan(ForegroundColorSpan(color), builder.length - span.length, builder.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return SpannableString.valueOf(builder)
    }

    override fun getItemCount() = activities.size
}

class ActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)