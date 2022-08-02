package com.portfolio.wizard.service

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.java.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory

class TickerInfoService {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val client = HttpClient(Java) {
        engine {
            threadsCount = 8
            pipelining = true
            config {
                version(java.net.http.HttpClient.Version.HTTP_2)
            }
        }
    }
    fun fillTickerInfo(ticker: String) {
        logger.info("ticker service: $ticker")
        runBlocking {
            launch { makeCall(ticker) }
        }
    }

    private suspend fun makeCall(ticker : String) {
            val response: HttpResponse = client.get("https://query1.finance.yahoo.com/v10/finance/quoteSummary/$ticker.ME?modules=assetProfile")
            logger.info(response.body())
    }

    fun closeClient() {
        client.close()
    }
}