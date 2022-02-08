import Com_mineinabyss_conventions_platform_gradle.Deps

val idofrontVersion: String by project
val gearyVersion: String by project

plugins {
    id("geary-addons.conventions")
    id("com.mineinabyss.conventions.publication")
    kotlin("plugin.serialization")
}

repositories {
    maven("https://repo.mineinabyss.com/releases")
}

dependencies {
    compileOnly(Deps.kotlinx.serialization.json)

//    implementation("com.mineinabyss:idofront:$idofrontVersion")
    compileOnly("com.mineinabyss:geary-core")
    compileOnly("com.mineinabyss:geary-autoscan")
    compileOnly("com.mineinabyss:geary-prefabs")
}
