package com.bluewhaleyt.whaleutils.activity.playground.git

import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Filter
import java.io.File

class FileSimpleListAdapter(context: Context, resource: Int, private val fileList: List<File>) :
    ArrayAdapter<File>(context, resource, fileList) {

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val suggestionList = mutableListOf<File>()

                if (constraint != null) {
                    // Get the parent directory entered by the user
                    val parentDir = File(constraint.toString()).parentFile

                    // If the user input matches the parent directory exactly, add it to the suggestion list
                    if (parentDir?.path == constraint.toString()) {
                        suggestionList.add(parentDir)
                    }

                    // Filter the list of files and directories to only include those that start with the parent directory
                    fileList.filterTo(suggestionList) {
                        // Get the file's parent directory
                        val fileParentDir = it.parentFile

                        // Check if the file is a directory and its parent directory matches the parent directory entered by the user
                        val isDirectory = it.isDirectory
                        val isParentDirMatch = fileParentDir?.path == parentDir?.path

                        // Check if the file's path starts with the parent directory and it is either a directory or has a file extension
                        val pathStartsWithParentDir = it.path.startsWith(constraint.toString())
                        val isDirectoryOrHasExtension = isDirectory || it.extension.isNotEmpty()

                        // Add the file to the suggestion list if it meets the filtering criteria
                        isParentDirMatch && pathStartsWithParentDir && isDirectoryOrHasExtension
                    }
                }

                filterResults.values = suggestionList
                filterResults.count = suggestionList.size
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    clear()
                    addAll(results.values as List<File>)
                    notifyDataSetChanged()
                }
            }

            override fun convertResultToString(resultValue: Any?): CharSequence {
                return (resultValue as File).path
            }
        }
    }
}