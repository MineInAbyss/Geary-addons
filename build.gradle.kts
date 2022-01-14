plugins {
    `java-library`
    id("com.mineinabyss.conventions.kotlin")
    id("org.jetbrains.dokka") version "1.6.10"
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
}

repositories {
    mavenCentral()
}

tasks.build {
    dependsOn(project("geary-commons-papermc").tasks.build)
}
