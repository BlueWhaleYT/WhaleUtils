import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    id("org.jetbrains.dokka")
    `maven-publish`
}

android {
    namespace = "com.bluewhaleyt.compose_common"
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    tasks.dokkaHtmlPartial.configure {
        val dokkaDir = rootProject.ext["dokkaDir"]
        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            dokkaSourceSets.configureEach {
                includes.from(
                    "$dokkaDir/common/CommonMain.md"
                )
            }
            customStyleSheets = listOf(file("${dokkaDir}/css/custom.css"))
        }
    }
}

dependencies {
    api(platform(libs.compose.bom))
    api(libs.core.ktx)
    api(libs.lifecycle.runtime.ktx)
    api(libs.activity.compose)
    api(libs.ui)
    api(libs.ui.graphics)
    api(libs.ui.tooling.preview)
    api(libs.material3)
    api(libs.accompanist.systemuicontroller)
    api(libs.androidx.material.icons.extended)
    api(libs.androidx.documentfile)
    api(libs.androidx.navigation.compose)
    api(libs.accompanist.navigation.animation)
}