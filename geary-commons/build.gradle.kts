val idofrontVersion: String by project
val gearyVersion: String by project

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.mia.kotlin.jvm.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    compileOnly(libs.kotlinx.serialization.json)

    compileOnly(gearyLibs.core)
    compileOnly(gearyLibs.prefabs)

//    implementation(libs.idofront.core)
}
