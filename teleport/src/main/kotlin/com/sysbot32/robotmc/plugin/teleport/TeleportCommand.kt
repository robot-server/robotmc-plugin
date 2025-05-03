package com.sysbot32.robotmc.plugin.teleport

import com.sysbot32.robotmc.plugin.core.format
import com.sysbot32.robotmc.plugin.core.toSimpleString
import io.github.oshai.kotlinlogging.KotlinLogging
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player
import kotlin.math.max

private val log = KotlinLogging.logger { }

class TeleportCommand : BukkitCommand(
    "teleport", "다른 위치로 순간이동합니다.", "/teleport <location>", listOf("tp", "taxi"),
) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        try {
            if (args.size != 3) {
                return false
            }
            if (sender !is Player) {
                return false
            }
            val destination = Location(
                sender.location.world,
                args[0].toDouble(), args[1].toDouble(), args[2].toDouble()
            )
            return this.teleport(sender, destination)
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
        super.tabComplete(sender, alias, args, location)
        if (args.size <= 3 && sender is Player) {
            return listOf(
                sender.location.x.format(3),
                sender.location.y.format(6),
                sender.location.z.format(3),
            )
        }
        return listOf()
    }

    private fun calculatePrice(source: Location, destination: Location): Double {
        val minPrice = 100.0
        val distance = source.distance(destination)
        return max(minPrice, distance).format(2).toDouble()
    }

    private fun teleport(player: Player, destination: Location): Boolean {
        val economy = Bukkit.getServer().servicesManager.getRegistration(Economy::class.java)?.provider ?: return false
        while (!destination.block.isEmpty) { // 생매장 방지
            destination.y++
        }
        val price = this.calculatePrice(player.location, destination)
        if (!economy.has(player, price)) {
            player.sendMessage("잔액이 부족합니다! 필요 금액: $price")
            return false
        }
        player.sendMessage("${player.location.toSimpleString()} -> ${destination.toSimpleString()}: $price")
        economy.withdrawPlayer(player, price)
        return player.teleport(destination)
    }
}
