package com.sysbot32.robotmc.plugin.clock

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import java.time.OffsetDateTime

class ClockRunnable : BukkitRunnable() {
    override fun run() {
        val now = OffsetDateTime.now()
        if (now.minute == 0) {
            Bukkit.getServer().sendMessage(Component.text("현재 시각은 $now 입니다."))
        }
    }
}
