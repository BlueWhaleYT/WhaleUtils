package com.bluewhaleyt.git

import android.util.Log
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.diff.DiffEntry
import org.eclipse.jgit.diff.DiffFormatter
import org.eclipse.jgit.lib.ObjectId
import org.eclipse.jgit.lib.TextProgressMonitor
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.treewalk.CanonicalTreeParser
import org.eclipse.jgit.treewalk.EmptyTreeIterator
import org.eclipse.jgit.treewalk.TreeWalk
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.Writer


class GitUtils(
    private val localPath: String? = null,
    private val remotePath: String? = null,
) {

    var repo = FileRepositoryBuilder().setGitDir(File(localPath, ".git")).build()
    val git: Git = Git(repo)

    fun add(filePattern: String? = ".") {
        runCommand(GitCommands.ADD, filePattern!!)
    }

    fun commit(message: String) {
        runCommand(GitCommands.COMMIT, message)
    }

    fun pushRepository() {
        runCommand(GitCommands.PUSH)
    }

    fun cloneRepository(writer: Writer? = null) {
        runCommand(GitCommands.CLONE, writer!!)
    }

    private fun runCommand(command: GitCommands, vararg params: Any) {
        when (command) {

            GitCommands.ADD -> {
                val filePattern = params[0].toString()
                val addCommand = git.add().addFilepattern(filePattern)
                addCommand.call()
            }

            GitCommands.COMMIT -> {
                val message = params[0].toString()
                val commitCommand = git.commit().setMessage(message)
                commitCommand.call()
            }

            GitCommands.PUSH -> {
                val pushCommand = git.push()
                pushCommand.call()
            }

            GitCommands.CLONE -> {
                val writer = params[0] as Writer

                val cloneCommand = Git.cloneRepository()
                    .setURI(remotePath)
                    .setDirectory(File(localPath.toString()))
                    .setProgressMonitor(TextProgressMonitor(writer))
                cloneCommand.call()

            }

            else -> {}
        }
    }

    fun getCommitList(): List<RevCommit> {
        val localBranch = git.repository.branch
        val revWalk = RevWalk(git.repository)
        val latestCommit = revWalk.parseCommit(git.repository.resolve(localBranch))
        revWalk.close()
        return if (latestCommit != null) {
            git.log().call().toList()
        } else {
            Log.e("GitUtils", "No commits found")
            emptyList()
        }
    }

    fun getChangedFiles(commit: RevCommit): List<String> {
        val treeWalk = TreeWalk(git.repository)
        treeWalk.addTree(commit.tree)
        if (commit.parentCount > 0) {
            // Add the parent tree only if it is not the same as the commit tree
            val parent = commit.getParent(0)
            if (!parent.tree.equals(commit.tree)) {
                treeWalk.addTree(parent.tree)
            }
            // Skip the second parent of merge commits
            if (commit.parentCount > 1) {
                val mergeParent = commit.getParent(1)
                if (!mergeParent.tree.equals(commit.tree) && !mergeParent.tree.equals(parent.tree)) {
                    treeWalk.addTree(mergeParent.tree)
                }
            }
        } else {
            treeWalk.addTree(ObjectId.zeroId())
        }
        treeWalk.isRecursive = true
        val changedFiles = mutableListOf<String>()
        while (treeWalk.next()) {
            val path = treeWalk.pathString
            if (treeWalk.isSubtree) {
                treeWalk.enterSubtree()
                continue
            }
            val oldId = treeWalk.getObjectId(1)
            val newId = treeWalk.getObjectId(0)
            if (oldId == ObjectId.zeroId()) {
                changedFiles.add(path)
            } else {
                val oldObject = git.repository.open(oldId)
                val newObject = git.repository.open(newId)
                if (!oldObject.bytes.contentEquals(newObject.bytes)) {
                    changedFiles.add(path)
                }
            }
        }
        return changedFiles
    }

    @Throws(IOException::class)
    fun getChangesOfCommit(commit: RevCommit): List<DiffEntry>? {
        val diffEntries: MutableList<DiffEntry> = ArrayList()
        RevWalk(repo).use { revWalk ->
            val parentCommit = if (commit.parentCount > 0) {
                revWalk.parseCommit(commit.getParent(0).id)
            } else {
                null
            }
            repo.newObjectReader().use { reader ->
                val oldTreeIter = if (parentCommit != null) {
                    CanonicalTreeParser().apply {
                        reset(reader, parentCommit.tree.id)
                    }
                } else {
                    EmptyTreeIterator()
                }
                val newTreeIter = CanonicalTreeParser().apply {
                    reset(reader, commit.tree.id)
                }
                Git(repo).use { git ->
                    val diffs = git.diff()
                        .setNewTree(newTreeIter)
                        .setOldTree(oldTreeIter)
                        .call()
                    diffEntries.addAll(diffs)
                }
            }
        }
        return diffEntries
    }

    fun getChangesTypeOfCommit(commit: RevCommit): List<DiffEntry.ChangeType> {
        val changes = getChangesOfCommit(commit)
        return changes?.map { it.changeType } ?: emptyList()
    }

    fun getFirstChangeTypeOfCommit(commit: RevCommit): DiffEntry.ChangeType? {
        val changeTypes = getChangesTypeOfCommit(commit)
        return changeTypes.firstOrNull()
    }

    fun getFileContent(commit: RevCommit, filePath: String): String? {
        val diffEntries = getChangesOfCommit(commit)
        for (diffEntry in diffEntries ?: emptyList()) {
            if (diffEntry.newPath == filePath || diffEntry.oldPath == filePath) {
                val objectId = if (diffEntry.newPath != "/dev/null") {
                    diffEntry.newId.toObjectId()
                } else {
                    diffEntry.oldId.toObjectId()
                }
                val treeWalk = TreeWalk(repo)
                treeWalk.addTree(commit.tree)
                treeWalk.isRecursive = true
                while (treeWalk.next()) {
                    if (treeWalk.pathString == filePath && treeWalk.getObjectId(0) == objectId) {
                        val loader = repo.open(treeWalk.getObjectId(0))
                        val bytes = loader.bytes
                        return String(bytes)
                    }
                }
            }
        }
        return null
    }

    private fun formatContentAsDiff(content: String, prefix: String): String {
        val lines = content.split("\n")
        val formattedLines = lines.map { "$prefix$it" }
        return formattedLines.joinToString("\n")
    }

    fun getDiffContent(commit: RevCommit, filePath: String, withHeader: Boolean = false): String? {
        val out = ByteArrayOutputStream()
        val formatter = DiffFormatter(out)
        formatter.setRepository(repo)
        val parentTree = if (commit.parentCount > 0) commit.getParent(0)?.tree else null
        val newTree = commit.tree
        if (parentTree == null) {
            // This is the initial commit, so we return the content of the file at the given path
            val content = getFileContent(commit, filePath)
            val header = "@@ -0,0 +1,${content?.lines()?.size} @@\n"
            return header + formatContentAsDiff(content!!, "+")
        } else {
            val oldTreeIter = CanonicalTreeParser().apply { reset(repo.newObjectReader(), parentTree.id) }
            val newTreeIter = CanonicalTreeParser().apply { reset(repo.newObjectReader(), newTree.id) }
            try {
                val diffs = formatter.scan(oldTreeIter, newTreeIter)
                for (entry in diffs) {
                    if (entry.oldPath == filePath || entry.newPath == filePath) {
                        formatter.format(entry)
                        val diffContent = out.toString()
                        if (withHeader) {
                            return diffContent
                        } else {
                            val startIndex = diffContent.indexOf("@@ ")
                            return diffContent.substring(startIndex)
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                formatter.close()
            }
        }
        return null
    }
}