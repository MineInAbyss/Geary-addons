plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

tasks.build {
    dependsOn(project("geary-commons-papermc").tasks.build)
}
