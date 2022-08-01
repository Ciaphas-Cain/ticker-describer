package com.portfolio.wizard.service

import org.slf4j.LoggerFactory

class TickerInfoService {
    private val logger = LoggerFactory.getLogger(javaClass)
    fun fillTickerInfo(ticker: String) {
        logger.info("ticker service: $ticker")
    }
}