package com.example.finance.data.mappers

import com.example.finance.data.api.models.QuoteResponse
import com.example.finance.data.interfaces.IEntityMapper
import com.example.finance.domain.entities.Quote
import javax.inject.Inject

class QuoteResponseToQuoteMapper @Inject constructor() :
    IEntityMapper<QuoteResponse, Quote> {
    override fun mapEntity(entity: QuoteResponse): Quote {
        return Quote(
            price = entity.currentPrice,
            change = entity.change,
            percentChange = entity.percentChange,
        )
    }
}