plugins {
    id("fabric-loom") version "1.10-SNAPSHOT"
    id("maven-publish")
    id("java")
    id("com.gradleup.shadow") version "9.0.0-beta16" apply true
}

group = property("maven_group")!!
version = property("mod_version")!!

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.

    //adventure lib
    // for development builds
    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/") {
        name = "sonatype-oss-snapshots1"
        mavenContent { snapshotsOnly() }
    }
    // for releases
    mavenCentral()
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:1.21.5")
    mappings("net.fabricmc:yarn:1.21.5+build.1:v2")
    modImplementation("net.fabricmc:fabric-loader:0.16.14")
    modImplementation(include("net.kyori:adventure-platform-fabric:6.4.0")!!) // for Minecraft 1.21.5
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.127.0+1.21.5")
    shadow(project(":TNIL-Core"))
}

java {

    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks {
    compileJava {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }

    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(getProperties())
            expand(mutableMapOf("version" to project.version))
        }
    }

    java {
        // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
        // if it is present.
        // If you remove this line, sources will not be generated.
        withSourcesJar()
    }

    jar {
        dependsOn(shadowJar)
        archiveFileName = "original-TNIL-Fabric-${project.version}.jar"
    }

    shadowJar {
        archiveFileName = "TNIL-Fabric-${project.version}.jar"

        isZip64 = true

        configurations = listOf(project.configurations.shadow.get())
    }

    publishing {

        // select the repositories you want to publish to
        repositories {
            // uncomment to publish to the local maven
            // mavenLocal()
        }
    }
}
