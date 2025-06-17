plugins {
    id("fabric-loom") version "1.10-SNAPSHOT"
    id("maven-publish")
    id("java")
}

group = property("maven_group")!!
version = property("mod_version")!!

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:1.21.5")
    mappings("net.fabricmc:yarn:1.21.5+build.1:v2")
    modImplementation("net.fabricmc:fabric-loader:0.16.14")

    modImplementation("net.fabricmc.fabric-api:fabric-api:0.127.0+1.21.5")
    implementation(project(":TNIL-Core"))
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
    }

    publishing {

        // select the repositories you want to publish to
        repositories {
            // uncomment to publish to the local maven
            // mavenLocal()
        }
    }
}
