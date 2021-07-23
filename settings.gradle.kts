pluginManagement {
    val kotlinVersion: String by settings
    val miaConventionsVersion: String by settings

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
                useVersion(miaConventionsVersion)
        }
    }
}

rootProject.name = "geary-addons"

include(
    "geary-commons",
    "geary-commons-papermc"
)
