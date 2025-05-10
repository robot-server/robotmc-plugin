package com.sysbot32.robotmc.plugin.core

import kotlinx.serialization.json.*
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import java.text.DecimalFormat

fun Double.format(digits: Int): String = "%.${digits}f".format(this)
fun Double.format(decimalFormat: DecimalFormat): String = decimalFormat.format(this)

fun List<*>.toJsonArray(): JsonArray {
    val list: MutableList<JsonElement> = mutableListOf()
    this.forEach { value ->
        when (value) {
            null -> list.add(JsonNull)
            is Map<*, *> -> list.add(value.toJsonObject())
            is List<*> -> list.add(value.toJsonArray())
            is Boolean -> list.add(JsonPrimitive(value))
            is Number -> list.add(JsonPrimitive(value))
            is String -> list.add(JsonPrimitive(value))
            is Enum<*> -> list.add(JsonPrimitive(value.toString()))
            else -> throw IllegalStateException("Can't serialize unknown collection type: $value")
        }
    }
    return JsonArray(list)
}

fun Map<*, *>.toJsonObject(): JsonObject {
    val map: MutableMap<String, JsonElement> = mutableMapOf()
    this.forEach { (key, value) ->
        key as String
        when (value) {
            null -> map[key] = JsonNull
            is Map<*, *> -> map[key] = value.toJsonObject()
            is List<*> -> map[key] = value.toJsonArray()
            is Boolean -> map[key] = JsonPrimitive(value)
            is Number -> map[key] = JsonPrimitive(value)
            is String -> map[key] = JsonPrimitive(value)
            is Enum<*> -> map[key] = JsonPrimitive(value.toString())
            else -> throw IllegalStateException("Can't serialize unknown type: $value")
        }
    }
    return JsonObject(map)
}
// https://youtrack.jetbrains.com/issue/KTOR-3063/Support-serializing-collections-of-different-element-types

fun World.getOtherEnvironment(environment: World.Environment): World? {
    if (this.environment == environment) {
        return this
    }
    val worldName = when (this.environment) {
        World.Environment.NORMAL -> this.name
        World.Environment.NETHER, World.Environment.THE_END -> this.name.removeSuffix(environment.name.lowercase())
        else -> throw IllegalArgumentException("Unknown environment ${this.environment}")
    }
    val suffix = when (environment) {
        World.Environment.NORMAL -> ""
        World.Environment.NETHER, World.Environment.THE_END -> environment.name.lowercase()
        else -> throw IllegalArgumentException("Unknown environment ${this.environment}")
    }
    return Bukkit.getWorld(worldName + "_" + suffix)
}

fun Location.toOverworld(): Location {
    return when (this.world.environment) {
        World.Environment.NORMAL -> this
        World.Environment.NETHER -> Location(
            this.world.getOtherEnvironment(World.Environment.NETHER),
            this.x.toInt() * 8.0,
            this.y,
            this.z.toInt() * 8.0,
        )

        else -> throw IllegalArgumentException("Overworld 또는 Nether만 가능합니다.")
    }
}

fun Location.toNether(): Location {
    return when (this.world.environment) {
        World.Environment.NORMAL -> Location(
            this.world.getOtherEnvironment(World.Environment.NETHER),
            this.x.toInt().floorDiv(8).toDouble(),
            this.y,
            this.z.toInt().floorDiv(8).toDouble(),
        )

        World.Environment.NETHER -> this

        else -> throw IllegalArgumentException("Overworld 또는 Nether만 가능합니다.")
    }
}

fun Location.toSimpleString(): String {
    return "[${this.world.name}] (${this.x.format(1)}, ${this.y.format(1)}, ${this.z.format(1)})"
}
