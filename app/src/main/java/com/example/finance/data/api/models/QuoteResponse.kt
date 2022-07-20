package com.example.finance.data.api.models

import com.google.gson.annotations.SerializedName

data class QuoteResponse(
    @SerializedName("c") val currentPrice: Float,
    @SerializedName("d") val change: Float,
    @SerializedName("dp") val percentChange: Float,
)