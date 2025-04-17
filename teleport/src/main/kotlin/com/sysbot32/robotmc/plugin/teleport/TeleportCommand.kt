package com.sysbot32.robotmc.plugin.teleport

import com.sysbot32.robotmc.plugin.core.format
import com.sysbot32.robotmc.plugin.core.toSimpleString
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player
import kotlin.math.max

class TeleportCommand : BukkitCommand(
    "teleport", "다른 위치로 순간이동합니다.", "/teleport <destination: x y z>", listOf("tp", "taxi"),
) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (args.size != 3) {
            return false
        }
        if (sender !is Player) {
            return false
        }
        val economy = Bukkit.getServer().servicesManager.getRegistration(Economy::class.java)?.provider ?: return false
        val destination = Location(sender.location.world, args[0].toDouble(), args[1].toDouble(), args[2].toDouble())
        while (!destination.block.isEmpty) {
            destination.y += 1
        }
        val distance = sender.location.distance(destination)
        val price = max(100.0, distance).format(2).toDouble()
        if (!economy.has(sender, price)) {
            sender.sendMessage("잔액이 부족합니다! 필요 금액: $price")
            return false
        }
        sender.sendMessage("${sender.location.toSimpleString()} -> ${destination.toSimpleString()}: $price")
        economy.withdrawPlayer(sender, price)
        sender.teleport(destination)
        return true
    }
}
