package com.bluewhaleyt.whaleutils.activity.playground.git

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluewhaleyt.common.common.dp
import com.bluewhaleyt.common.coroutines.runInBackground
import com.bluewhaleyt.common.coroutines.runOnUI
import com.bluewhaleyt.common.coroutines.withUI
import com.bluewhaleyt.git.GitUtils
import com.bluewhaleyt.whaleutils.R
import com.bluewhaleyt.whaleutils.activity.playground.PlaygroundActivity
import com.bluewhaleyt.whaleutils.databinding.ActivityPlaygroundGitBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.io.OutputStream
import java.io.PrintWriter

class GitPlaygroundActivity : PlaygroundActivity() {

    private lateinit var binding: ActivityPlaygroundGitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaygroundGitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBinding(binding)

        catchExceptionWithSnackbar {
            init()
        }
    }

    private fun getGit(): GitUtils {
        val localPath = binding.etLocalUrl.text.toString()
        return GitUtils(
            localPath = localPath,
        )
    }

    private fun init() {
        setupCardUrlSettings()
        setupCardCommits()

        setupCardCloneRepo()
    }

    private fun setupCardUrlSettings() {
        binding.etLocalUrl.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No-op
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val parentDir = File(s.toString()).parentFile
                val fileList = parentDir?.listFiles()?.toList()

                if (fileList.isNullOrEmpty()) {
                    Toast.makeText(applicationContext, "No files or directories found in parent directory", Toast.LENGTH_SHORT).show()
                } else {
                    val adapter = FileSimpleListAdapter(applicationContext, R.layout.layout_simple_list_item_1 , fileList)
                    binding.etLocalUrl.setAdapter(adapter)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // No-op
            }
        })
    }

    private fun setupCardCommits() {
        binding.btnPrintCommits.setOnClickListener {
            val git = getGit()
            val adapter = GitCommitAdapter(git)
            binding.rvCommit.apply {
                setAdapter(adapter)
                layoutManager = LinearLayoutManager(context)
            }
            catchExceptionWithSnackbar {
                val commits = git.getCommitList()
                withUI {
                    adapter.updateCommits(commits)
                    binding.tvCommitCount.text = commits.size.toString()
                    binding.rvCommit.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupCardCloneRepo() {
        binding.btnCloneRepo.setOnClickListener {
            val localUrl = binding.etLocalUrl.text.toString() + "/" + (0 until 10000).random()
            val remoteUrl = binding.etRemoteUrl.text.toString()

            val textView = TextView(this).apply {
                text = "Cloning..."
                setPadding(20.dp, 20.dp, 20.dp, 20.dp)
                setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_BodyMedium)
            }
            val sheet = BottomSheetDialog(this).apply {
                setContentView(textView)
                setCancelable(false)
                show()
            }

            runInBackground {
                val git = GitUtils(localUrl, remoteUrl)
                val writer = PrintWriter(
                    object : OutputStream() {
                        override fun write(p0: Int) {
                            runOnUI {
                                textView.append(p0.toChar().toString())
                            }
                        }
                        override fun write(b: ByteArray?) {
                            runOnUI {
                                textView.append("\n" + b?.toString(Charsets.UTF_8))
                            }
                        }
                    }
                )
                git.cloneRepository(writer)
                runOnUI {
                    sheet.dismiss()
                }
            }
        }
    }
}