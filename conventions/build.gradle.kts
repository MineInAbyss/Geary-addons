import org.gradle.api.Project.GRADLE_PROPERTIES
import java.util.*

// Load properties from root gradle.properties
Properties().apply { load(rootDir.toPath().resolveSibling(GRADLE_PROPERTIES).toFile().inputStream()) }
    .forEach { (key, value) -> project.ext["$key"] = value }

val idofrontConventions: String by project
val kotlinVersion: String by project

plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    maven("https://repo.mineinabyss.com/releases")
}

dependencies {
    implementation("com.mineinabyss.conventions.kotlin:com.mineinabyss.conventions.kotlin.gradle.plugin:$idofrontConventions")
    implementation(kotlin("gradle-plugin", version = kotlinVersion))
}
