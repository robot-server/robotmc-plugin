package com.sysbot32.robotmc.plugin.coordinate

import com.sysbot32.robotmc.plugin.core.coordinates.Coordinate
import org.bukkit.Location
import org.bukkit.OfflinePlayer
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class CoordinateImpl : Coordinate {
    override fun save(player: OfflinePlayer, name: String, location: Location) {
        transaction {
            CoordinateTable.insert {
                it[this.name] = name
                it[uuid] = player.uniqueId
                it[world] = location.world.name
                it[environment] = location.world.environment.name
                it[x] = location.x
                it[y] = location.y
                it[z] = location.z
            }
        }
    }
}
