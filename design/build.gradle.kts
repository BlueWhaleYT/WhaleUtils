@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    `maven-publish`
}

android {
    namespace = "com.bluewhaleyt.design"
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
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.BlueWhaleYT.WhaleUtils"
                artifactId = "design"
                version = "1.0.0"
            }
        }
    }
}

dependencies {
    implementation(kotlin("reflect"))

    implementation(project(":common"))
    implementation("com.android.databinding:viewbinding:8.1.0")
}