package com.sysbot32.robotmc.plugin.core

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World

fun Double.format(digits: Int): String = "%.${digits}f".format(this)

fun World.getOtherEnvironment(environment: World.Environment): World? {
    if (this.environment == environment) {
        return this
    }
    val worldName = when (this.environment) {
        World.Environment.NORMAL -> this.name
        World.Environment.NETHER, World.Environment.THE_END -> this.name.removeSuffix(environment.name.lowercase())
        else -> throw IllegalArgumentException("Unknown environment ${this.environment}")
    }
    val suffix = when (environment) {
        World.Environment.NORMAL -> ""
        World.Environment.NETHER, World.Environment.THE_END -> environment.name.lowercase()
        else -> throw IllegalArgumentException("Unknown environment ${this.environment}")
    }
    return Bukkit.getWorld(worldName + "_" + suffix)
}

fun Location.toOverworld(): Location {
    return when (this.world.environment) {
        World.Environment.NORMAL -> this
        World.Environment.NETHER -> Location(
            this.world.getOtherEnvironment(World.Environment.NETHER),
            this.x.toInt() * 8.0,
            this.y,
            this.z.toInt() * 8.0,
        )

        else -> throw IllegalArgumentException("Overworld 또는 Nether만 가능합니다.")
    }
}

fun Location.toNether(): Location {
    return when (this.world.environment) {
        World.Environment.NORMAL -> Location(
            this.world.getOtherEnvironment(World.Environment.NETHER),
            this.x.toInt().floorDiv(8).toDouble(),
            this.y,
            this.z.toInt().floorDiv(8).toDouble(),
        )

        World.Environment.NETHER -> this

        else -> throw IllegalArgumentException("Overworld 또는 Nether만 가능합니다.")
    }
}

fun Location.toSimpleString(): String {
    return "${this.world.environment} ${this.x.format(3)} / ${this.y.format(5)} / ${this.z.format(3)}"
}
