package com.sysbot32.robotmc.plugin.coordinate

import com.sysbot32.robotmc.plugin.core.toSimpleString
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player

class CoordinateCommand : BukkitCommand(
    "coordinate", "현재 위치를 공유합니다.", "/coordinate [name]", listOf("coord"),
) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val name = if (args.isNotEmpty()) args.first() else "현재 위치"
            sender.chat("${name}: ${sender.location.toSimpleString()}")
            return true
        }
        return false
    }
}
