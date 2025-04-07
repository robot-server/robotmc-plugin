package com.sysbot32.robotmc.plugin.challenge

import com.sysbot32.robotmc.plugin.core.format
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.entity.Vehicle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.vehicle.VehicleEnterEvent
import org.bukkit.event.vehicle.VehicleExitEvent
import org.bukkit.event.vehicle.VehicleMoveEvent
import java.time.OffsetDateTime
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

class VehicleMoveChallenge : Listener {
    companion object {
        const val OBJECTIVE_NAME = "vehicleMove"
        const val DISPLAY_NAME = "탈 것으로 최장 거리"
    }

    private var data = Data()

    @EventHandler
    fun onVehicleEnter(event: VehicleEnterEvent) {
        if (event.entered is Player) {
            this.data.enter(event.vehicle, event.entered as Player)
        }
    }

    @EventHandler
    fun onVehicleMove(event: VehicleMoveEvent) {
        this.data.move(event.vehicle, event.from, event.to)
    }

    @EventHandler
    fun onVehicleExit(event: VehicleExitEvent) {
        if (event.exited is Player) {
            val player = event.exited as Player
            this.data.exit(event.vehicle)
            player.sendMessage("총 이동 거리: ${this.data.playerScores[player]?.format(3)}m")
        }
    }

    data class Data(
        private val vehiclePlayerMap: MutableMap<Vehicle, Player> = mutableMapOf(),
        val playerScores: MutableMap<Player, Double> = mutableMapOf<Player, Double>().withDefault { 0.0 },
        val startAt: OffsetDateTime = OffsetDateTime.now(),
        val endAt: OffsetDateTime = startAt.plusMinutes(5),
    ) {
        val first: Player
            get() = this.playerScores.maxBy { it.value }.key

        fun enter(vehicle: Vehicle, player: Player) {
            this.vehiclePlayerMap[vehicle] = player
            if (!this.playerScores.containsKey(player)) {
                this.playerScores[player] = 0.0
            }
        }

        fun move(vehicle: Vehicle, from: Location, to: Location) {
            if (vehicle in this.vehiclePlayerMap) {
                val player = this.vehiclePlayerMap[vehicle]!!
                val distance = sqrt((to.x - from.x).pow(2) + (to.y - from.y).pow(2))
                val score = this.playerScores[player]!!.plus(distance)

                this.playerScores[player] = score
                Bukkit.getScoreboardManager().mainScoreboard.getObjective(OBJECTIVE_NAME)?.getScore(player)?.score =
                    score.roundToInt()
            }
        }

        fun exit(vehicle: Vehicle) {
            this.vehiclePlayerMap.remove(vehicle)
        }
    }
}
