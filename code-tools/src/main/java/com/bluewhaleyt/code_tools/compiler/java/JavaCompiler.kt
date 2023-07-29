package com.bluewhaleyt.code_tools.compiler.java

import org.eclipse.jdt.core.JavaCore
import org.eclipse.jdt.core.compiler.IProblem
import org.eclipse.jdt.core.dom.AST
import org.eclipse.jdt.core.dom.ASTParser
import org.eclipse.jdt.core.dom.CompilationUnit
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions


class JavaCompiler(
    private val code: String,
) {
    fun getCompilationError(): String? {
        val parser = ASTParser.newParser(AST.JLS10)
        parser.setSource(code.toCharArray())

        // Set compiler options
        val options: MutableMap<String, String> = JavaCore.getOptions()
        options[CompilerOptions.OPTION_ReportMissingSerialVersion] = CompilerOptions.IGNORE
        options[CompilerOptions.OPTION_ReportUnusedImport] = CompilerOptions.IGNORE
        options[CompilerOptions.OPTION_Compliance] = JavaCore.VERSION_1_8
        options[CompilerOptions.OPTION_Source] = JavaCore.VERSION_1_8
        options[CompilerOptions.OPTION_TargetPlatform] = JavaCore.VERSION_1_8

        // Compile the Java code string

        val compilationUnit: CompilationUnit = parser.createAST(null) as CompilationUnit
        // Get compilation errors, if any
        val problems: Array<IProblem> = compilationUnit.problems
        for (problem in problems) {
            return problem.message
        }
        return null
    }
}