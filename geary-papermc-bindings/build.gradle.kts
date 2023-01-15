val idofrontVersion: String by project

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.mia.kotlin.asProvider().get().pluginId)
    id(libs.plugins.mia.papermc.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
//    id("com.mineinabyss.conventions.copyjar")
//    id("com.mineinabyss.conventions.publication")

}

dependencies {
    compileOnly(myLibs.geary.core)

    // MineInAbyss platform
    compileOnly(libs.kotlin.stdlib)
    compileOnly(libs.kotlinx.serialization.json)
    compileOnly(libs.kotlinx.serialization.json)
    compileOnly(libs.kotlinx.serialization.kaml)
    compileOnly(libs.kotlinx.coroutines)
    compileOnly(libs.minecraft.mccoroutine)

    // Other plugins
    compileOnly(myLibs.looty)
    compileOnly(myLibs.mobzy)

    implementation(libs.bundles.idofront.core)
}
