package com.sysbot32.robotmc.plugin.core.coordinates

import org.bukkit.Location
import org.bukkit.OfflinePlayer

interface Coordinate {
    fun save(player: OfflinePlayer, name: String, location: Location)
    fun load(player: OfflinePlayer): List<Location>
}
