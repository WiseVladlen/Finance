package com.example.finance.data.repository

import com.example.finance.data.api.FinanceApi
import com.example.finance.data.api.models.StockResponse
import com.example.finance.data.interfaces.IEntityMapper
import com.example.finance.domain.entities.Stock
import com.example.finance.domain.repositories.RemoteStockRepository
import javax.inject.Inject

class RemoteStockRepositoryImpl @Inject constructor(
    private val financeApi: FinanceApi,
    private val stockResponseToStockMapper: IEntityMapper<StockResponse, Stock>,
) : RemoteStockRepository {
    override suspend fun getStockList(exchange: String): List<Stock>? {
        val response = financeApi.getStockList(exchange)
        return when (response.isSuccessful) {
            true -> response.body()?.map { stock ->
                        stockResponseToStockMapper.mapEntity(stock)
                    }?.sortedBy { stock ->
                        stock.symbol
                    }
            false -> emptyList()
        }
    }
}