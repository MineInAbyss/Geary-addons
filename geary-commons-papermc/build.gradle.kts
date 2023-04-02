val idofrontVersion: String by project

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.mia.kotlin.jvm.get().pluginId)
    id(libs.plugins.mia.papermc.get().pluginId)
    id(libs.plugins.mia.copyjar.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    api(project(":geary-commons"))

    // MineInAbyss platform
    compileOnly(libs.kotlin.stdlib)
    compileOnly(libs.kotlinx.serialization.json)
    compileOnly(libs.kotlinx.serialization.json)
    compileOnly(libs.kotlinx.serialization.kaml)
    compileOnly(libs.kotlinx.coroutines)
    compileOnly(libs.minecraft.mccoroutine)

    // Other plugins
    compileOnly(myLibs.geary.papermc)

    implementation(libs.bundles.idofront.core)
}
