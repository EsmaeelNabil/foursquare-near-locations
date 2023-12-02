plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint") version "12.0.2"
    id("com.google.secrets_gradle_plugin") version "0.6"
}

secrets {
    // Optionally specify a different file name containing your secrets.
    // The plugin defaults to "local.properties"
    propertiesFileName = "secrets.properties"
}

android {

    compileSdk = 31

    defaultConfig {
        applicationId = "com.esmaeel.challenge"
        minSdk = 23
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        getByName("debug") {
            // you can use this with different values inside buildTypes.
            isDebuggable = true
        }

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kotlinOptions {
        // We have to add the explicit cast before accessing the options itself.
        val options = this
        options.jvmTarget = "1.8"
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        dataBinding = true
    }

    bundle {
        language {
            // We want to be able to switch the locale at runtime using AppLocale!
            enableSplit = false
        }
    }

    packagingOptions {
        exclude("META-INF/LICENSE.md")
        exclude("META-INF/LICENSE-notice.md")
    }
}

dependencies {


    implementation(Kotlin.stdlib.jdk8)
    implementation(AndroidX.core.ktx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.activity.ktx)
    implementation(AndroidX.activity)
    implementation(AndroidX.fragment.ktx)
    implementation(AndroidX.fragment)
    implementation(Google.android.material)
    implementation(Google.android.playServices.location)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.swipeRefreshLayout)

    implementation(Square.okHttp3.okHttp)
    implementation(Square.okHttp3.loggingInterceptor)
    implementation(Square.retrofit2.retrofit)
    implementation(Square.retrofit2.converter.moshi)

    implementation(JakeWharton.timber)

    implementation(AndroidX.lifecycle.runtime)
    implementation(AndroidX.lifecycle.runtimeKtx)
    implementation(AndroidX.lifecycle.viewModelKtx)


    implementation(AndroidX.recyclerView)
    implementation(KotlinX.coroutines.core)
    implementation(KotlinX.coroutines.android)

    implementation(Libs.coil)
    implementation(Libs.coilSvg)

    //Loading
    implementation(Libs.aviLoader)

    //Location and permission handling
    implementation(Libs.location)
    implementation(Libs.permissions)

    // dimensions
    implementation(Libs.dimensions.sdp)
    implementation(Libs.dimensions.ssp)

    // bundler for intents
    implementation(Libs.bundler)


    // Dagger-Hilt
    implementation(Google.dagger.hilt.android)
    kapt(Google.dagger.hilt.compiler)
    testImplementation(Google.dagger.hilt.android.testing)


    // Testing
    testImplementation(Testing.mockK)
    testImplementation(Libs.test.androidArchTestCore)
    testImplementation(Libs.truth)
    testImplementation(Libs.turbine)
    testImplementation(AndroidX.test.core)
    implementation(AndroidX.test.core)
    testImplementation(KotlinX.coroutines.test)
    testImplementation(Kotlin.test.testng)
    testImplementation(AndroidX.test.coreKtx)
    testImplementation(AndroidX.test.ext.junit)
    testImplementation(AndroidX.test.ext.junitKtx)
    testImplementation(AndroidX.test.runner)
    testImplementation(AndroidX.test.rules)
    androidTestImplementation(Libs.test.kaspresso)
    testImplementation(Libs.test.kotest)
    androidTestImplementation(Libs.test.kotest)


}