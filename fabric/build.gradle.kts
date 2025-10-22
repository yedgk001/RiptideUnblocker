import net.fabricmc.loom.task.RemapJarTask

plugins {
    id("fabric-loom") version "1.11-SNAPSHOT"
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.10")
    mappings("net.fabricmc:yarn:1.21.10+build.1")
    modImplementation("net.fabricmc:fabric-loader:0.17.2")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.134.1+1.21.10")
    implementation(project(":shared"))
}

afterEvaluate {
    tasks.withType<RemapJarTask> {
        archiveFileName.set("${rootProject.name}-${project.name}-${project.version}.jar")
    }
}


