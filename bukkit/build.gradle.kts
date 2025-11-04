dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
    implementation(project("shared"))
}

tasks.withType<Jar> {
    archiveFileName.set("${rootProject.name}-${project.name}-${project.version}.jar")
}
