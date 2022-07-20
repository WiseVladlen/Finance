package com.example.finance.data.mappers

import com.example.finance.data.interfaces.IExtendedEntityMapper
import com.example.finance.domain.entities.Stock
import com.example.finance.domain.entities.Quote
import javax.inject.Inject

class QuoteAndStockToFullStockMapper @Inject constructor() :
    IExtendedEntityMapper<Quote, Stock, Stock> {
    override fun mapEntity(entityOne: Quote, entityTwo: Stock): Stock {
        return Stock(
            currency = entityTwo.currency,
            symbol = entityTwo.symbol,
            price = entityOne.price,
            change = entityOne.change,
            percentChange = entityOne.percentChange,
        )
    }
}