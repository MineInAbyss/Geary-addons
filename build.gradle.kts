val idofrontVersion: String by project

plugins {
    `java-library`
    kotlin("jvm")
    id("com.mineinabyss.conventions.kotlin")
    id("org.jetbrains.dokka")
}

repositories {
    mavenCentral()
}

tasks{
    build {
        dependsOn(project("geary-commons-papermc").tasks.build)
    }
}

subprojects {
    tasks {
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf(
                    "-Xcontext-receivers",
                )
            }
        }
    }
}
