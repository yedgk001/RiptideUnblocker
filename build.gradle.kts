allprojects {
    group = "io.yedgk.riptideunblocker"
    version = "1.1.0"
}

subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://maven.fabricmc.net/")
    }
    tasks.withType<ProcessResources> {
        expand("project" to project)
    }
}