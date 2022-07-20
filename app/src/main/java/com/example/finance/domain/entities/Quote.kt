package com.example.finance.domain.entities

data class Quote(
    val price: Float,
    val change: Float,
    val percentChange: Float,
)