package com.sysbot32.robotmc.plugin.coordinate

import com.sysbot32.robotmc.plugin.core.toSimpleString
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player

class CoordinateCommand : BukkitCommand(
    "coordinate", "현재 위치를 공유합니다.", "/coordinate", listOf("coord"),
) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            sender.chat("현재 위치: ${sender.location.toSimpleString()}")
            return true
        }
        return false
    }
}
