package com.sysbot32.robotmc.plugin.coordinate

import com.sysbot32.robotmc.plugin.core.toOverworld
import com.sysbot32.robotmc.plugin.core.toSimpleString
import io.github.oshai.kotlinlogging.KotlinLogging
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player

private val log = KotlinLogging.logger { }

class ToOverworldCommand : BukkitCommand(
    "to-overworld", "현재 위치를 Overworld의 위치로 변환합니다.", "/to-overworld", listOf(),
) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        try {
            if (sender !is Player) {
                return false
            }
            sender.sendMessage(sender.location.toOverworld().toSimpleString())
            return true
        } catch (e: Exception) {
            log.error(e) { e.message }
            return false
        }
    }
}
