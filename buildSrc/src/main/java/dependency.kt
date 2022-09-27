import Versions.GLIDE_VERSION
import Versions.NAV_VERSION
import Versions.PAGING_VERSION
import Versions.ROOM_VERSION

object Versions {
    const val NAV_VERSION = "2.4.0-alpha10"
    const val ROOM_VERSION = "2.4.3"
    const val PAGING_VERSION = "3.1.1"
    const val GLIDE_VERSION = "4.11.0"

}

object NavComponent {
    const val NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:$NAV_VERSION"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:$NAV_VERSION"
    const val NAVIGATION_DYNAMIC_FEATURES_FRAGMENT =
        "androidx.navigation:navigation-dynamic-features-fragment:$NAV_VERSION"
    const val NAVIGATION_TESTING = "androidx.navigation:navigation-testing:$NAV_VERSION"
    const val NAVIGATION_COMPOSE = "androidx.navigation:navigation-compose:2.4.0-alpha10"
}

object AndroidX {
    const val MATERIAL = "androidx.compose.material:material:1.0.0-rc02"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.1.0"
    const val APP_COMPAT = "androidx.appcompat:appcompat:1.3.1"
    const val LEGACY = "androidx.legacy:legacy-support-v4:1.0.0"
    const val LIFECYCLE_VIEW_MODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1"
    const val LIFECYCLE_LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:2.4.1"
    const val LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.0"
    const val ACTIVITY = "androidx.activity:activity-ktx:1.3.1"
    const val FRAGMENT = "androidx.fragment:fragment-ktx:1.3.6"
    const val DATASTORE = "androidx.datastore:datastore-preferences:1.0.0"
}

object Paging {
    const val PAGING_RUNTIME = "androidx.paging:paging-runtime:$PAGING_VERSION"
    const val PAGING_COMMON = "androidx.paging:paging-common-ktx:$PAGING_VERSION"
}

object Glide {
    const val GLIDE = "com.github.bumptech.glide:glide:$GLIDE_VERSION"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:$GLIDE_VERSION"
    const val GLIDE_BLUR =  "jp.wasabeef:glide-transformations:4.3.0"
}

object DaggerHilt {
    const val DAGGER_HILT = "com.google.dagger:hilt-android:2.38.1"
    const val DAGGER_HILT_COMPILER = "com.google.dagger:hilt-android-compiler:2.38.1"
    const val DAGGER_HILT_VIEW_MODEL = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
    const val DAGGER_HILT_ANDROIDX_COMPILER = "androidx.hilt:hilt-compiler:1.0.0"
}

object Retrofit {
    const val RETROFIT = "com.squareup.retrofit2:retrofit:2.9.0"
    const val CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:2.9.0"
    const val CONVERTER_JAXB = "com.squareup.retrofit2:converter-jaxb:2.9.0"
}

object OkHttp {
    const val OKHTTP = "com.squareup.okhttp3:okhttp:4.9.1"
    const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:4.9.1"
}

object Coroutines {
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2"
}

object Room {

    const val ROOM_RUNTIME = "androidx.room:room-runtime:$ROOM_VERSION"
    const val ROOM_COMPILER = "androidx.room:room-compiler:$ROOM_VERSION"
    const val ROOM_COMMON = "androidx.room:room-common:$ROOM_VERSION"
    const val ROOM_KTX = "androidx.room:room-ktx:$ROOM_VERSION"
}

object SpinKit {
    const val SPINKIT = "com.github.ybq:Android-SpinKit:1.4.0"
}

object CircleProgressbar {
    const val CIRCLE = "com.dinuscxj:circleprogressbar:1.3.6"
}

object CircleImageView {
    const val CIRCLE = "io.github.informramiz:simplecircleimageview:1.2.0"
}

object Youtube{
    const val PLAYER = "com.pierfrancescosoffritti.androidyoutubeplayer:core:10.0.5"
}

object Shimmer{
    const val SHIMMER = "com.facebook.shimmer:shimmer:0.5.0"
}