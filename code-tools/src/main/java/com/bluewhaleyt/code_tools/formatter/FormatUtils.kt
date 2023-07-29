package com.bluewhaleyt.code_tools.formatter

import com.bluewhaleyt.code_tools.CodeLanguages
import org.eclipse.jdt.core.ToolFactory
import org.eclipse.jdt.core.formatter.CodeFormatter
import org.eclipse.jface.text.BadLocationException
import org.eclipse.jface.text.Document
import org.eclipse.jface.text.IDocument
import org.eclipse.text.edits.MalformedTreeException
import org.eclipse.text.edits.TextEdit

class FormatUtils(
    private val language: CodeLanguages,
    private val code: String
) {

    private lateinit var formattedCode: String

    fun getFormattedCode(): String {
        return formattedCode
    }

    fun format() {
        when (language) {
            CodeLanguages.JAVA -> formatJava()
        }
    }

    private fun formatJava() {
        val codeFormatter: CodeFormatter = ToolFactory.createCodeFormatter(null)
        val textEdit: TextEdit = codeFormatter.format(CodeFormatter.K_COMPILATION_UNIT, code, 0, code.length, 0, null)
        val doc: IDocument = Document(code)
        textEdit.apply(doc)
        formattedCode = doc.get()
    }
}