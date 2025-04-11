package com.sysbot32.robotmc.plugin.basic_income

import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class BasicIncomeRunnable : BukkitRunnable() {
    override fun run() {
        val amount = 25.0
        val onlinePlayers = Bukkit.getOnlinePlayers()
        val economy = Bukkit.getServer().servicesManager.getRegistration(Economy::class.java)?.provider ?: return
        for (onlinePlayer in onlinePlayers) {
            economy.depositPlayer(onlinePlayer, amount)
            onlinePlayer.sendMessage("기본 소득 지급: $amount")
        }
    }
}
