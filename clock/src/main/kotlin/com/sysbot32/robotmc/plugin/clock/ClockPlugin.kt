package com.sysbot32.robotmc.plugin.clock

import org.bukkit.plugin.java.JavaPlugin

class ClockPlugin : JavaPlugin() {
    override fun onEnable() {
        server.commandMap.register(name.lowercase(), NowCommand())
        ClockRunnable().runTaskTimerAsynchronously(this, 0, 20)
    }

    override fun onDisable() {
        server.scheduler.cancelTasks(this)
    }
}
