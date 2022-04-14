val idofrontVersion: String by project

plugins {
    id("com.mineinabyss.conventions.kotlin")
    id("com.mineinabyss.conventions.papermc")
    id("com.mineinabyss.conventions.copyjar")
    id("com.mineinabyss.conventions.publication")
    kotlin("plugin.serialization")
}

repositories {
    maven("https://repo.mineinabyss.com/releases")
    maven("https://jitpack.io")
    mavenLocal()
}

dependencies {
    // MineInAbyss platform
    compileOnly(idoLibs.kotlin.stdlib)
    compileOnly(idoLibs.kotlinx.serialization.json)
    compileOnly(idoLibs.kotlinx.serialization.json)
    compileOnly(idoLibs.kotlinx.serialization.kaml)
    compileOnly(idoLibs.kotlinx.coroutines)
    compileOnly(idoLibs.minecraft.skedule)

    // Other plugins
    compileOnly(libs.looty)
    compileOnly(libs.mobzy)

    api(project(":geary-commons"))
    compileOnly(libs.geary.papermc.core)

    implementation("com.mineinabyss:idofront:$idofrontVersion")
}
