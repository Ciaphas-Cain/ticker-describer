package com.portfolio.wizard.service

import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import com.rabbitmq.client.Delivery

class RabbitService {
    private val cf = ConnectionFactory()

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
        val processTicker = DeliverCallback{ consumerTag: String?, message: Delivery? ->
            println("Consuming message: ${String(message!!.body)} : consumerTag: $consumerTag")
        }
        val cancelCB = CancelCallback {consumerTag: String? ->
            println("Cancelled... $consumerTag")
        }
        channel.basicConsume("moex_tickers", true, processTicker, cancelCB)
    }
}