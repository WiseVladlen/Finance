package com.example.finance.domain.repositories

import com.example.finance.domain.entities.Stock

interface RemoteQuoteRepository {

    suspend fun getQuoteList(
        stockList: List<Stock>,
        fromIndex: Int,
        toIndex: Int,
    ) : List<Stock>
}