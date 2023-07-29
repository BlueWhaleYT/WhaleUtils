package com.bluewhaleyt.code_tools.parser

import com.bluewhaleyt.code_tools.CodeLanguages

class ParserUtils(
    language: CodeLanguages,
    code: String
) {

    var parser : Parser

    init {
        parser = when (language) {
            CodeLanguages.JAVA -> JavaParser()
        }
    }
}