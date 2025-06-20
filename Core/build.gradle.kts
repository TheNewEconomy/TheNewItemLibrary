/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {

    `java-library`
    `maven-publish`
    id("java")
    id("com.gradleup.shadow") version "9.0.0-beta16" apply true
}

java {

    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
}

tasks {
    compileJava {
        sourceCompatibility = "17"
        targetCompatibility = "17"

    }

    jar {
        dependsOn(shadowJar)
        archiveFileName = "original-TNIL-Core-${project.version}.jar"
    }

    shadowJar {
        archiveFileName = "TNIL-Core-${project.version}.jar"
    }
}

description = "The New Item Library Core"