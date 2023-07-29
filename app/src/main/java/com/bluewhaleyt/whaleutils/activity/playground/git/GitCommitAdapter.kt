package com.bluewhaleyt.whaleutils.activity.playground.git

import android.app.ProgressDialog.show
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluewhaleyt.common.animation.AnimationUtils
import com.bluewhaleyt.common.animation.Animations
import com.bluewhaleyt.common.common.dp
import com.bluewhaleyt.common.common.getLayoutInflater
import com.bluewhaleyt.common.common.toColorResource
import com.bluewhaleyt.git.GitUtils
import com.bluewhaleyt.whaleutils.R
import com.bluewhaleyt.whaleutils.databinding.LayoutListItem3Binding
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.eclipse.jgit.revwalk.RevCommit

class GitCommitAdapter(private val git: GitUtils) : RecyclerView.Adapter<GitCommitAdapter.ViewHolder>() {

    private lateinit var binding: LayoutListItem3Binding

    private val commits = mutableListOf<RevCommit>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = LayoutListItem3Binding.inflate(parent.context.getLayoutInflater(), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val commit = commits[position]
        holder.bind(git, commit)

        val anim = AnimationUtils(holder.itemView.context)
        anim.applyAnimation(holder.itemView, Animations.RIGHT_TO_LEFT, anim.DURATION_LONG)
    }

    override fun getItemCount(): Int {
        return commits.size
    }

    fun updateCommits(commits: List<RevCommit>) {
        this.commits.clear()
        this.commits.addAll(commits)
        notifyItemRangeChanged(0, commits.size)
    }

    class ViewHolder(private val binding: LayoutListItem3Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(git: GitUtils, commit: RevCommit) {
            val context = itemView.context
            val commitId = commit.id.abbreviate(7).name()

            val changeType = git.getFirstChangeTypeOfCommit(commit)
            val color = when (changeType?.name) {
                "MODIFY" -> R.color.warningColor.toColorResource(context)
                "ADD" -> R.color.successColor.toColorResource(context)
                "DELETE" -> R.color.errorColor.toColorResource(context)
                else -> 0
            }
            val icon = when (changeType?.name) {
                "MODIFY" -> R.drawable.baseline_edit_24
                "ADD" -> R.drawable.baseline_add_24
                "DELETE" -> R.drawable.baseline_delete_24
                else -> 0
            }

            val borderSize = 4
            val borderColor = color

            val borderShape = GradientDrawable()
            borderShape.shape = GradientDrawable.RECTANGLE
            borderShape.setStroke(borderSize, borderColor)

            val layerList = LayerDrawable(arrayOf(borderShape))
            layerList.setLayerInset(0, 0, (-5).dp, (-5).dp, (-5).dp)

            itemView.background = layerList

            binding.chipText1.apply {
                chipBackgroundColor = ColorStateList.valueOf(color).withAlpha(50)
                chipStrokeColor = ColorStateList.valueOf(color)
                setChipIconResource(icon)
                chipIconTint = ColorStateList.valueOf(color)
            }

            binding.tvText1.text = commit.shortMessage
            binding.tvText2.text = commitId
            binding.tvText3.text = commit.committerIdent.name

            binding.tvText1.maxLines = 1
            binding.tvText1.ellipsize = TextUtils.TruncateAt.END

            setCommitClick(context, git, commit, commitId)
        }

        private fun setCommitClick(context: Context, git: GitUtils, commit: RevCommit, commitId: String) {
            itemView.setOnClickListener {
                val dialog = BottomSheetDialog(context)

                val changes = git.getChangesOfCommit(commit)

                val files = mutableListOf<Pair<String, String>>()
                changes?.forEach { diffEntry ->
                    val filePath = if (diffEntry.newPath != "/dev/null") diffEntry.newPath else diffEntry.oldPath
                    val fileStatus = diffEntry.changeType.toString()
                    files.add(Pair(fileStatus, filePath))
                }


                val view = View.inflate(context, R.layout.layout_dialog_git_commit_file_view, null)
                val recyclerView = view.findViewById<RecyclerView>(R.id.rv_files)
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = GitCommitListAdapter(files, git, commit)

                dialog.apply {
                    setTitle(commitId)
                    setContentView(view)
                    show()
                }
            }
        }

    }
}


//                changes?.forEach { diffEntry ->
//                    val filePath = if (diffEntry.newPath != "/dev/null") diffEntry.newPath else diffEntry.oldPath
//                    val fileStatus = diffEntry.changeType.toString()
//                    message.append("$fileStatus: $filePath\n")
//                    val fileContent = git.getFileContent(commit, filePath)
//                    if (fileContent != null) {
//                        message.append(fileContent)
//                        message.append("\n\n")
//                    }
//                }