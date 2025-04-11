package com.sysbot32.robotmc.plugin.coordinate

import com.sysbot32.robotmc.plugin.core.toSimpleString
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class CoordinateListener : Listener {
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        event.player.sendMessage("죽은 위치: ${event.player.location.toSimpleString()}")
    }
}
