import Com_mineinabyss_conventions_platform_gradle.Deps

val serverVersion: String by project
val gearyVersion: String by project
val lootyVersion: String by project
val mobzyVersion: String by project
val idofrontVersion: String by project

plugins {
    id("geary-addons.conventions")
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
    compileOnly(Deps.kotlin.stdlib)
    compileOnly(Deps.kotlinx.serialization.json)
    compileOnly(Deps.kotlinx.serialization.json)
    compileOnly(Deps.kotlinx.serialization.kaml)
    compileOnly(Deps.kotlinx.coroutines)
    compileOnly(Deps.minecraft.skedule)

    // Other plugins
    compileOnly("com.mineinabyss:looty")
    compileOnly("com.mineinabyss:mobzy")

    api(project(":geary-commons"))
    compileOnly("com.mineinabyss:geary-papermc-core")
}