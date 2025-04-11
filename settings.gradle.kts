plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "robotmc-plugin"

include("core")
include("basic-income")
include("challenge")
include("clock")
include("coordinate")
include("teleport")
