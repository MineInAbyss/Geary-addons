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

    compileOnly(gaddonlibs.geary.core)
    compileOnly(gaddonlibs.geary.prefabs)

    implementation(libs.idofront.core)
}
