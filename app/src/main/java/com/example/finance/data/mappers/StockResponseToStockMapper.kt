package com.example.finance.data.mappers

import com.example.finance.data.api.models.StockResponse
import com.example.finance.data.interfaces.IEntityMapper
import com.example.finance.domain.entities.Stock
import javax.inject.Inject

class StockResponseToStockMapper @Inject constructor() :
    IEntityMapper<StockResponse, Stock> {
    override fun mapEntity(entity: StockResponse): Stock {
        return Stock(
            currency = entity.currency,
            symbol = entity.symbol,
        )
    }
}