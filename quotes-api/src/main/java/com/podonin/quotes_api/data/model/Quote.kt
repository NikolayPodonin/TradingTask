package com.podonin.quotes_api.data.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class Quote(
    @SerialName("c") val ticker: String, // Тикер
    @SerialName("name") val name: String? = null, // Название бумаги
    @SerialName("ltr") val lastTradeExchange: String? = null, // Биржа последней сделки
    @SerialName("ltp") val lastTradePrice: Double? = null, // Цена последней сделки
    @SerialName("chg") val lastTradeChange: Double? = null, // Изменение цены последней сделки в пунктах относительно цены закрытия предыдущей торговой сессии
    @SerialName("pcp") val lastTradeChangePercent: Double? = null, // Изменение в процентах относительно цены закрытия предыдущей торговой сессии
    @SerialName("min_step") val minStep: Double? = null, // Минимальный шаг цены
//    @SerialName("name2") val nameLatin: String? = null,
//    @SerialName("bbp") val bestBidPrice: Double? = null,
//    @SerialName("bbc") val bestBidChange: String? = null,
//    @SerialName("bbs") val bestBidSize: Int? = null,
//    @SerialName("bbf") val bestBidVolume: Double? = null,
//    @SerialName("bap") val bestAskPrice: Double? = null,
//    @SerialName("bac") val bestAskChange: String? = null,
//    @SerialName("bas") val bestAskSize: Int? = null,
//    @SerialName("baf") val bestAskVolume: Double? = null,
//    @SerialName("pp") val previousClosePrice: Double? = null,
//    @SerialName("op") val openPrice: Double? = null,
//    @SerialName("lts") val lastTradeSize: Int? = null,
//    @SerialName("ltt") val lastTradeTime: Long? = null,
//    @SerialName("ltc") val lastTradeChangeIndicator: String? = null,
//    @SerialName("mintp") val minTradePrice: Double? = null,
//    @SerialName("maxtp") val maxTradePrice: Double? = null,
//    @SerialName("vol") val volumeInShares: Double? = null,
//    @SerialName("vlt") val volumeInCurrency: Double? = null,
//    @SerialName("yld") val yieldToMaturity: Double? = null,
//    @SerialName("acd") val accumulatedCouponIncome: Double? = null,
//    @SerialName("fv") val faceValue: Double? = null,
//    @SerialName("mtd") val maturityDate: String? = null,
//    @SerialName("cpn") val couponValue: Double? = null,
//    @SerialName("cpp") val couponPeriodDays: Int? = null,
//    @SerialName("ncd") val nextCouponDate: String? = null,
//    @SerialName("ncp") val lastCouponDate: String? = null,
//    @SerialName("dpd") val marginBuy: Double? = null,
//    @SerialName("dps") val marginSell: Double? = null,
//    @SerialName("trades") val tradesCount: Int? = null,
//    @SerialName("step_price") val stepPrice: Double? = null
)