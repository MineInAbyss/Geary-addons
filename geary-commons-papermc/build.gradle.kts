import Com_mineinabyss_conventions_platform_gradle.Deps

val serverVersion: String by project
val gearyVersion: String by project
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
    mavenLocal()
}

dependencies {
    compileOnly(Deps.kotlinx.serialization.json)
    compileOnly("com.mineinabyss:looty:0.3.28")
    compileOnly("com.mineinabyss:mobzy:0.11.32")

    api(project(":geary-commons"))
    compileOnly("com.mineinabyss:geary-platform-papermc:$gearyVersion")
    compileOnly("com.mineinabyss:idofront-nms:$idofrontVersion")
}
