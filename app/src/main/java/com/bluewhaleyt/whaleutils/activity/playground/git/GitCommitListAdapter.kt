package com.bluewhaleyt.whaleutils.activity.playground.git

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bluewhaleyt.git.GitUtils
import com.bluewhaleyt.whaleutils.R
import com.bluewhaleyt.whaleutils.activity.widget.Card
import org.eclipse.jgit.revwalk.RevCommit

class GitCommitListAdapter(
    private val files: List<Pair<String?, String?>>,
    private val git: GitUtils,
    private val commit: RevCommit
) :
    RecyclerView.Adapter<GitCommitListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvChangesType: TextView = itemView.findViewById(R.id.tv_changes_type)
        val etDiffContent: TextView = itemView.findViewById(R.id.et_file_content)
        val cardCommit: Card = itemView.findViewById(R.id.card_commit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_list_item_commit_file, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val file = files[position]
        val type = file.first
        val filePath = file.second.toString()

        holder.tvChangesType.text = type
        holder.cardCommit.subtitle = filePath
        holder.etDiffContent.text = git.getDiffContent(commit, filePath)

    }

    override fun getItemCount(): Int {
        return files.size
    }
}