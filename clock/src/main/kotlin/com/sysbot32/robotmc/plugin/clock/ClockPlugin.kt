package com.sysbot32.robotmc.plugin.clock

import org.bukkit.plugin.java.JavaPlugin

class ClockPlugin : JavaPlugin() {
    override fun onEnable() {
        ClockRunnable().runTaskTimerAsynchronously(this, 0, 20)
    }
}
