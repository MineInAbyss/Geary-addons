pluginManagement {
    val kotlinVersion: String by settings
    val idofrontVersion: String by settings

    repositories {
        gradlePluginPortal()
        mavenLocal()
        maven("https://repo.mineinabyss.com/releases")
        maven("https://papermc.io/repo/repository/maven-public/")
    }

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
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
        create("idoLibs") {
            from("com.mineinabyss:catalog:$idofrontVersion")
        }
    }
}

rootProject.name = "geary-addons"

include(
    "geary-commons",
    "geary-commons-papermc",
//    "geary-platform",
)
