
import com.github.javaparser.utils.Utils.set
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration

buildscript {
    dependencies {
        classpath("org.jetbrains.dokka:dokka-base:1.8.20")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.8.20")
        classpath("org.jetbrains.dokka:kotlin-as-java-plugin:1.8.20")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    id("org.jetbrains.dokka") version "1.8.20"
    `maven-publish`
}

tasks.dokkaHtmlMultiModule {
    val dokkaDir = rootProject.ext["dokkaDir"]
//    outputDirectory.set(buildDir.resolve("docs"))
    outputDirectory.set(file("docs"))
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        customStyleSheets = listOf(file("${dokkaDir}/css/custom.css"))
    }
}

project.afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.bluewhaleyt.whaleutils"
            }
        }
    }
}

ext {
    set("dokkaDir", "${rootDir}/dokka")

    set("compileSdk", 34)
    set("targetSdk", 33)
    set("minSdk", 26)

    set("javaVersion", JavaVersion.VERSION_11)
    set("jvmTarget", JavaVersion.VERSION_11.toString())
}