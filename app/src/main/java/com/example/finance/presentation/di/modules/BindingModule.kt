package com.example.finance.presentation.di.modules

import com.example.finance.data.api.models.QuoteResponse
import com.example.finance.data.api.models.StockResponse
import com.example.finance.data.interfaces.IEntityMapper
import com.example.finance.data.interfaces.IExtendedEntityMapper
import com.example.finance.data.mappers.QuoteAndStockToFullStockMapper
import com.example.finance.data.mappers.QuoteResponseToQuoteMapper
import com.example.finance.data.mappers.StockResponseToStockMapper
import com.example.finance.data.repository.RemoteQuoteRepositoryImpl
import com.example.finance.data.repository.RemoteStockRepositoryImpl
import com.example.finance.domain.entities.Stock
import com.example.finance.domain.entities.Quote
import com.example.finance.domain.repositories.RemoteQuoteRepository
import com.example.finance.domain.repositories.RemoteStockRepository
import dagger.Binds
import dagger.Module

@Module
abstract class BindingModule {

    @Binds
    abstract fun bindRemoteQuoteRepository(repository: RemoteQuoteRepositoryImpl): RemoteQuoteRepository

    @Binds
    abstract fun bindRemoteStockRepository(repository: RemoteStockRepositoryImpl): RemoteStockRepository

    @Binds
    abstract fun bindQuoteResponseToQuoteMapper(mapper: QuoteResponseToQuoteMapper): IEntityMapper<QuoteResponse, Quote>

    @Binds
    abstract fun bindStockResponseToStockMapper(mapper: StockResponseToStockMapper): IEntityMapper<StockResponse, Stock>

    @Binds
    abstract fun bindQuoteAndStockToFullStockMapper(mapper: QuoteAndStockToFullStockMapper): IExtendedEntityMapper<Quote, Stock, Stock>
}