package com.bluewhaleyt.whaleutils.activity.playground.compiler

import android.os.Bundle
import com.bluewhaleyt.code_tools.CodeLanguages
import com.bluewhaleyt.code_tools.compiler.java.JavaCompiler
import com.bluewhaleyt.code_tools.parser.JavaParser
import com.bluewhaleyt.code_tools.parser.ParserUtils
import com.bluewhaleyt.whaleutils.activity.playground.PlaygroundActivity
import com.bluewhaleyt.whaleutils.databinding.ActivityPlaygroundCompilerBinding
import org.eclipse.jdt.core.dom.AST
import org.eclipse.jdt.core.dom.ASTParser
import org.eclipse.jdt.core.dom.ASTVisitor
import org.eclipse.jdt.core.dom.CompilationUnit
import org.eclipse.jdt.core.dom.Javadoc
import org.eclipse.jdt.core.dom.MethodDeclaration
import org.eclipse.jdt.core.dom.SingleVariableDeclaration
import java.time.LocalDateTime


class CompilerPlaygroundActivity : PlaygroundActivity() {
    private lateinit var binding: ActivityPlaygroundCompilerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaygroundCompilerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBinding(binding)

        binding.btnFormatCode.setOnClickListener { formatCode(binding.etCode.text.toString()) }
        binding.btnCompileCode.setOnClickListener { compileCode(binding.etCode.text.toString()) }
    }

    private fun setResult(str: String, self: Boolean = false) {
        if (self) binding.etCode.setText(str) else binding.tvResultText.text = str
    }

    private fun formatCode(code: String) {
//        val formatter = FormatUtils(CodeLanguages.JAVA, code)
//        formatter.format()
//        setResult(formatter.getFormattedCode(), true)

//        val parser = ParserUtils(CodeLanguages.JAVA, code)
//        val javaParser = parser.parser as JavaParser
//        val formatted = javaParser.insertJavaDoc(code, "main")
//        setResult(formatted, true)
    }

    private fun compileCode(code: String) {
//        val compiler = JavaCompiler(code)
//        setResult(compiler.getCompilationError().toString())
    }
}