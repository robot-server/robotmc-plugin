plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "robotmc-plugin"

include("core")
include("clock")
include("coordinate")
include("economy")
include("teleport")
