package com.portfolio.wizard.service

import com.portfolio.wizard.entity.QuoteSummary
import com.portfolio.wizard.entity.YahooResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory

class TickerInfoService {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val client = HttpClient(Java) {
        install(ContentNegotiation) {
            json(
                Json{
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
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
        val yahooResponse: YahooResponse = response.body()
        val assetProfile = yahooResponse.quoteSummary.result[0].assetProfile
        logger.info("$ticker : ${assetProfile.industry} : ${assetProfile.sector}")
    }

    fun closeClient() {
        client.close()
    }
}