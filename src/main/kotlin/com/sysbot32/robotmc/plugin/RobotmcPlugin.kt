package com.sysbot32.robotmc.plugin

import com.sysbot32.robotmc.plugin.clock.ClockRunnable
import com.sysbot32.robotmc.plugin.coordinate.CoordinateCommand
import org.bukkit.plugin.java.JavaPlugin

class RobotmcPlugin : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic
        ClockRunnable().runTaskTimerAsynchronously(this, 0, 20)
        server.commandMap.register(name.lowercase(), CoordinateCommand())
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
