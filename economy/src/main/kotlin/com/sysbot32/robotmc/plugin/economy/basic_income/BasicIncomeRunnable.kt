package com.sysbot32.robotmc.plugin.economy.basic_income

import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable

class BasicIncomeRunnable(
    private val plugin: Plugin,
) : BukkitRunnable() {
    override fun run() {
        val config = plugin.config
        val message = config.getString("message", "기본 소득 지급")
        val amount = config.getDouble("amount", 25.0)
        val onlinePlayers = Bukkit.getOnlinePlayers()
        val economy = Bukkit.getServer().servicesManager.getRegistration(Economy::class.java)?.provider ?: return
        for (onlinePlayer in onlinePlayers) {
            economy.depositPlayer(onlinePlayer, amount)
            onlinePlayer.sendMessage("$message: $amount")
        }
    }
}
