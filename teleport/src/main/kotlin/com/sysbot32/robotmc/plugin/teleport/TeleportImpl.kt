package com.sysbot32.robotmc.plugin.teleport

import com.sysbot32.robotmc.plugin.core.format
import com.sysbot32.robotmc.plugin.core.services.Teleport
import com.sysbot32.robotmc.plugin.core.toSimpleString
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import kotlin.math.max

class TeleportImpl : Teleport {
    private fun calculatePrice(source: Location, destination: Location): Double {
        val minPrice = 100.0
        val distance = source.distance(destination)
        return max(minPrice, distance).format(2).toDouble()
    }

    override fun teleport(player: Player, destination: Location): Boolean {
        val economy = Bukkit.getServer().servicesManager.getRegistration(Economy::class.java)?.provider ?: return false
        while (!destination.block.isEmpty) { // 생매장 방지
            destination.y++
        }
        val price = this.calculatePrice(player.location, destination)
        if (!economy.has(player, price)) {
            player.sendMessage("잔액이 부족합니다! 필요 금액: $price")
            return false
        }
        player.sendMessage("${player.location.toSimpleString()} -> ${destination.toSimpleString()}: $price")
        economy.withdrawPlayer(player, price)
        return player.teleport(destination)
    }

    override fun teleport(player: Player, destination: Player): Boolean {
        if (player.world != destination.world) {
            player.sendMessage("서로 다른 월드 간 이동은 현재 지원하지 않습니다.")
            return false
        }
        return this.teleport(player, destination.location)
    }
}
