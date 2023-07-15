import org.jetbrains.dokka.Platform
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import java.net.URL

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    id("org.jetbrains.dokka")
    `maven-publish`
}

android {
    namespace = "com.bluewhaleyt.file_management"
    compileSdk = rootProject.ext["compileSdk"] as Int

    defaultConfig {
        minSdk = rootProject.ext["minSdk"] as Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = rootProject.ext["javaVersion"] as JavaVersion
        targetCompatibility = rootProject.ext["javaVersion"] as JavaVersion
    }
    kotlinOptions {
        jvmTarget = rootProject.ext["jvmTarget"] as String
    }
    tasks.dokkaHtmlPartial.configure {
        val dokkaDir = rootProject.ext["dokkaDir"]
        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            dokkaSourceSets.configureEach {
                includes.from(
                    "$dokkaDir/modules/file-management/FileManagement.md"
                )
            }
            customStyleSheets = listOf(file("${dokkaDir}/css/custom.css"))
            separateInheritedMembers = true
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.BlueWhaleYT.WhaleUtils"
                artifactId = "file-management"
                version = "1.0.0"
            }
        }
    }
}

dependencies {
    implementation(project(":common"))

    api(libs.androidx.documentfile)
}