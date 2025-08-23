package com.sysbot32.robotmc.plugin.economy.pay

import io.github.oshai.kotlinlogging.KotlinLogging
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player

private val log = KotlinLogging.logger { }

class PayCommand : BukkitCommand(
    "pay", "다른 사람에게 돈을 지불합니다.", "/pay <player> <amount>", listOf(),
) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        try {
            if (sender !is Player) {
                return false
            }
            val remitter = sender
            val recipient = Bukkit.getOfflinePlayer(args[0])
            val amount = args[1].toDouble()
            Bukkit.getServicesManager().getRegistration(Economy::class.java)?.provider?.run {
                if (!has(remitter, amount)) {
                    remitter.sendMessage("잔고가 부족합니다!")
                    return false
                }
                withdrawPlayer(remitter, amount)
                depositPlayer(recipient, amount)
                remitter.sendMessage("${recipient.name}님에게 ${amount}원을 보냈습니다.")
                recipient.player?.sendMessage("${remitter.name}님으로 부터 ${amount}원을 받았습니다.")
                log.info { "${remitter.name} -> ${recipient.name}: $amount" }
                return true
            }
            return false
        } catch (e: Exception) {
            log.error(e) { e.message }
            return false
        }
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): List<String?> {
        val matchedPlayers = super.tabComplete(sender, alias, args)
        if (args.size == 1) { // <player>
            return matchedPlayers
        }
        if (args.size == 2) { // <amount>
            return listOf("0.01")
        }
        return listOf()
    }
}
