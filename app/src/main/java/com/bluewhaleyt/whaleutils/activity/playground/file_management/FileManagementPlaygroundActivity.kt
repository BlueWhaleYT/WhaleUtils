package com.bluewhaleyt.whaleutils.activity.playground.file_management

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluewhaleyt.common.animation.AnimationUtils
import com.bluewhaleyt.common.animation.Animations
import com.bluewhaleyt.common.color.getDominantColor
import com.bluewhaleyt.common.common.catchException
import com.bluewhaleyt.common.common.getAttributeColor
import com.bluewhaleyt.common.coroutines.runInBackground
import com.bluewhaleyt.common.coroutines.runOnUI
import com.bluewhaleyt.common.coroutines.withUI
import com.bluewhaleyt.design.widget.recyclerview.adapter.AdapterCallback
import com.bluewhaleyt.design.widget.recyclerview.adapter.CustomAdapter
import com.bluewhaleyt.design.widget.recyclerview.adapter.FilterMode
import com.bluewhaleyt.file_management.core.FileUtils
import com.bluewhaleyt.file_management.core.basicSort
import com.bluewhaleyt.file_management.core.getFileContent
import com.bluewhaleyt.file_management.core.getFileExtension
import com.bluewhaleyt.file_management.core.getParentPath
import com.bluewhaleyt.file_management.saf.SAFUtils
import com.bluewhaleyt.file_management.search.SearchResult
import com.bluewhaleyt.file_management.search.SearchResultAdapter
import com.bluewhaleyt.file_management.search.SearchUtils
import com.bluewhaleyt.materialfileicon.core.FileIconHelper
import com.bluewhaleyt.sora_editor.CustomCompletionAdapter
import com.bluewhaleyt.sora_editor.CustomCompletionLayout
import com.bluewhaleyt.sora_editor.textmate.TextMateUtils
import com.bluewhaleyt.whaleutils.activity.playground.PlaygroundActivity
import com.bluewhaleyt.whaleutils.databinding.ActivityPlaygroundFileManagementBinding
import com.google.android.material.chip.Chip
import com.lazygeniouz.dfc.file.DocumentFileCompat
import io.github.rosemoe.sora.widget.EditorSearcher
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

class FileManagementPlaygroundActivity : PlaygroundActivity() {

    private lateinit var binding: ActivityPlaygroundFileManagementBinding

    private val fileUtils = FileUtils(this)
    private val safUtils = SAFUtils(this)

    private val searchUtils = SearchUtils(this)
    private lateinit var searchResultAdapter: SearchResultAdapter
    private lateinit var searchResults: ArrayList<SearchResult>

    private lateinit var pathOrUri: Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaygroundFileManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBinding(binding)

        if (safUtils.isExternalStorageGranted()) init()
        else safUtils.requestAllFileAccess()
    }

    private fun init() {
        initFileList(fileUtils.externalStoragePath)

        binding.btnGotoParentDir.setOnClickListener {
            initFileList(pathOrUri.toString().getParentPath().toString())
        }

        initCodeEditor()
        runInBackground { TextMateUtils(this, binding.etEditor) }

        initSearchFileList()
        binding.btnGlobalSearch.setOnClickListener {
            catchException {
                performSearch(binding.etGlobalSearch.text.toString())
            }
        }
    }

//    private fun init() {
//        val launcher = saf.registerActivityResultLauncher(this) {
//            setupFileList(it!!)
//        }
//        binding.btnSelectFile.setOnClickListener {
//            val intent = saf.intentOpenDocumentTree
//            launcher.launch(intent)
//        }
//    }

//    private fun setupFileList(uri: Uri) {
//        setLoading(true)
//
//        runInBackground {
//            var list = saf.listFiles()
//            for (file in list) Log.d("list", file.name)
//            withUI {
//                setLoading(false)
//
//                val adapter = CustomAdapter<DocumentFileCompat>(this)
//                val callback = object : AdapterCallback<DocumentFileCompat> {
//                    override fun onCreateView(itemView: View, data: DocumentFileCompat, itemIndex: Int) {
//                        initFileView(itemView, data)
//                    }
//                    override fun onItemClick(itemView: View, data: DocumentFileCompat, itemIndex: Int) {
//                        if (data.isDirectory()) {
//                            // todo
////                            setLoading(true)
////                            runInBackground {
////                                list = saf.listFiles(data.uri)
////                                withUI {
////                                    setLoading(false)
////                                    setupFileList(data.uri)
////                                }
////                            }
//
////                            Log.d("clicked uri", data.uri.toString())
////                            Log.d("uri path", data.uri.toRealFilePath(itemView.context))
////                            Log.d("uri scheme", data.uri.scheme.toString())
//                        }
//                        else showDialog(data.name, data.uri.getFileContent(itemView.context))
//                    }
//                }
//
//                adapter.setCallback(callback)
//                    .setLayoutView(R.layout.layout_list_item_3)
//                binding.rvFile.setAdapter(adapter, list)
//
//                binding.cardFiles.subtitle = uri.toString()
//            }
//        }
//
//
//        Log.d("uri", uri.toString())
//
//    }

    private fun initFileList(pathOrUri: Any) {
        this.pathOrUri = pathOrUri
        setLoading(true)

        runInBackground {
            val list = fileUtils.listFiles(pathOrUri.toString()).basicSort()
            withUI {
                setLoading(false)

                val adapter = CustomAdapter<File>(this)
                val callback = object : AdapterCallback<File> {
                    override fun onCreateView(itemView: View, data: File, itemIndex: Int) {
                        initFileView(adapter, itemView, data)
                    }

                    override fun onItemClick(itemView: View, data: File, itemIndex: Int) {
                        if (data.isDirectory) initFileList(data.path)
                        else {
                            binding.etEditor.setText(data.path.getFileContent())
                            binding.cardCodeEditor.apply {
                                subtitle = data.path
                            }
                            runInBackground {
                                val textmateUtils = TextMateUtils(itemView.context, binding.etEditor)
                                withUI {
                                    textmateUtils.applyLanguage(data.path.getFileExtension().toString())
                                }
                            }
                        }
                    }
                }

                adapter.setCallback(callback)
                    .setLayoutView(com.bluewhaleyt.whaleutils.R.layout.layout_list_item_3)
                    .setFilterable(true)

                binding.rvFile.setAdapter(adapter, list)

                binding.cardFiles.subtitle = pathOrUri.toString()
            }
        }
    }

    class ViewHolder(itemView: View) {
        val tvFilePath: TextView = itemView.findViewById(com.bluewhaleyt.whaleutils.R.id.tv_text_3)
        val tvFileName: TextView = itemView.findViewById(com.bluewhaleyt.whaleutils.R.id.tv_text_1)
        val tv2: TextView = itemView.findViewById(com.bluewhaleyt.whaleutils.R.id.tv_text_2)
        val chipFileIcon: Chip = itemView.findViewById(com.bluewhaleyt.whaleutils.R.id.chip_text_1)
    }

    private fun initFileView(adapter: CustomAdapter<File>, itemView: View, file: Any) {
        val viewHolder = ViewHolder(itemView)
        val context = itemView.context

        when (file) {
            is File -> initRegularFileView(viewHolder, file)
            is DocumentFileCompat -> initSAFFileView(viewHolder, file)
        }

        val anim = AnimationUtils(itemView.context)
        anim.applyAnimation(itemView, Animations.BOUNCE_IN, anim.DURATION_LONG)

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapter.addFilter(FilterMode.SIMPLE, p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun initRegularFileView(viewHolder: ViewHolder, file: File) {
        val fileIconHelper = FileIconHelper(file.path)
        fileIconHelper.isDynamicFolderEnabled = true
        fileIconHelper.isEnvironmentEnabled = true

        val fileColor = if (file.isFile) getAttributeColor("textColor") else getAttributeColor("textColorSecondary")
        val fileIcon = fileIconHelper.fileIcon

        viewHolder.tv2.apply { visibility = View.GONE }
        viewHolder.tvFilePath.apply { text = file.path.replace(fileUtils.externalStoragePath, "..") }
        viewHolder.tvFileName.apply {
            text = file.name
            setTextColor(fileColor)
        }
        viewHolder.chipFileIcon.apply {
            setChipIconResource(fileIcon)
            chipBackgroundColor = ColorStateList.valueOf(this.chipIcon!!.getDominantColor()).withAlpha(50)
            chipStrokeColor = ColorStateList.valueOf(this.chipIcon!!.getDominantColor())
        }
    }

    private fun initSAFFileView(viewHolder: ViewHolder, documentFile: DocumentFileCompat) {

    }

//    private fun initFileView(itemView: View, file: DocumentFileCompat) {
//        val context = itemView.context
//        val tvFilePath = itemView.findViewById<TextView>(R.id.tv_text_3)
//        val tvFileName = itemView.findViewById<TextView>(R.id.tv_text_1)
//        val tv2 = itemView.findViewById<TextView>(R.id.tv_text_2)
//        val chipFileIcon = itemView.findViewById<Chip>(R.id.chip_text_1)
//
//        val color = if (file.isFile()) getAttributeColor("textColor") else getAttributeColor("textColorSecondary")
//        val fileIcon = if (file.isFile()) R.drawable.baseline_insert_drive_file_24 else R.drawable.baseline_folder_24
//
//        val anim = AnimationUtils(context)
//        anim.applyAnimation(itemView, Animations.BOUNCE_IN, anim.DURATION_LONG)
//
//        tvFilePath.apply {
//            text = file.uri.toString()
//        }
//
//        tvFileName.apply {
//            text = file.name
//        }
//
//        chipFileIcon.apply {
//            chipIconTint = ColorStateList.valueOf(color)
//            setChipIconResource(fileIcon)
//        }
//
//        tv2.visibility = View.GONE
//
//    }

//    private fun initFileList(type: Int, uri: Uri? = null) {
//        setLoading(true)
//
//        runInBackground {
//            val list = when (type) {
//                0 -> saf.listExternalStorage()
//                1 -> saf.listFiles(
//                    uri = uri!!,
//                )
//                else -> null
//            }!!
//            val sortedList = list.sortedWith(compareBy(
//                { if (it.isDirectory()) 0 else 1 },
//                { it.name.lowercase(Locale.ROOT) }
//            ))
//
//            withUI {
//                setLoading(false)
//
//                val adapter = CustomAdapter<DocumentFileCompat>(applicationContext)
//
//                val callback = object : AdapterCallback<DocumentFileCompat> {
//                    override fun onCreateView(itemView: View, data: DocumentFileCompat, itemIndex: Int) {
//                        val anim = AnimationUtils(itemView.context)
//                        anim.applyAnimation(itemView, Animations.BOUNCE_IN, anim.DURATION_LONG)
//
//                        val tvFileName = itemView.findViewById<TextView>(R.id.tv_text_1)
//                        val tv2 = itemView.findViewById<TextView>(R.id.tv_text_2)
//                        val tvFilePath = itemView.findViewById<TextView>(R.id.tv_text_3)
//                        val chipFileIcon = itemView.findViewById<Chip>(R.id.chip_text_1)
//
//                        tvFileName.text = data.name
//                        tvFilePath.text = data.uri.toRealFilePath(itemView.context, type == 1)
//                        tv2.visibility = View.GONE
//
//                        val color = if (data.isFile()) getAttributeColor("textColor")
//                        else getAttributeColor("textColorSecondary")
//                        tvFileName.setTextColor(color)
//                        chipFileIcon.chipIconTint = ColorStateList.valueOf(color)
//
//                        chipFileIcon.setChipIconResource(if (data.isFile()) R.drawable.baseline_insert_drive_file_24
//                        else R.drawable.baseline_folder_24)
//                    }
//                    override fun onItemClick(itemView: View, data: DocumentFileCompat, itemIndex: Int) {
//                        if (data.isFile()) showDialog(data.name, data.uri.getFileContent(applicationContext))
//                        else {
//                            adapter.updateData(data)
//                            initFileList(1, data.uri)
//                        }
//                    }
//                }
//
//                adapter.setLayoutView(R.layout.layout_list_item_3)
//                    .setCallback(callback)
//
//                binding.rvFile.setAdapter(adapter, sortedList)
//
//                binding.cardFiles.subtitle = uri?.toRealFilePath(applicationContext, type == 1) ?: saf.externalStoragePath
//            }
//        }
//
//        Log.d("uri", uri.toString())
//    }



    private fun setLoading(loading: Boolean) {
        binding.pbLoading.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun setLoading2(loading: Boolean) {
        binding.pbLoading2.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun initCodeEditor() {
        val editor = binding.etEditor
        editor.props.stickyScroll = true
        editor.getEditorCompletion().setAdapter(CustomCompletionAdapter())
        editor.getEditorCompletion().setLayout(CustomCompletionLayout())

        val textMateUtils = TextMateUtils(this, editor)
        textMateUtils.applyLanguage()
    }

    private fun initSearchFileList() {
        searchResultAdapter = SearchResultAdapter()
        binding.rvGlobalFile.layoutManager = LinearLayoutManager(this)
        binding.rvGlobalFile.adapter = searchResultAdapter

        searchResultAdapter.setOnItemClickListener(object : SearchResultAdapter.OnItemClickListener{
            override fun onItemClick(view: View?, result: SearchResult?) {
                binding.etEditor.setText(result!!.filePath.getFileContent())
                binding.cardCodeEditor.subtitle = result.filePath
                runInBackground {
                    val textmateUtils = TextMateUtils(view!!.context, binding.etEditor)
                    withUI {
                        textmateUtils.applyLanguage(result.filePath.getFileExtension().toString())
                    }
                }
                binding.etEditor.searcher.search(
                    binding.etGlobalSearch.text.toString(),
                    EditorSearcher.SearchOptions(true, false)
                )
                binding.etEditor.jumpToLine(result.lineNumber - 1)
            }

        })
    }

    private fun updateResultText(numFiles: Int, numResults: Int) {
        binding.cardGlobalSearchFiles.subtitle = "Found $numResults results, involving $numFiles files"
    }

    private fun performSearch(query: String) {
        runOnUI {
            binding.cardGlobalSearchFiles.subtitle = ""
            if (query != "") {
                setLoading(true)
                searchResults = ArrayList()
                val fileCounts: MutableMap<String, Int> = HashMap()
                runOnUI {
                    searchResultAdapter.setSearchResults(searchResults)
                    searchResultAdapter.notifyDataSetChanged()
                }
                runInBackground {
                    val dir = File(pathOrUri.toString())
                    searchFiles(dir, query, searchResults, fileCounts)
                    runOnUI {
                        searchResultAdapter.notifyDataSetChanged()
                        updateResultText(fileCounts.size, searchResults.size)
                        setLoading(false)
                    }
                }
            }
        }
    }

    private fun searchFiles(dir: File, query: String, results: MutableList<SearchResult>, fileCounts: MutableMap<String, Int>) {
        val files = dir.listFiles()
        if (files != null) {
            for (file in files) {
                if (file.isFile) {
                    try {
                        val reader = BufferedReader(FileReader(file))
                        var line: String?
                        var content = ""
                        var lineNumber = 1
                        while (reader.readLine().also { line = it } != null) {
                            content += line + "\n"
                            if (line!!.lowercase().contains(query.lowercase())) {
                                val startIndex = line!!.trim().lowercase().indexOf(query.lowercase())
                                val endIndex = startIndex + query.length
                                val highlightedLine = SpannableString(line!!.trim())
                                val color = getAttributeColor("colorPrimary")
                                highlightedLine.setSpan(ForegroundColorSpan(color), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                                highlightedLine.setSpan(BackgroundColorSpan(ColorUtils.setAlphaComponent(color, 40)), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                                val searchResult = SearchResult(
                                    file.absolutePath,
                                    file.name,
                                    content,
                                    highlightedLine,
                                    lineNumber
                                )
                                var count = 0
                                if (fileCounts.containsKey(file.absolutePath)) {
                                    count = fileCounts[file.absolutePath]!!
                                }
                                fileCounts[file.absolutePath] = count + 1
                                runOnUiThread {
                                    results.add(searchResult)
                                    searchResultAdapter.notifyItemInserted(results.indexOf(searchResult))
                                    updateResultText(fileCounts.size, results.size)
                                }
                            }
                            lineNumber++
                        }
                        reader.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else if (file.isDirectory) {
                    searchFiles(file, query, results, fileCounts)
                }
            }
        }
    }

}