package com.example.finance.domain.interactors

import com.example.finance.domain.entities.Stock
import com.example.finance.domain.repositories.RemoteStockRepository
import javax.inject.Inject

class LoadRemoteStockInteractor@Inject constructor(
    private val repository: RemoteStockRepository
) {
    suspend fun getStockList(
        exchange: String
    ): List<Stock> {
        return when (val stockList = repository.getStockList(exchange)) {
            null -> emptyList()
            else -> stockList
        }
    }
}