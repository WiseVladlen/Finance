package com.example.finance.domain.entities

data class Stock(
    val currency: String,
    val symbol: String,
    val price: Float = 0f,
    val change: Float = 0f,
    val percentChange: Float = 0f,
)