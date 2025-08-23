package com.sysbot32.robotmc.plugin.teleport

import com.sysbot32.robotmc.plugin.core.format
import com.sysbot32.robotmc.plugin.core.services.Teleport
import io.github.oshai.kotlinlogging.KotlinLogging
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player

private val log = KotlinLogging.logger { }

class TeleportCommand : BukkitCommand(
    "teleport", "다른 위치로 순간이동합니다.", "/teleport <location>", listOf("tp", "taxi"),
) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        try {
            if (args.size != 1 && args.size != 3) {
                return false
            }
            if (sender !is Player) {
                return false
            }
            Bukkit.getServicesManager().getRegistration(Teleport::class.java)?.provider?.run {
                if (args.size == 1) {
                    val destination = Bukkit.getPlayer(args.first())
                    if (destination == null) {
                        sender.sendMessage("${args.first()} 플레이어를 찾을 수 없습니다.")
                        return false
                    }
                    return teleport(sender, destination)
                } else {
                    val destination = Location(
                        sender.location.world,
                        args[0].toDouble(), args[1].toDouble(), args[2].toDouble()
                    )
                    return teleport(sender, destination)
                }
            }
            return false
        } catch (e: Exception) {
            log.error(e) { e.message }
            return false
        }
    }

    override fun tabComplete(
        sender: CommandSender,
        alias: String,
        args: Array<out String>,
        location: Location?
    ): List<String?> {
        val matchedPlayer = super.tabComplete(sender, alias, args, location)
        if (sender !is Player) {
            return listOf()
        }
        val coordinates = listOf(
            sender.location.x.format(3),
            sender.location.y.format(6),
            sender.location.z.format(3),
        )
        if (args.size == 1) {
            return matchedPlayer + coordinates
        }
        if (args.size <= 3) {
            return coordinates
        }
        return listOf()
    }
}
