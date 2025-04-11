package com.sysbot32.robotmc.plugin.core

import org.bukkit.Location

fun Double.format(digits: Int): String = "%.${digits}f".format(this)

fun Location.toSimpleString(): String {
    return "${this.world.environment} ${this.x.format(3)} / ${this.y.format(5)} / ${this.z.format(3)}"
}
