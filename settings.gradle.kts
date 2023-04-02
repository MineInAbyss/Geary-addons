pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.mineinabyss.com/releases")
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencyResolutionManagement {
    val idofrontVersion: String by settings
    val gearyVersion: String by settings

    repositories {
        maven("https://repo.mineinabyss.com/releases")
    }

    versionCatalogs {
        create("libs").from("com.mineinabyss:catalog:$idofrontVersion")
        create("myLibs").from(files("gradle/myLibs.versions.toml"))
        create("gearyLibs").from("com.mineinabyss:geary-catalog:$gearyVersion")
    }
}

rootProject.name = "geary-addons"

include(
    "geary-commons",
    "geary-commons-papermc",
)
