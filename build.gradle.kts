import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    kotlin("jvm") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "me.loidsemus"
version = "1.0.0"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/public/")
    maven("https://repo.aikar.co/content/groups/aikar/")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compileOnly("org.spigotmc:spigot-api:1.16.1-R0.1-SNAPSHOT")
    implementation("co.aikar:idb-core:1.0.0-SNAPSHOT")
    implementation("com.zaxxer:HikariCP:3.4.5")
    implementation("me.loidsemus:configlib:2.2.0") {
        exclude("org.yaml", "snakeyaml")
    }
}

tasks {
    shadowJar {
        val base = "${project.group}.${rootProject.name}.libs."

        relocate("co.aikar.idb", base + "idb")
        relocate("de.exlll.configlib", base + "configlib")

        archiveFileName.set(project.name + ".jar")
        minimize()
    }

    processResources {
        filter<ReplaceTokens>("tokens" to mapOf("version" to project.version))
    }

    build {
        dependsOn(shadowJar)
    }
}