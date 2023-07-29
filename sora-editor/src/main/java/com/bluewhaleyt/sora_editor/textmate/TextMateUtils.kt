package com.bluewhaleyt.sora_editor.textmate

import android.content.Context
import com.bluewhaleyt.sora_editor.Languages
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


class TextMateUtils(
    private val context: Context,
    private val editor: CodeEditor,
    private val theme: String,
    private val themes: Array<String>,
    private val themeDir: String,
    private val langDir: String,
    private val langBasePath: String
) {

    private lateinit var lang: String

    fun applyLanguage(languages: Languages) {
        lang = when (languages) {
            Languages.DIFF -> "source.diff"
        }

        loadDefaultThemes();
        loadDefaultLanguages();

        ensureTextmateTheme();

        applyThemes();
        if (lang != "") {
            applyLanguages();
        }

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

    @Throws(java.lang.Exception::class)
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

    @Throws(java.lang.Exception::class)
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