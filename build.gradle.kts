plugins {
    id("java")
}

allprojects {
    group = "io.yedgk.riptideunblocker"
    version = "1.1.0"
}

subprojects {
    apply(plugin = "java")

    java {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://maven.fabricmc.net/")
    }
    tasks.withType<ProcessResources> {
        expand("project" to project)
    }
}