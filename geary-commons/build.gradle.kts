val idofrontVersion: String by project
val gearyVersion: String by project

plugins {
    id("com.mineinabyss.conventions.kotlin")
    id("com.mineinabyss.conventions.publication")
    kotlin("plugin.serialization")
}

dependencies {
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json")

    implementation("com.mineinabyss:idofront:$idofrontVersion")
    compileOnly("com.mineinabyss:geary-core:$gearyVersion")
}
