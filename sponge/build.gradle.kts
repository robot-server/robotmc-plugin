import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
    kotlin("jvm") version "2.1.20"
    `java-library`
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.spongepowered.gradle.plugin") version "2.2.0"
}

sponge {
    apiVersion("12.0.0")
    license("All-Rights-Reserved")
    loader {
        name(PluginLoaders.JAVA_PLAIN)
        version("1.0")
    }
    plugin("robotmc-plugin") {
        displayName("robotmc-plugin")
        entrypoint("com.sysbot32.robotmc.plugin.RobotmcPlugin")
        description("My plugin description")
        links {
            // homepage("https://spongepowered.org")
            // source("https://spongepowered.org/source")
            // issues("https://spongepowered.org/issues")
        }
        dependency("spongeapi") {
            loadOrder(PluginDependency.LoadOrder.AFTER)
            optional(false)
        }
    }
}

val javaTarget = 21 // Sponge targets a minimum of Java 21
kotlin {
    jvmToolchain(javaTarget)
}

tasks.build {
    dependsOn("shadowJar")
}

// Make sure all tasks which produce archives (jar, sources jar, javadoc jar, etc) produce more consistent output
tasks.withType<AbstractArchiveTask>().configureEach {
    isReproducibleFileOrder = true
    isPreserveFileTimestamps = false
}
