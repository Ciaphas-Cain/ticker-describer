package com.portfolio.wizard.entity

import kotlinx.serialization.Serializable

@Serializable
data class YahooResponse (
    val quoteSummary: QuoteSummary
)

@Serializable
data class QuoteSummary (
    val result: List<Result>
)

@Serializable
data class Result (
    val assetProfile: AssetProfile,
)

@Serializable
data class AssetProfile (
    val industry: String,
    val sector: String
) {
    override fun toString(): String {
        return "assetProfile(industry='$industry', sector='$sector')"
    }
}