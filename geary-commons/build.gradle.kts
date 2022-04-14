val idofrontVersion: String by project
val gearyVersion: String by project

plugins {
    id("com.mineinabyss.conventions.kotlin")
    id("com.mineinabyss.conventions.publication")
    kotlin("plugin.serialization")
}

repositories {
    maven("https://repo.mineinabyss.com/releases")
}

dependencies {
    compileOnly(idoLibs.kotlinx.serialization.json)

    implementation("com.mineinabyss:idofront:$idofrontVersion")
    compileOnly(libs.geary.core)
//    compileOnly("com.mineinabyss:geary-autoscan")
    compileOnly(libs.geary.prefabs)
}
