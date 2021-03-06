plugins {
    `java-platform`
    `maven-publish`
}

val runNumber = System.getenv("GITHUB_RUN_NUMBER") ?: "DEV"

version = "$version.$runNumber"

val gearyVersion: String by project
val lootyVersion: String by project
val mobzyVersion: String by project

dependencies {
    constraints {
        api("com.mineinabyss:looty:$lootyVersion")
        api("com.mineinabyss:mobzy:$mobzyVersion")
        api("com.mineinabyss:geary-papermc-core:$gearyVersion")
        api("com.mineinabyss:geary-prefabs:$gearyVersion")
        api("com.mineinabyss:geary-core:$gearyVersion")
        api("com.mineinabyss:geary-autoscan:$gearyVersion")
        rootProject.subprojects.forEach {
            api("com.mineinabyss:${it.name}:${it.version}")
        }
    }
}

publishing {
    repositories {
        maven("https://repo.mineinabyss.com/releases") {
            credentials {
                username = project.findProperty("mineinabyssMavenUsername") as String?
                password = project.findProperty("mineinabyssMavenPassword") as String?
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["javaPlatform"])
        }
    }
}
