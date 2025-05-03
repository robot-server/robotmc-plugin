package com.sysbot32.robotmc.plugin.coordinate

import com.sysbot32.robotmc.plugin.core.coordinates.Coordinate
import com.sysbot32.robotmc.plugin.core.toSimpleString
import io.github.oshai.kotlinlogging.KotlinLogging
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player

private val log = KotlinLogging.logger { }

class CoordinateCommand : BukkitCommand(
    "coordinate", "현재 위치를 공유합니다.", "/coordinate [name]", listOf("coord"),
) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        try {
            if (sender !is Player) {
                return false
            }
            val name = if (args.isNotEmpty()) args.first() else "현재 위치"
            sender.chat("${name}: ${sender.location.toSimpleString()}")
            Bukkit.getServer().servicesManager.getRegistration(Coordinate::class.java)?.provider?.run {
                save(sender, name, sender.location)
            }
            return true
        } catch (e: Exception) {
            log.error(e) { e.message }
            return false
        }
    }
}
