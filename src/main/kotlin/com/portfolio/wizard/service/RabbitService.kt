package com.portfolio.wizard.service

import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import com.rabbitmq.client.Delivery
import org.slf4j.LoggerFactory

class RabbitService {
    private val cf = ConnectionFactory()
    private val logger = LoggerFactory.getLogger(javaClass)
    private val tickerInfoService = TickerInfoService()

    init {
        cf.host = "localhost"
        cf.port = 5672
        cf.virtualHost = "/"
        cf.username = "guest"
        cf.password = "guest"
    }

    fun startListening() {
        val connection = cf.newConnection()
        val channel = connection.createChannel()
        val processTicker = DeliverCallback{ _: String?, message: Delivery? ->
            tickerInfoService.fillTickerInfo(String(message!!.body))
        }
        val cancelCB = CancelCallback {consumerTag: String? ->
            logger.info("Cancelled... $consumerTag")
        }
        channel.basicConsume("moex_tickers", true, processTicker, cancelCB)
    }
}