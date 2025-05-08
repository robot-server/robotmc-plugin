package com.sysbot32.robotmc.plugin.economy.balance

import com.sysbot32.robotmc.plugin.core.format
import io.github.oshai.kotlinlogging.KotlinLogging
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player
import java.text.DecimalFormat

private val log = KotlinLogging.logger { }

class BalanceCommand : BukkitCommand(
    "balance", "잔고을 조회합니다.", "/balance", listOf("bal", "money"),
) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        try {
            if (sender !is Player) {
                return false
            }
            Bukkit.getServicesManager().getRegistration(Economy::class.java)?.provider?.run {
                val balance = getBalance(sender)
                sender.sendMessage("잔고: $${balance.format(DecimalFormat("#,##0.00"))}")
                return true
            }
            return false
        } catch (e: Exception) {
            log.error(e) { e.message }
            return false
        }
    }
}
