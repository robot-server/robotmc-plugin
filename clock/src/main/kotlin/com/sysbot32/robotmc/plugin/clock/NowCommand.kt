package com.sysbot32.robotmc.plugin.clock

import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import java.time.OffsetDateTime

class NowCommand : BukkitCommand(
    "now", "현재 시각을 표시합니다.", "/now", listOf(),
) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        val now = OffsetDateTime.now()
        sender.sendMessage("현재 시각은 $now 입니다.")
        return true
    }
}
