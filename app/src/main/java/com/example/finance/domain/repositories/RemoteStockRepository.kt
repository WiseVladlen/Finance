package com.example.finance.domain.repositories

import com.example.finance.domain.entities.Stock

interface RemoteStockRepository {

    suspend fun getStockList(exchange: String) : List<Stock>?
}