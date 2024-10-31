plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.assignment3_hansenbillyramades"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.assignment3_hansenbillyramades"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.okhttp)
    implementation (libs.converter.gson)
    implementation (libs.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.glide)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.datastore.preferences)
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("com.tbuonomo:dotsindicator:5.0")
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.legacy.support.v4)
    implementation ("com.facebook.shimmer:shimmer:0.5.0")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("com.google.dagger:hilt-android:2.51.1")
    testImplementation("org.mockito:mockito-core:5.4.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
}