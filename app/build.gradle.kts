
plugins {
    id("buildlogic.java-application-conventions")
}

dependencies {
    // Possibly irrelevant dependencies
    implementation("org.apache.commons:commons-text")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.2")

    // Cucumber and JUnit 5
    testImplementation("io.cucumber:cucumber-java:7.14.0")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.14.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
}

application {
    mainClass.set("GameLauncher")
}

tasks.named<Jar>("jar") {
    manifest {
        attributes["Main-Class"] = "GameLauncher"
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE // or .WARN, .INCLUDE, etc.

    from({
        configurations.runtimeClasspath.get()
            .filter { it.name.endsWith("jar") }
            .map { zipTree(it) }
    })
}
