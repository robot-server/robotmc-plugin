package com.sysbot32.robotmc.plugin.challenge

import net.kyori.adventure.text.Component
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.Criteria

class ChallengePlugin : JavaPlugin() {
    override fun onEnable() {
        server.scoreboardManager.mainScoreboard.registerNewObjective(
            VehicleMoveChallenge.OBJECTIVE_NAME,
            Criteria.DUMMY,
            Component.text(VehicleMoveChallenge.DISPLAY_NAME),
        )
        server.pluginManager.registerEvents(VehicleMoveChallenge(), this)
    }

    override fun onDisable() {
        server.scoreboardManager.mainScoreboard.getObjective(VehicleMoveChallenge.OBJECTIVE_NAME)?.unregister()
    }
}
