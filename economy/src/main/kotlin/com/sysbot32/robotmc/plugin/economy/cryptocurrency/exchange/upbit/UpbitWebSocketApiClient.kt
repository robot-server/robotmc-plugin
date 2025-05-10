package com.sysbot32.robotmc.plugin.economy.cryptocurrency.exchange.upbit

import com.sysbot32.robotmc.plugin.core.config.HttpClientConfig
import com.sysbot32.robotmc.plugin.core.toJsonArray
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import kotlinx.coroutines.runBlocking
import java.util.*

private val log = KotlinLogging.logger { }

class UpbitWebSocketApiClient(
    private val publicUrl: String = "wss://api.upbit.com/websocket/v1",
    private val httpClient: HttpClient = HttpClientConfig().httpClient(),
) {
    fun ticker(codes: List<String>) {
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
                        "is_only_snapshot" to true,
                    ),
                    mapOf(
                        "format" to "DEFAULT",
                    ),
                ).toJsonArray()
                log.info { "request: $request" }
                sendSerialized(request)
                val response = String(incoming.receive().data)
                log.info { "response: $response" }
            }
        }
    }
}
