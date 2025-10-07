package com.sysbot32.robotmc.plugin.economy

import io.github.oshai.kotlinlogging.KotlinLogging
import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.OfflinePlayer
import java.math.BigDecimal

private val log = KotlinLogging.logger { }

class EconomyImpl : Economy {
    private val accounts = mutableMapOf<OfflinePlayer, BigDecimal>()

    companion object {
        private val NOT_FOUND_PLAYER_RESPONSE =
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "플레이어가 존재하지 않습니다.")
        private val NOT_IMPLEMENTED_RESPONSE =
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "지원하지 않는 기능입니다.")
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getName(): String {
        return "Robotmc Economy"
    }

    override fun hasBankSupport(): Boolean {
        return false
    }

    override fun fractionalDigits(): Int {
        return -1
    }

    override fun format(amount: Double): String {
        return "${this.currencyNameSingular()}${BigDecimal.valueOf(amount).toPlainString()}"
    }

    override fun currencyNamePlural(): String {
        return this.currencyNameSingular()
    }

    override fun currencyNameSingular(): String {
        return "$"
    }

    @Deprecated("Deprecated in Java")
    override fun hasAccount(playerName: String?): Boolean {
        return this.accounts.keys.find { it.name == playerName }?.let { this.hasAccount(it) } ?: false
    }

    override fun hasAccount(player: OfflinePlayer?): Boolean {
        return this.accounts.containsKey(player)
    }

    @Deprecated("Deprecated in Java")
    override fun hasAccount(playerName: String?, worldName: String?): Boolean {
        return this.hasAccount(playerName)
    }

    override fun hasAccount(player: OfflinePlayer?, worldName: String?): Boolean {
        return this.hasAccount(player)
    }

    @Deprecated("Deprecated in Java")
    override fun getBalance(playerName: String?): Double {
        return this.accounts.keys.find { it.name == playerName }?.let { this.getBalance(it) } ?: 0.0
    }

    override fun getBalance(player: OfflinePlayer?): Double {
        return (this.accounts[player]?.toDouble() ?: 0.0).also {
            log.info { "${player?.name} 잔액: $it" }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun getBalance(playerName: String?, world: String?): Double {
        return this.getBalance(playerName)
    }

    override fun getBalance(player: OfflinePlayer?, world: String?): Double {
        return this.getBalance(player)
    }

    @Deprecated("Deprecated in Java")
    override fun has(playerName: String?, amount: Double): Boolean {
        return this.accounts.keys.find { it.name == playerName }?.let { this.has(it, amount) } ?: false
    }

    override fun has(player: OfflinePlayer?, amount: Double): Boolean {
        return (this.accounts[player] ?: BigDecimal.ZERO) >= BigDecimal.valueOf(amount)
    }

    @Deprecated("Deprecated in Java")
    override fun has(playerName: String?, worldName: String?, amount: Double): Boolean {
        return this.has(playerName, amount)
    }

    override fun has(player: OfflinePlayer?, worldName: String?, amount: Double): Boolean {
        return this.has(player, amount)
    }

    @Deprecated("Deprecated in Java")
    override fun withdrawPlayer(playerName: String?, amount: Double): EconomyResponse {
        return this.accounts.keys.find { it.name == playerName }?.let { this.withdrawPlayer(it, amount) }
            ?: NOT_FOUND_PLAYER_RESPONSE
    }

    override fun withdrawPlayer(player: OfflinePlayer?, amount: Double): EconomyResponse {
        if (player == null) {
            return NOT_FOUND_PLAYER_RESPONSE
        }
        if (!this.has(player, amount)) {
            return EconomyResponse(0.0, this.getBalance(player), EconomyResponse.ResponseType.FAILURE, "잔액이 부족합니다.")
        }
        val balance = (this.accounts[player] ?: BigDecimal.ZERO) - BigDecimal.valueOf(amount)
        this.accounts[player] = balance
        return EconomyResponse(amount, balance.toDouble(), EconomyResponse.ResponseType.SUCCESS, null).also {
            log.info { "${player.name} 출금: ${it.amount}" }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun withdrawPlayer(playerName: String?, worldName: String?, amount: Double): EconomyResponse? {
        return this.withdrawPlayer(playerName, amount)
    }

    override fun withdrawPlayer(player: OfflinePlayer?, worldName: String?, amount: Double): EconomyResponse {
        return this.withdrawPlayer(player, amount)
    }

    @Deprecated("Deprecated in Java")
    override fun depositPlayer(playerName: String?, amount: Double): EconomyResponse {
        return this.accounts.keys.find { it.name == playerName }?.let { this.depositPlayer(it, amount) }
            ?: NOT_FOUND_PLAYER_RESPONSE
    }

    override fun depositPlayer(player: OfflinePlayer?, amount: Double): EconomyResponse {
        if (player == null) {
            return NOT_FOUND_PLAYER_RESPONSE
        }
        val balance = (this.accounts[player] ?: BigDecimal.ZERO) + BigDecimal.valueOf(amount)
        this.accounts[player] = balance
        return EconomyResponse(amount, balance.toDouble(), EconomyResponse.ResponseType.SUCCESS, null).also {
            log.info { "${player.name} 입금: ${it.amount}" }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun depositPlayer(playerName: String?, worldName: String?, amount: Double): EconomyResponse {
        return this.depositPlayer(playerName, amount)
    }

    override fun depositPlayer(player: OfflinePlayer?, worldName: String?, amount: Double): EconomyResponse {
        return this.depositPlayer(player, amount)
    }

    @Deprecated("Deprecated in Java")
    override fun createBank(name: String?, player: String?): EconomyResponse {
        return NOT_IMPLEMENTED_RESPONSE
    }

    override fun createBank(name: String?, player: OfflinePlayer?): EconomyResponse {
        return NOT_IMPLEMENTED_RESPONSE
    }

    override fun deleteBank(name: String?): EconomyResponse {
        return NOT_IMPLEMENTED_RESPONSE
    }

    override fun bankBalance(name: String?): EconomyResponse {
        return NOT_IMPLEMENTED_RESPONSE
    }

    override fun bankHas(name: String?, amount: Double): EconomyResponse {
        return NOT_IMPLEMENTED_RESPONSE
    }

    override fun bankWithdraw(name: String?, amount: Double): EconomyResponse {
        return NOT_IMPLEMENTED_RESPONSE
    }

    override fun bankDeposit(name: String?, amount: Double): EconomyResponse {
        return NOT_IMPLEMENTED_RESPONSE
    }

    @Deprecated("Deprecated in Java")
    override fun isBankOwner(name: String?, playerName: String?): EconomyResponse {
        return NOT_IMPLEMENTED_RESPONSE
    }

    override fun isBankOwner(name: String?, player: OfflinePlayer?): EconomyResponse {
        return NOT_IMPLEMENTED_RESPONSE
    }

    @Deprecated("Deprecated in Java")
    override fun isBankMember(name: String?, playerName: String?): EconomyResponse {
        return NOT_IMPLEMENTED_RESPONSE
    }

    override fun isBankMember(name: String?, player: OfflinePlayer?): EconomyResponse {
        return NOT_IMPLEMENTED_RESPONSE
    }

    override fun getBanks(): List<String> {
        return listOf()
    }

    @Deprecated("Deprecated in Java")
    override fun createPlayerAccount(playerName: String?): Boolean {
        return this.accounts.keys.find { it.name == playerName }?.let { this.createPlayerAccount(it, playerName) }
            ?: false
    }

    override fun createPlayerAccount(player: OfflinePlayer?): Boolean {
        if (player == null || this.hasAccount(player)) {
            return false
        }
        this.accounts[player] = BigDecimal.ZERO
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun createPlayerAccount(playerName: String?, worldName: String?): Boolean {
        return this.createPlayerAccount(playerName)
    }

    override fun createPlayerAccount(player: OfflinePlayer?, worldName: String?): Boolean {
        return this.createPlayerAccount(player)
    }
}
