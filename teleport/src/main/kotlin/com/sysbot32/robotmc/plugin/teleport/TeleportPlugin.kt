package com.sysbot32.robotmc.plugin.teleport

import org.bukkit.plugin.java.JavaPlugin

class TeleportPlugin : JavaPlugin() {
    override fun onEnable() {
        server.commandMap.register(name.lowercase(), TeleportCommand())
    }
}
