package com.podonin.quotes.data.mapper

import kotlinx.serialization.json.add
import kotlinx.serialization.json.addJsonArray
import kotlinx.serialization.json.buildJsonArray

fun Pair<String, List<String>>.mapToRequest(): String {
    val jsonArray = buildJsonArray {
        add(first)
        addJsonArray { second.forEach { add(it) } }
    }
    return jsonArray.toString()
}