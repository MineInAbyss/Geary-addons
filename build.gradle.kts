val idofrontVersion: String by project

plugins {
    `java-library`
    kotlin("jvm")
    id("com.mineinabyss.conventions.kotlin")
    id("com.mineinabyss.conventions.autoversion")
    id("org.jetbrains.dokka")
}

repositories {
    mavenCentral()
}

tasks {
    build {
        dependsOn(project("geary-commons-papermc").tasks.build)
    }
}

subprojects {
    tasks {
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        }
    }
}

allprojects {
    val runNumber: String? = System.getenv("GITHUB_RUN_NUMBER")
    if (runNumber != null) version = "$version.$runNumber"
}
