package com.bluewhaleyt.code_tools.parser

import org.eclipse.jdt.core.dom.AST
import org.eclipse.jdt.core.dom.ASTParser
import org.eclipse.jdt.core.dom.ASTVisitor
import org.eclipse.jdt.core.dom.CompilationUnit
import org.eclipse.jdt.core.dom.Javadoc
import org.eclipse.jdt.core.dom.MethodDeclaration
import org.eclipse.jdt.core.dom.SingleVariableDeclaration

class JavaParser : Parser() {

    private lateinit var code: String
    var parser: ASTParser = ASTParser.newParser(AST.JLS10)

    init {
        parser.setKind(ASTParser.K_COMPILATION_UNIT)
        parser.setResolveBindings(true)
        parser.setBindingsRecovery(true)
    }

    @JvmName("setCodeText")
    fun setCode(code: String) {
        this.code = code
    }

    fun insertJavaDoc(code: String, methodName: String): String {
        setCode(code)
        parser.setSource(code.toCharArray())

        val cu: CompilationUnit = parser.createAST(null) as CompilationUnit
        cu.accept(object : ASTVisitor() {
            override fun visit(method: MethodDeclaration): Boolean {
                if (method.name.identifier.equals(methodName)) {
                    val ast: AST = method.ast
                    val javadoc: Javadoc = ast.newJavadoc()
                    // Add a @param tag for each method parameter
                    for (obj in method.parameters()) {
                        val param = obj as SingleVariableDeclaration
                        val paramTag = ast.newTagElement()
                        paramTag.tagName = "@param"
                        paramTag.fragments().add(ast.newSimpleName(param.name.identifier))
                        paramTag.fragments().add(ast.newTextElement())
                        javadoc.tags().add(paramTag)
                    }
                    method.javadoc = javadoc
                }
                return true
            }
        })
        return cu.toString()
    }
}