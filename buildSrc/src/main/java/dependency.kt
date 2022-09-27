import Versions.NAV_VERSION
import Versions.PAGING_VERSION
import Versions.ROOM_VERSION

object Versions {
    const val NAV_VERSION = "2.4.0-alpha10"
    const val ROOM_VERSION = "2.4.3"
    const val PAGING_VERSION = "3.1.1"

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