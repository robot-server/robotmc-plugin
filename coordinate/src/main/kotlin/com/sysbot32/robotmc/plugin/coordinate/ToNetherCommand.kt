package com.sysbot32.robotmc.plugin.coordinate

import com.sysbot32.robotmc.plugin.core.toNether
import com.sysbot32.robotmc.plugin.core.toSimpleString
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player

class ToNetherCommand : BukkitCommand(
    "to-nether", "현재 위치를 Nether의 위치로 변환합니다.", "/to-nether", listOf(),
) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            return false
        }
        sender.sendMessage(sender.location.toNether().toSimpleString())
        return true
    }
}
