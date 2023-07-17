package com.bluewhaleyt.git

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.errors.GitAPIException
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.transport.CredentialsProvider
import java.io.File
import java.lang.RuntimeException

/**
 * A utility class for interacting with Git repositories using JGit.
 *
 * <p class="note danger">
 *     This utility is unstable currently.
 * </p>
 *
 * @property localPath the local path of the Git repository
 * @property remotePath the remote path of the Git repository
 */
class GitUtils(
    private val localPath: String,
    private val remotePath: String
) {

    lateinit var credentialsProvider: CredentialsProvider
    var git = Git.open(File(localPath))

    val commits: Iterable<RevCommit> = git.log().call()

    /**
     * Adds the specified file pattern to the Git repository.
     *
     * @param filePattern The file pattern to add.
     * @param listener The listener to notify upon completion.
     *
     * @see runCommand
     * @see GitCommand.ADD
     */
    fun add(filePattern: String?, listener: GitListener?) {
        runCommand(GitCommand.ADD, listener!!, filePattern!!)
    }

    /**
     * Clones the Git repository.
     *
     * @param listener The listener to notify upon completion.
     * @see runCommand
     * @see GitCommand.CLONE
     */
    fun cloneRepository(listener: GitListener?) {
        runCommand(GitCommand.CLONE, listener!!)
    }

    /**
     * Commits the changes in the Git repository with the specified message.
     *
     * @param message The commit message.
     * @param listener The listener to notify upon completion.
     *
     * @see runCommand
     * @see GitCommand.COMMIT
     */
    fun commit(message: String, listener: GitListener?) {
        runCommand(GitCommand.COMMIT, listener!!, message)
    }

    /**
     * Pulls the changes from the Git repository.
     *
     * @param listener The listener to notify upon completion.
     *
     * @see runCommand
     * @see GitCommand.PULL
     */
    fun pullFromRepository(listener: GitListener?) {
        runCommand(GitCommand.PULL, listener!!)
    }

    fun listCommits(): List<RevCommit> {
        return commits.toList()
    }

    fun traverseCommitInfo(commits: List<RevCommit>, extractInfo: (RevCommit) -> Any?): List<Any?> {
        val result = mutableListOf<Any?>()
        for (commit in commits) {
            val info = extractInfo(commit)
            result.add(info)
        }
        return result
    }

    private fun runCommand(command: GitCommand, listener: GitListener, vararg params: String) {
        val progressMonitor = object : ProgressMonitor {
            private var currentFile = ""
            private var lastPercent = 0.0

            override fun start(totalTasks: Int) {}
            override fun beginTask(title: String?, totalWork: Int) {
                currentFile = title ?: ""
                listener.onProgress(File(currentFile), 0.0)
            }
            override fun update(completed: Int) {
                val percent = completed * 100.0 / 100.0
                if (percent != lastPercent) {
                    lastPercent = percent
                    listener.onProgress(File(currentFile), percent)
                }
            }
            override fun endTask() {}
            override fun isCancelled(): Boolean = false
        }
        when (command) {

            GitCommand.ADD -> {
                val filePattern = params[0].ifEmpty { "." }
                val addCommand = git.add().addFilepattern(filePattern)
                addCommand.call()
            }

            GitCommand.CLONE -> {
                val cloneCommand = Git.cloneRepository()
                    .setURI(remotePath)
                    .setDirectory(File(localPath))
                start(listener) { cloneCommand.setProgressMonitor(progressMonitor).call() }
            }

            GitCommand.COMMIT -> {
                val commitCommand = git.commit().setMessage(params[0])
                commitCommand.call()
            }

            GitCommand.FETCH -> {
                // TODO
            }

            GitCommand.PULL -> {
                val pullCommand = git.pull()
                start(listener) { pullCommand.setProgressMonitor(progressMonitor).call() }
            }

            GitCommand.PUSH -> {
                // TODO
            }
        }

    }

    private fun start(listener: GitListener, tryAction: () -> Unit) {
        try {
            tryAction()
            git.repository.close()
            listener.onSuccess()
        } catch (e: GitAPIException) {
            git.repository.close()
            listener.onFailure(e.message!!)
        }
    }
}