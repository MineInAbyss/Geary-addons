import Com_mineinabyss_conventions_platform_gradle.Deps

val useNMS: String? by project
val idofrontVersion: String by project

plugins {
    id("com.mineinabyss.conventions.kotlin")
}

repositories {
    mavenCentral()
}

dependencies {
    // MineInAbyss platform
    compileOnly(Deps.kotlin.stdlib)
    compileOnly(platform(project(":geary-addons-platform")))

    if (useNMS.toBoolean())
        implementation("com.mineinabyss:idofront-nms:$idofrontVersion")
    else
        implementation("com.mineinabyss:idofront:$idofrontVersion")
}
