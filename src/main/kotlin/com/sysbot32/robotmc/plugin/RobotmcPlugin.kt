package com.sysbot32.robotmc.plugin

import com.sysbot32.robotmc.plugin.challenge.VehicleMoveChallenge
import com.sysbot32.robotmc.plugin.clock.ClockRunnable
import com.sysbot32.robotmc.plugin.coordinate.CoordinateCommand
import com.sysbot32.robotmc.plugin.coordinate.CoordinateListener
import net.kyori.adventure.text.Component
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot

class RobotmcPlugin : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic
        ClockRunnable().runTaskTimerAsynchronously(this, 0, 20)
        server.commandMap.register(name.lowercase(), CoordinateCommand())
        server.pluginManager.registerEvents(CoordinateListener(), this)
        server.scoreboardManager.mainScoreboard.registerNewObjective(
            VehicleMoveChallenge.OBJECTIVE_NAME,
            Criteria.DUMMY,
            Component.text(VehicleMoveChallenge.DISPLAY_NAME),
        ).apply {
            displaySlot = DisplaySlot.SIDEBAR
        }
        server.pluginManager.registerEvents(VehicleMoveChallenge(), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
