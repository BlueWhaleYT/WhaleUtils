@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
//    id("org.jetbrains.dokka")
    `maven-publish`
}

android {
    namespace = "com.bluewhaleyt.whaleutils"
    compileSdk = rootProject.ext["compileSdk"] as Int

    defaultConfig {
        applicationId = "com.bluewhaleyt.whaleutils"
        minSdk = rootProject.ext["minSdk"] as Int
        targetSdk = rootProject.ext["targetSdk"] as Int
        versionCode = 1
        versionName = "0.1.0-alpha04"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"

            excludes += "META-INF/eclipse.inf"
            excludes += "about_files/LICENSE-2.0.txt"
            excludes += "plugin.properties"
            excludes += "plugin.xml"
        }
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":compose-common"))
    implementation(project(":file-management"))
    implementation(project(":git"))
    implementation(project(":network"))
    implementation(project(":resources"))
    implementation(project(":code-tools"))

    //    implementation("com.github.smuyyh:JsonViewer:1.0.7") {
//        exclude(group = "com.android.support")
//    }

    implementation("uk.co.samuelwall:material-tap-target-prompt:3.3.2")
    implementation("com.github.pvarry:android-json-viewer:v1.1")

    implementation("com.github.pedrovgs:lynx:1.1.0") {
        exclude(group = "com.android.support")
    }

    implementation("com.googlecode.java-diff-utils:diffutils:1.3.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}