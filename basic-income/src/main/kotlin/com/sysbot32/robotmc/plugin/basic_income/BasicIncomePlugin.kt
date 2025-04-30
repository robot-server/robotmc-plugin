package com.sysbot32.robotmc.plugin.basic_income

import com.sysbot32.robotmc.plugin.core.ReloadCommand
import org.bukkit.plugin.java.JavaPlugin
import kotlin.time.Duration.Companion.minutes

class BasicIncomePlugin : JavaPlugin() {
    override fun onEnable() {
        config.options().copyDefaults(true)
        saveConfig()
        BasicIncomeRunnable(this).runTaskTimerAsynchronously(this, 0, 20 * 20.minutes.inWholeSeconds)
        server.commandMap.register(name.lowercase(), ReloadCommand(this))
    }
}
