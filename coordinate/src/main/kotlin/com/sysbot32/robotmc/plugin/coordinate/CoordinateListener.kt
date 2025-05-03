package com.sysbot32.robotmc.plugin.coordinate

import com.sysbot32.robotmc.plugin.core.coordinates.Coordinate
import com.sysbot32.robotmc.plugin.core.toSimpleString
import io.github.oshai.kotlinlogging.KotlinLogging
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

private val log = KotlinLogging.logger { }

class CoordinateListener : Listener {
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        try {
            event.player.sendMessage("죽은 위치: ${event.player.location.toSimpleString()}")
            Bukkit.getServer().servicesManager.getRegistration(Coordinate::class.java)?.provider?.run {
                save(event.player, "죽은 위치", event.player.location)
            }
            log.info { "${event.player} 죽은 위치: ${event.player.location.toSimpleString()}" }
        } catch (e: Exception) {
            log.error(e) { e.message }
        }
    }
}
