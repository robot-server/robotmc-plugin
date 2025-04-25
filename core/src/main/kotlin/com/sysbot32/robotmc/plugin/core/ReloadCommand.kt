package com.sysbot32.robotmc.plugin.core

import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class ReloadCommand(
    private val plugin: Plugin,
) : BukkitCommand(
    "reload", "플러그인을 다시 불러옵니다.", "/reload", listOf(),
) {
    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (sender !is Player && !sender.isOp) {
            return false
        }
        this.plugin.reloadConfig()
        return true
    }
}
