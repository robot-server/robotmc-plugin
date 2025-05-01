package com.sysbot32.robotmc.plugin.economy

import com.sysbot32.robotmc.plugin.core.format
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player

class BalanceCommand : BukkitCommand(
    "balance", "잔고을 조회합니다.", "/balance", listOf("bal"),
) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            return false
        }
        Bukkit.getServicesManager().getRegistration(Economy::class.java)?.provider?.run {
            val balance = getBalance(sender)
            sender.sendMessage("잔고: ${balance.format(2)}원")
            return true
        }
        return false
    }
}
