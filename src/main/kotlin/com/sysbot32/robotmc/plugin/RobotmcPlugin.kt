package com.sysbot32.robotmc.plugin

import com.sysbot32.robotmc.plugin.clock.ClockRunnable
import org.bukkit.plugin.java.JavaPlugin

class RobotmcPlugin : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic
        ClockRunnable().runTaskTimerAsynchronously(this, 0, 20)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
