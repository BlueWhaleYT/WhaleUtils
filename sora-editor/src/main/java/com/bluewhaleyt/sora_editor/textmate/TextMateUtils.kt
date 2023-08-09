package com.bluewhaleyt.sora_editor.textmate

import android.content.Context
import com.bluewhaleyt.common.common.isInDarkMode
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.langs.textmate.registry.model.ThemeModel
import io.github.rosemoe.sora.langs.textmate.registry.provider.AssetsFileResolver
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import org.eclipse.tm4e.core.registry.IThemeSource

private val themeDark = "dark/material-palenight.json"
private val themeLight = "light/material-lighter.json"

class TextMateUtils(
    private val context: Context,
    private val editor: CodeEditor,
    private val theme: String = if (context.isInDarkMode()) themeDark else themeLight,
    private val themes: Array<String> = arrayOf(themeDark, themeLight),
    private val themeDir: String = "textmate/themes/",
    private val langDir: String = "textmate/languages/",
    private val langBasePath: String = "languages.json",
) {

//    private val colorSchemeBuilder: TextMateColorSchemeBuilder? = null
    private var lang: String = ""

    init {
        applyLanguage()
    }

//    fun getColorSchemeBuilder(): TextMateColorSchemeBuilder? {
//        return colorSchemeBuilder
//    }

    fun applyLanguage(language: String = "txt") {
        lang = when (language) {
            "css", "sass", "less", "scss" -> "source.css"
            "kt", "kts" -> "source.kotlin"
            "java" -> "source.java"
            "js" -> "source.js"
            "json" -> "source.json"
            "php" -> "source.php"
            "py" -> "source.python"

            "log" -> "text.log"
            "txt" -> "text.plain"
            "xml" -> "text.xml"
            "html", "htm" -> "text.html.basic"
            "md", "mdx" -> "text.html.markdown"
            else -> ""
        }

        loadDefaultThemes()
        loadDefaultLanguages()

        ensureTextmateTheme()

        applyThemes()
        if (lang != "") applyLanguages()
        else editor.rerunAnalysis()

    }

    @Throws(Exception::class)
    private fun loadDefaultThemes() {
        FileProviderRegistry.getInstance().addFileProvider(AssetsFileResolver(context.assets))
        val themeRegistry = ThemeRegistry.getInstance()
        var i = 0
        while (i < themes.size) {
            val path: String = themeDir + themes[i]
            themeRegistry.loadTheme(
                ThemeModel(
                    IThemeSource.fromInputStream(
                        FileProviderRegistry.getInstance().tryGetInputStream(path), path, null
                    ), path
                )
            )
            i++
        }
    }

    private fun loadDefaultLanguages() {
        GrammarRegistry.getInstance().loadGrammars(langDir + langBasePath)
    }

    @Throws(Exception::class)
    private fun ensureTextmateTheme() {
        var editorColorScheme: EditorColorScheme? = editor.colorScheme
        if (editorColorScheme !is TextMateColorScheme) {
            editorColorScheme = TextMateColorScheme.create(ThemeRegistry.getInstance())
            editor.colorScheme = editorColorScheme
        }
    }

    private fun applyThemes() {
        ThemeRegistry.getInstance().setTheme(themeDir + theme)
    }

    private fun applyLanguages() {
        ensureTextmateTheme()
        val language: TextMateLanguage
        val editorLanguage = editor.editorLanguage

        if (editorLanguage is TextMateLanguage) {
            language = editorLanguage
            language.updateLanguage(lang)
        } else {
            language = TextMateLanguage.create(lang, true)
        }
        editor.setEditorLanguage(language)
    }
}