@file:Suppress("UnstableApiUsage")
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
}

val  localProperties = Properties()
localProperties.load(project.rootProject.file("local.properties").inputStream())

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.batro"
        namespace = "com.batro"
        minSdk = 23
        targetSdk = 33
        versionCode = 5
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }
    signingConfigs {
        create("release"){
            keyAlias = localProperties.getProperty("keystore.key")
            keyPassword = localProperties.getProperty("keystore.keyPassword")
            storeFile = file(localProperties.getProperty("keystore.storeFile"))
            storePassword = localProperties.getProperty("keystore.password")
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs["release"]
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),"proguard-rules.pro")
        }
        debug {
//            applicationIdSuffix = ".dev"
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                    project.buildDir.absolutePath + "/compose_metrics"
        )
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                    project.buildDir.absolutePath + "/compose_metrics"
        )
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val composeBom = platform("androidx.compose:compose-bom:2022.12.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose")
    implementation("androidx.compose.runtime:runtime-livedata")

    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")

    implementation("androidx.navigation:navigation-compose:2.5.3")

//    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

    implementation("com.airbnb.android:mavericks:2.7.0")
    implementation("com.airbnb.android:mavericks-compose:2.7.0")

    implementation("io.insert-koin:koin-core:3.3.2")
    implementation("io.insert-koin:koin-android:3.3.2")


    implementation("io.coil-kt:coil-compose:2.2.2")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.6")

    implementation(platform("com.google.firebase:firebase-bom:30.3.1"))
    implementation("com.google.firebase:firebase-firestore-ktx")

    implementation("org.web3j:core:4.8.7-android")

    implementation("androidx.room:room-runtime:2.5.0")
    annotationProcessor("androidx.room:room-compiler:2.5.0")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:2.5.0")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.5.0")

    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.13.4")

}
