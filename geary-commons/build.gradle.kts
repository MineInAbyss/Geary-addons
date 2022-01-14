import Com_mineinabyss_conventions_platform_gradle.Deps

val idofrontVersion: String by project
val gearyVersion: String by project

plugins {
    id("geary-addons.conventions")
    id("com.mineinabyss.conventions.publication")
    kotlin("plugin.serialization")
}

dependencies {
    compileOnly(Deps.kotlinx.serialization.json)

//    implementation("com.mineinabyss:idofront:$idofrontVersion")
    compileOnly("com.mineinabyss:geary-core")
    compileOnly("com.mineinabyss:geary-prefabs")
}
