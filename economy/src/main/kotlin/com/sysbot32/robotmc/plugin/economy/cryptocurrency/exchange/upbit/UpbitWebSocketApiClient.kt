package com.sysbot32.robotmc.plugin.economy.cryptocurrency.exchange.upbit

import com.sysbot32.robotmc.plugin.core.config.HttpClientConfig
import com.sysbot32.robotmc.plugin.core.toJsonArray
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonPrimitive
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

private val log = KotlinLogging.logger { }

class UpbitWebSocketApiClient(
    private val plugin: Plugin,
    private val publicUrl: String = "wss://api.upbit.com/websocket/v1",
    private val httpClient: HttpClient = HttpClientConfig().httpClient(),
) {
    private val tickerResponses = mutableMapOf<String, JsonObject>()

    init {
        object : BukkitRunnable() {
            override fun run() {
                ticker(listOf("KRW-BTC", "KRW-DOGE"))
            }
        }.runTaskAsynchronously(this.plugin)
    }

    private fun ticker(codes: List<String>) {
        runBlocking {
            httpClient.webSocket(
                urlString = publicUrl,
            ) {
                val request = listOf(
                    mapOf(
                        "ticket" to UUID.randomUUID().toString(),
                    ),
                    mapOf(
                        "type" to "ticker",
                        "codes" to codes,
                    ),
                    mapOf(
                        "format" to "DEFAULT",
                    ),
                ).toJsonArray()
                log.info { "request: $request" }
                sendSerialized(request)
                while (true) {
                    val response = Json.decodeFromString<JsonObject>(String(incoming.receive().data))
                    log.debug { "response: $response" }
                    val code = response["code"]?.jsonPrimitive.toString().removeSurrounding("\"")
                    tickerResponses[code] = response
                }
            }
        }
    }

    fun getTradePrice(code: String): Double {
        return tickerResponses[code]?.get("trade_price")?.jsonPrimitive?.double ?: 0.0
    }
}
