package com.sysbot32.robotmc.plugin.teleport

import com.sysbot32.robotmc.plugin.core.services.Teleport
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin

class TeleportPlugin : JavaPlugin() {
    override fun onEnable() {
        server.commandMap.register(name.lowercase(), TeleportCommand())
        server.servicesManager.register(Teleport::class.java, TeleportImpl(), this, ServicePriority.Normal)
    }

    override fun onDisable() {
        server.servicesManager.unregisterAll(this)
    }
}
