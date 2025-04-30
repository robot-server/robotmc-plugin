package com.sysbot32.robotmc.plugin.coordinate

import com.sysbot32.robotmc.plugin.core.coordinates.Coordinate
import com.sysbot32.robotmc.plugin.core.toSimpleString
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player

class CoordinateListCommand : BukkitCommand(
    "coordinate-list", "저장된 위치 목록을 표시합니다.", "/coordinate-list", listOf(),
) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            return false
        }
        Bukkit.getServer().servicesManager.getRegistration(Coordinate::class.java)?.provider?.run {
            val list = load(sender)
            sender.sendMessage(list.joinToString("\n") { it.toSimpleString() })
            return true
        }
        return false
    }
}
