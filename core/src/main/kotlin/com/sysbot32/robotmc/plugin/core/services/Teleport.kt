package com.sysbot32.robotmc.plugin.core.services

import org.bukkit.Location
import org.bukkit.entity.Player

interface Teleport {
    fun teleport(player: Player, destination: Location): Boolean
    fun teleport(player: Player, destination: Player): Boolean
}
