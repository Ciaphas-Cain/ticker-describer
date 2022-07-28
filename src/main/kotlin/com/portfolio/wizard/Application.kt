package com.portfolio.wizard

import io.ktor.server.application.*
import com.portfolio.wizard.plugins.*
import com.portfolio.wizard.service.RabbitService

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureRouting()
    RabbitService().startListening()
}
