plugins {
    `java-platform`
    `maven-publish`
}

val gearyVersion: String by project
val lootyVersion: String by project
val mobzyVersion: String by project

dependencies {
    constraints {
        api("com.mineinabyss:looty:$lootyVersion")
        api("com.mineinabyss:mobzy:$mobzyVersion")
        api("com.mineinabyss:geary-platform-papermc:$gearyVersion")
        api("com.mineinabyss:geary-prefabs:$gearyVersion")
        api("com.mineinabyss:geary-core:$gearyVersion")
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
