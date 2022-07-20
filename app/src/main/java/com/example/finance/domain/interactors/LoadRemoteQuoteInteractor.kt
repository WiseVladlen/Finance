package com.example.finance.domain.interactors

import com.example.finance.domain.entities.Stock
import com.example.finance.domain.repositories.RemoteQuoteRepository
import javax.inject.Inject

class LoadRemoteQuoteInteractor @Inject constructor(
    private val repository: RemoteQuoteRepository
) {
    suspend fun getQuoteList(
        stockList: List<Stock>,
        fromIndex: Int,
        toIndex: Int,
    ): List<Stock> {
        return repository.getQuoteList(stockList, fromIndex, toIndex)
    }
}