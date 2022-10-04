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
    compileOnly(libs.kotlinx.serialization.json)

    compileOnly(gaddonLibs.geary.core)
    compileOnly(gaddonLibs.geary.prefabs)

    implementation(libs.bundles.idofront.core)
}
