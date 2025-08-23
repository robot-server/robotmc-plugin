plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "robotmc-plugin"

include("core")
include("paper")
include("paper:clock")
include("paper:coordinate")
include("paper:economy")
include("paper:teleport")
include("sponge")
