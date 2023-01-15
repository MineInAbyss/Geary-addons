val idofrontVersion: String by project
val gearyVersion: String by project

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.mia.kotlin.asProvider().get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    compileOnly(libs.kotlinx.serialization.json)

    compileOnly(myLibs.geary.core)
    compileOnly(myLibs.geary.prefabs)

//    implementation(libs.idofront.core)
}
