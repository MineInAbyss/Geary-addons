import Com_mineinabyss_conventions_platform_gradle.Deps
import io.github.slimjar.func.slim

val serverVersion: String by project
val gearyVersion: String by project
val lootyVersion: String by project
val mobzyVersion: String by project
val idofrontVersion: String by project

plugins {
    id("com.mineinabyss.conventions.kotlin")
    id("com.mineinabyss.conventions.papermc")
    id("com.mineinabyss.conventions.copyjar")
    id("com.mineinabyss.conventions.slimjar")
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
    compileOnly("com.mineinabyss:looty:$lootyVersion")
    compileOnly("com.mineinabyss:mobzy:$mobzyVersion")

    // From Geary
    slim(Deps.kotlinx.serialization.json)
    slim(Deps.kotlinx.serialization.kaml)
    slim(Deps.kotlinx.coroutines)
    slim(Deps.minecraft.skedule)

    api(project(":geary-commons"))
    compileOnly("com.mineinabyss:geary-platform-papermc:$gearyVersion")

    // Shade
    implementation("com.mineinabyss:idofront-nms:$idofrontVersion")
}
