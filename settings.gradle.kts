pluginManagement {
    val kotlinVersion: String by settings
    val idofrontVersion: String by settings

    repositories {
        gradlePluginPortal()
        maven("https://repo.mineinabyss.com/releases")
        maven("https://repo.papermc.io/repository/maven-public/")
    }

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
        id("org.jetbrains.dokka") version kotlinVersion
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("com.mineinabyss.conventions"))
                useVersion(idofrontVersion)
        }
    }
}

dependencyResolutionManagement {
    val idofrontVersion: String by settings

    repositories {
        maven("https://repo.mineinabyss.com/releases")
    }

    versionCatalogs {
        create("libs").from("com.mineinabyss:catalog:$idofrontVersion")
        create("gaddonlibs").from(files("gradle/gaddonlibs.versions.toml"))
    }
}

rootProject.name = "geary-addons"

include(
    "geary-commons",
    "geary-commons-papermc",
//    "geary-platform",
)
