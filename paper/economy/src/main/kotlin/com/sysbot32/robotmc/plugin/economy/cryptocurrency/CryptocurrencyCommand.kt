package com.sysbot32.robotmc.plugin.economy.cryptocurrency

import com.sysbot32.robotmc.plugin.economy.cryptocurrency.exchange.upbit.UpbitWebSocketApiClient
import io.github.oshai.kotlinlogging.KotlinLogging
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

private val log = KotlinLogging.logger { }

class CryptocurrencyCommand(
    private val plugin: Plugin,
) : BukkitCommand(
    "cryptocurrency", "암호화폐를 거래합니다.", "/cryptocurrency", listOf()
) {
    val exchange = UpbitWebSocketApiClient(this.plugin)
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        try {
            if (sender !is Player) {
                return false
            }
            val btc = this.exchange.getTradePrice("KRW-BTC")
            val doge = this.exchange.getTradePrice("KRW-DOGE")
            sender.sendMessage("BTC: $btc\nDOGE: $doge")
            return true
        } catch (e: Exception) {
            log.error(e) { e.message }
            return false
        }
    }
}
