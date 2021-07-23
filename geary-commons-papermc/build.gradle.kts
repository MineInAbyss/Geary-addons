import com.mineinabyss.mineInAbyss

val serverVersion: String by project
val gearyVersion: String by project
val idofrontVersion: String by project

plugins {
    id("com.mineinabyss.conventions.kotlin")
    id("com.mineinabyss.conventions.papermc")
    id("com.mineinabyss.conventions.publication")
    kotlin("plugin.serialization")
}

repositories {
    mineInAbyss()
    mavenLocal()
}

dependencies {
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json")
    compileOnly("com.mineinabyss:looty:0.3.16")
    compileOnly("com.mineinabyss:mobzy:0.9.25")

    api(project(":geary-commons"))
    compileOnly("com.mineinabyss:geary-platform-papermc:$gearyVersion")
    implementation("com.mineinabyss:idofront-nms:$idofrontVersion")
}
