object Deps {

    object Plugins {
        const val application = "com.android.application"
        const val library = "com.android.library"
        const val kotlinAndroid = "org.jetbrains.kotlin.android"
        const val kotlinKapt = "org.jetbrains.kotlin.kapt"
        const val hilt = "com.google.dagger.hilt.android"
    }

    object ClassPath {
        object Version {
            const val gradle = "7.4.1"
            const val kotlin = "1.8.10"
            const val hilt = "2.45"
        }
        const val androidGradlePlugin = "com.android.tools.build:gradle:${Version.gradle}"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
        const val daggerHiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Version.hilt}"
    }

    object Core{
        object Version {
            const val coreKtx = "1.10.0"
            const val appCompat = "1.6.1"
            const val material = "1.8.0"
            const val constrain = "2.1.4"
        }
        const val coreKts = "androidx.core:core-ktx:${Version.coreKtx}"
        const val appCompat = "androidx.appcompat:appcompat:${Version.appCompat}"
        const val androidMaterial = "com.google.android.material:material:${Version.material}"
        const val constaraiLayout = "androidx.constraintlayout:constraintlayout:${Version.constrain}"
    }

    object Retrofit {
        private const val version = "2.9.0"

        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val converterGson = "com.squareup.retrofit2:converter-gson:$version"
    }

    object Lifecycle{
        private const val version = "2.5.1"

        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
    }

    object Hilt {
        private const val version = "2.45"

        const val hilt = "com.google.dagger:hilt-android:$version"
        const val annotation = "com.google.dagger:hilt-compiler:$version"
    }



    object Navigation {
        private const val version = "2.5.3"

        const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
        const val ui = "androidx.navigation:navigation-ui-ktx:$version"
    }

    object ThirdParty{
        object Version{
            const val circular = "3.1.0"
            const val sceleton = "1.1.3"
            const val picasso = "2.71828"
        }

        const val picasso = "com.squareup.picasso:picasso:${Version.picasso}"
        const val sceleton = "com.github.skydoves:androidveil:${Version.sceleton}"
        const val circular = "de.hdodenhof:circleimageview:${Version.circular}"
    }

    object Test {
        object Version {
            const val junit = "1.1.1"
            const val roboeletric = "4.6.1"
            const val mockk = "1.12.4"
            const val okHttp3MockWebServer = "4.9.2"
            const val esspreso = "3.5.1"
        }
        const val junit = "junit:junit:${Version.junit}"
        const val androidJunit = "androidx.test.ext:junit:${Version.junit}"
        const val roboeletric = "org.robolectric:robolectric:${Version.roboeletric}"
        const val mockk = "io.mockk:mockk:${Version.mockk}"
        const val okHttp3MockWebServer = "com.squareup.okhttp3:mockwebserver:${Version.okHttp3MockWebServer}"
        const val esspresso = "androidx.test.espresso:espresso-core:${Version.esspreso}"
    }
}