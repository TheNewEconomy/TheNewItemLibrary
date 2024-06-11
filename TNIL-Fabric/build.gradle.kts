plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1" apply true
    id("fabric-loom") version "1.6-SNAPSHOT" apply true
    id("maven-publish") apply true
}

group = property("maven_group")!!
version = property("tnil_version")!!

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")
    api(project(":TNIL-Core"))
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

    shadowJar {
        archiveFileName = "TNIL-Fabric-${project.version}-shadow.jar"

        dependencies {
            exclude("net.fabricmc:.*")
            include(dependency(":TNIL-Core"))
            exclude("/mappings/*")
        }
    }

    remapJar {
        dependsOn(shadowJar)
        addNestedDependencies = true
        archiveFileName = "TNIL-Fabric-${project.version}.jar"
    }
}

java {
    //withSourcesJar()
}

description = "The New Item Library Fabric"