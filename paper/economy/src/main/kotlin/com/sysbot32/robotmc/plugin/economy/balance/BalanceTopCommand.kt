package com.sysbot32.robotmc.plugin.economy.balance

import com.sysbot32.robotmc.plugin.core.format
import io.github.oshai.kotlinlogging.KotlinLogging
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import java.text.DecimalFormat
import java.time.OffsetDateTime

private val log = KotlinLogging.logger { }

class BalanceTopCommand : BukkitCommand(
    "balancetop", "서버 내 잔고 순위를 표시합니다.", "/balancetop", listOf("baltop"),
) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        try {
            val now = OffsetDateTime.now()
            val players = Bukkit.getOfflinePlayers().toList()
            val playersWithBalance = mutableListOf<Pair<OfflinePlayer, Double>>()
            var totalBalance = 0.0
            Bukkit.getServicesManager().getRegistration(Economy::class.java)?.provider?.run {
                for (player in players) {
                    val balance = getBalance(player)
                    playersWithBalance.add(Pair(player, balance))
                    totalBalance += balance
                }
            }
            playersWithBalance.sortBy { -it.second }
            log.info { "playerWithBalance: $playersWithBalance" }
            log.info { "totalBalance: $totalBalance" }
            val padLength = players.maxByOrNull { it.name?.length ?: 0 }?.name?.length ?: 0
            val decimalFormat = DecimalFormat("#,##0.00")
            sender.sendMessage(
                "잔고 순위 ($now)\n전체 잔고: $${totalBalance.format(decimalFormat)}\n${
                    playersWithBalance.mapIndexed { index, pair ->
                        "${index + 1}. ${pair.first.name?.padEnd(padLength)}: $${pair.second.format(decimalFormat)}"
                    }.joinToString("\n")
                }"
            )
            return true
        } catch (e: Exception) {
            log.error(e) { e.message }
            return false
        }
    }
}
