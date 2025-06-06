package com.sysbot32.robotmc.plugin.economy.basic_income

import io.github.oshai.kotlinlogging.KotlinLogging
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable

private val log = KotlinLogging.logger { }

class BasicIncomeRunnable(
    private val plugin: Plugin,
) : BukkitRunnable() {
    override fun run() {
        val config = plugin.config
        val message = config.getString("basic-income.message", "기본 소득 지급")
        val amount = config.getDouble("basic-income.amount", 25.0)
        val onlinePlayers = Bukkit.getOnlinePlayers()
        Bukkit.getServer().servicesManager.getRegistration(Economy::class.java)?.provider?.run {
            for (onlinePlayer in onlinePlayers) {
                depositPlayer(onlinePlayer, amount)
                onlinePlayer.sendMessage("$message: $amount")
            }
            log.info { message }
        }
    }
}
