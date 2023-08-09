package com.bluewhaleyt.sora_editor

import android.animation.LayoutTransition
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.ProgressBar
import com.bluewhaleyt.common.common.getLayoutInflater
import com.bluewhaleyt.common.common.toPx
import io.github.rosemoe.sora.widget.component.CompletionLayout
import io.github.rosemoe.sora.widget.component.EditorAutoCompletion
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme

class CustomCompletionLayout : CompletionLayout {

    private lateinit var viewHolder: ViewHolder
    private lateinit var editorAutoCompletion: EditorAutoCompletion

    private var cornerRadius: Float = 0f
    private val stroke: Int = 1
    private var isAnimationEnabled: Boolean = false

    override fun onApplyColorScheme(colorScheme: EditorColorScheme) {
        val gd = GradientDrawable()
        gd.apply {
            cornerRadius = this@CustomCompletionLayout.cornerRadius.toPx(editorAutoCompletion.context).toFloat()
            setStroke(stroke, colorScheme.getColor(EditorColorScheme.COMPLETION_WND_CORNER))
            setColor(colorScheme.getColor(EditorColorScheme.COMPLETION_WND_BACKGROUND))
        }
        viewHolder.rootView.background = gd
    }

    override fun setEditorCompletion(completion: EditorAutoCompletion) {
        this.editorAutoCompletion = completion
    }

    override fun setEnabledAnimation(enabledAnimation: Boolean) {
        this.isAnimationEnabled = enabledAnimation

        val rootView = viewHolder.rootView
        val listView = viewHolder.lvCompletion

        if (isAnimationEnabled) {
            rootView.layoutTransition = LayoutTransition()
            val transition = rootView.layoutTransition
            transition.enableTransitionType(LayoutTransition.CHANGING)
            transition.enableTransitionType(LayoutTransition.APPEARING)
            transition.enableTransitionType(LayoutTransition.DISAPPEARING)
            transition.enableTransitionType(LayoutTransition.CHANGE_APPEARING)
            transition.enableTransitionType(LayoutTransition.CHANGE_DISAPPEARING)
            listView.layoutTransition = rootView.layoutTransition
        } else {
            rootView.layoutTransition = null
            listView.layoutTransition = null
        }
    }

    override fun inflate(context: Context): View {
        val rootView: View = context.getLayoutInflater().inflate(R.layout.layout_completion, null)
        this.viewHolder = ViewHolder(rootView)

        return rootView
    }

    override fun getCompletionList(): AdapterView<*> {
        return viewHolder.lvCompletion
    }

    override fun setLoading(loading: Boolean) {
        viewHolder.pbLoading.visibility = if (loading) View.VISIBLE else View.GONE
    }

    override fun ensureListPositionVisible(position: Int, incrementPixels: Int) {
        val listView = viewHolder.lvCompletion
        listView.post {
            while (listView.firstVisiblePosition + 1 > position && listView.canScrollList(-1)) {
                performScrollList(incrementPixels / 2)
            }
            while (listView.lastVisiblePosition - 1 < position && listView.canScrollList(1)) {
                performScrollList(-incrementPixels / 2)
            }
        }
    }

    private fun performScrollList(offset: Int) {
        val adpView = completionList
        val down = SystemClock.uptimeMillis()
        var ev = MotionEvent.obtain(down, down, MotionEvent.ACTION_DOWN, 0f, 0f, 0)
        adpView.onTouchEvent(ev)
        ev.recycle()
        ev = MotionEvent.obtain(down, down, MotionEvent.ACTION_MOVE, 0f, offset.toFloat(), 0)
        adpView.onTouchEvent(ev)
        ev.recycle()
        ev = MotionEvent.obtain(down, down, MotionEvent.ACTION_CANCEL, 0f, offset.toFloat(), 0)
        adpView.onTouchEvent(ev)
        ev.recycle()
    }

    private class ViewHolder(itemView: View) {
        val rootView = itemView as LinearLayout
        val lvCompletion = itemView.findViewById<ListView>(R.id.lv_completion)
        val pbLoading = itemView.findViewById<ProgressBar>(R.id.pb_loading)
    }

}