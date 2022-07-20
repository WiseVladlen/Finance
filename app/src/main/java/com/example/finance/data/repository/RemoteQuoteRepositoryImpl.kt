package com.example.finance.data.repository

import com.example.finance.data.api.FinanceApi
import com.example.finance.data.api.models.QuoteResponse
import com.example.finance.data.interfaces.IEntityMapper
import com.example.finance.data.interfaces.IExtendedEntityMapper
import com.example.finance.domain.entities.Stock
import com.example.finance.domain.entities.Quote
import com.example.finance.domain.repositories.RemoteQuoteRepository
import javax.inject.Inject

class RemoteQuoteRepositoryImpl @Inject constructor(
    private val financeApi: FinanceApi,
    private val quoteResponseToQuoteMapper: IEntityMapper<QuoteResponse, Quote>,
    private val quoteAndStockToFullStockMapper: IExtendedEntityMapper<Quote, Stock, Stock>,
) : RemoteQuoteRepository {
    override suspend fun getQuoteList(
        stockList: List<Stock>,
        fromIndex: Int,
        toIndex: Int,
    ): List<Stock> {
        val resultList = stockList.toMutableList()
        for (index in fromIndex..toIndex) {
            val stock = stockList[index]
            val response = financeApi.getQuoteByStockName(stock.symbol)
            if (response.isSuccessful) {
                response.body()?.let { quote ->
                    resultList[index] = quoteAndStockToFullStockMapper.mapEntity(
                        quoteResponseToQuoteMapper.mapEntity(quote),
                        stock,
                    )
                }
            }
        }
        return resultList
    }
}