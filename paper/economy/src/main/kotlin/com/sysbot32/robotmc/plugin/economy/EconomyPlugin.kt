package com.sysbot32.robotmc.plugin.economy

import com.sysbot32.robotmc.plugin.core.ReloadCommand
import com.sysbot32.robotmc.plugin.economy.balance.BalanceCommand
import com.sysbot32.robotmc.plugin.economy.balance.BalanceTopCommand
import com.sysbot32.robotmc.plugin.economy.basic_income.BasicIncomeRunnable
import com.sysbot32.robotmc.plugin.economy.cryptocurrency.CryptocurrencyCommand
import com.sysbot32.robotmc.plugin.economy.pay.PayCommand
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin
import kotlin.time.Duration.Companion.minutes

class EconomyPlugin : JavaPlugin() {
    override fun onEnable() {
        config.options().copyDefaults(true)
        saveConfig()
        BasicIncomeRunnable(this).runTaskTimerAsynchronously(this, 0, 20 * 20.minutes.inWholeSeconds)
        server.commandMap.register(name.lowercase(), BalanceCommand())
        server.commandMap.register(name.lowercase(), BalanceTopCommand())
        server.commandMap.register(name.lowercase(), CryptocurrencyCommand(this))
        server.commandMap.register(name.lowercase(), PayCommand())
        server.commandMap.register(name.lowercase(), ReloadCommand(this))
        if (server.servicesManager.getRegistration(Economy::class.java) == null) {
            server.servicesManager.register(Economy::class.java, EconomyImpl(), this, ServicePriority.Normal)
        }
    }

    override fun onDisable() {
        server.scheduler.cancelTasks(this)
    }
}
