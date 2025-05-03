package com.sysbot32.robotmc.plugin.coordinate

import com.sysbot32.robotmc.plugin.core.coordinates.Coordinate
import io.github.oshai.kotlinlogging.KotlinLogging
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.OfflinePlayer
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

class CoordinateImpl : Coordinate {
    override fun save(player: OfflinePlayer, name: String, location: Location) {
        try {
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
        } catch (e: Exception) {
            log.error(e) { e.message }
        }
    }

    override fun load(player: OfflinePlayer): List<Location> {
        try {
            return transaction {
                CoordinateTable.selectAll()
                    .where { CoordinateTable.uuid eq player.uniqueId }
                    .orderBy(CoordinateTable.createdAt, SortOrder.DESC)
                    .limit(10)
                    .sortedBy { it[CoordinateTable.createdAt] }
                    .map {
                        Location(
                            Bukkit.getWorld(it[CoordinateTable.world]),
                            it[CoordinateTable.x],
                            it[CoordinateTable.y],
                            it[CoordinateTable.z],
                        )
                    }
            }
        } catch (e: Exception) {
            log.error(e) { e.message }
            return listOf()
        }
    }
}
