plugins {
    id (Deps.Plugins.library)
    id (Deps.Plugins.kotlinKapt)
    id (Deps.Plugins.kotlinAndroid)
}

android {
    namespace = Config.dataName
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.MinSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    project(path = ":domain")

    implementation(Deps.Retrofit.retrofit)
    implementation(Deps.Retrofit.converterGson)

    testImplementation(Deps.Test.junit)
    testImplementation(Deps.Test.androidJunit)
    testImplementation(Deps.Test.esspresso)
}