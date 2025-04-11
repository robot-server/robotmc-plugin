package com.sysbot32.robotmc.plugin.coordinate

import org.bukkit.plugin.java.JavaPlugin

class CoordinatePlugin : JavaPlugin() {
    override fun onEnable() {
        server.commandMap.register(name.lowercase(), CoordinateCommand())
        server.pluginManager.registerEvents(CoordinateListener(), this)
    }
}
