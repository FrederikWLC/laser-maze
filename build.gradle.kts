/*
 * This file was generated manually
 */

import org.gradle.api.tasks.SourceSetContainer


plugins {
    application
}

application {
    mainClass.set("GameLauncher")
}


subprojects {

    repositories {
        mavenCentral()
    }

    // This ensures the dependency handler is available
    configurations.matching { it.name.startsWith("testImplementation") }

    afterEvaluate {
        dependencies {
            "implementation"("org.apache.commons:commons-text:1.10.0")
            "implementation"("com.fasterxml.jackson.core:jackson-databind:2.15.2")
            "implementation"("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.2")
            "testImplementation"("io.cucumber:cucumber-java:7.21.1")
            "testImplementation"("io.cucumber:cucumber-junit:7.21.1")
            "testImplementation"("io.cucumber:cucumber-junit-platform-engine:7.21.1")
            "testImplementation"("org.junit.jupiter:junit-jupiter:5.10.0")
            "testImplementation"("org.junit.platform:junit-platform-suite:1.10.0")
        }
    }

}