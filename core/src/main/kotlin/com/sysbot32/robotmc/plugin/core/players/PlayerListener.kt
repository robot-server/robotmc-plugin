package com.sysbot32.robotmc.plugin.core.players

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.replace
import org.jetbrains.exposed.sql.transactions.transaction

class PlayerListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        transaction {
            SchemaUtils.create(RobotmcPlayer)
            RobotmcPlayer.replace {
                it[uuid] = event.player.uniqueId
                it[username] = event.player.name
            }
        }
    }
}
