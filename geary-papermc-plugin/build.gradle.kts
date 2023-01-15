val idofrontVersion: String by project

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.mia.copyjar.get().pluginId)
    id(libs.plugins.mia.kotlin.asProvider().get().pluginId)
    id(libs.plugins.mia.publication.get().pluginId)
    id(libs.plugins.mia.papermc.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    compileOnly(myLibs.geary.core)
    compileOnly(myLibs.geary.prefabs)
    compileOnly(myLibs.geary.serialization)
    compileOnly(myLibs.geary.autoscan)
//    api(project(":geary-autoscan"))
//    api(project(":geary-uuid"))

    // Plugins
    compileOnly(myLibs.plugman)

    // MineInAbyss platform
    compileOnly(libs.kotlin.stdlib)
    compileOnly(libs.kotlinx.serialization.json)
    compileOnly(libs.kotlinx.serialization.kaml)
    compileOnly(libs.kotlinx.coroutines)
    compileOnly(libs.minecraft.mccoroutine)

    implementation(libs.bundles.idofront.core)
}
