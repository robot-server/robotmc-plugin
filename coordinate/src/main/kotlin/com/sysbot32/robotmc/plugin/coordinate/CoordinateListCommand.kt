package com.sysbot32.robotmc.plugin.coordinate

import com.sysbot32.robotmc.plugin.core.coordinates.Coordinate
import com.sysbot32.robotmc.plugin.core.toSimpleString
import io.github.oshai.kotlinlogging.KotlinLogging
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player

private val log = KotlinLogging.logger { }

class CoordinateListCommand : BukkitCommand(
    "coordinate-list", "저장된 위치 목록을 표시합니다.", "/coordinate-list", listOf(),
) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        try {
            if (sender !is Player) {
                return false
            }
            Bukkit.getServer().servicesManager.getRegistration(Coordinate::class.java)?.provider?.run {
                val list = load(sender)
                sender.sendMessage(list.joinToString("\n") { it.toSimpleString() })
                return true
            }
            return false
        } catch (e: Exception) {
            log.error(e) { e.message }
            return false
        }
    }
}
