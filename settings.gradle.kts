pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.mineinabyss.com/releases")
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencyResolutionManagement {
    val idofrontVersion: String by settings

    repositories {
        maven("https://repo.mineinabyss.com/releases")
    }

    versionCatalogs {
        create("libs").from("com.mineinabyss:catalog:$idofrontVersion")
        create("myLibs").from(files("gradle/myLibs.versions.toml"))
    }
}

rootProject.name = "geary-addons"

include(
    "geary-commons",
    "geary-papermc-bindings",
    "geary-papermc-plugin",
)
