plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kspPlugin)
    alias(libs.plugins.hiltPlugin)
    //alias(libs.plugins.crashlytics)
    //alias(libs.plugins.googleServices)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.weatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weatherapp"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {


    //AndroidX
    implementation(libs.android.activity)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.viewModel)
    implementation(libs.app.compat)
    implementation(libs.core.ktx)
    implementation(libs.core.animation)
    implementation(libs.splash.screen)
    implementation(libs.multiDex)
    implementation(libs.securityCrypto)
    implementation(libs.annotation)

    implementation(libs.kotlinVersion)

    //compose
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.compiler)
    implementation(libs.compose.material3)
    implementation(libs.compose.constraintlayout)
    implementation(libs.compose.navigation)
    implementation(libs.compose.adaptiveNavigation)
    implementation(libs.compose.paging)
    implementation(libs.compose.materialicon)
    implementation(libs.compose.runtime)
    implementation(libs.activityCompose)
    implementation(platform(libs.composeBom))
    implementation(libs.lifecycle.runtime.compose)
    androidTestImplementation(platform(libs.composeBom))
    debugImplementation(libs.composeUiTooling)

    //Google-play-core
    implementation(libs.play.update)
    implementation(libs.play.review)
    implementation(libs.location)
    implementation(libs.analytics)

    //Coroutines
    implementation(libs.coroutines)
    implementation(libs.coroutines.core)


    //Dagger-Hilt
    implementation(libs.dagger.hilt)
    implementation(libs.dagger.hilt.navigation)
    debugImplementation(libs.ui.test.manifest)
    ksp(libs.dagger.hilt.compiler)
    ksp(libs.dagger.hilt.androidx)

    //Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.auth)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.kotlinx.serialization)

    //Gson
    implementation(libs.gson)

    //Coil
    implementation(libs.coil)

    //Lottie-Animation
    implementation(libs.lottie.compose)

    //Room-db
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    implementation(libs.work.manager)

    //DataStore
    implementation(libs.data.store.core)

    //test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.composeBom))

    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.rules)
    androidTestImplementation(libs.rules)
    androidTestImplementation(libs.runner)
    androidTestImplementation(libs.mockito)
    androidTestImplementation(libs.mockitoCore)
    androidTestImplementation(libs.composeUiTesting)
}