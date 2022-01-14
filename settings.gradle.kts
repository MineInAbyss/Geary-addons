pluginManagement {
    val kotlinVersion: String by settings
    val idofrontConventions: String by settings

    repositories {
        gradlePluginPortal()
        mavenLocal()
        maven("https://repo.mineinabyss.com/releases")
    }

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("com.mineinabyss.conventions"))
                useVersion(idofrontConventions)
        }
    }
}

rootProject.name = "geary-addons"

include(
    "geary-commons",
    "geary-commons-papermc",
    "geary-platform",
)

includeBuild("conventions")
