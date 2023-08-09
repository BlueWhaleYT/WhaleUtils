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
    namespace = "com.bluewhaleyt.network"
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
//                includes.from(
//                    "$dokkaDir/modules/git/Git.md"
//                )
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
                artifactId = "network"
                version = "1.0.0"
            }
        }
    }
}

dependencies {
    api(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    api("com.squareup.okhttp3:okhttp")
}