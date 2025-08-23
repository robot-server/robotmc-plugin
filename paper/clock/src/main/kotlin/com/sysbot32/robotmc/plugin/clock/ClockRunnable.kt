package com.sysbot32.robotmc.plugin.clock

import io.github.oshai.kotlinlogging.KotlinLogging
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import java.time.OffsetDateTime

private val log = KotlinLogging.logger { }

class ClockRunnable : BukkitRunnable() {
    override fun run() {
        try {
            val now = OffsetDateTime.now()
            if (now.minute == 0 && now.second == 0) {
                Bukkit.getServer().sendMessage(Component.text("현재 시각은 $now 입니다."))
            }
        } catch (e: Exception) {
            log.error(e) { e.message }
        }
    }
}
