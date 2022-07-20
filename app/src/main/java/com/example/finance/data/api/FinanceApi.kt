package com.example.finance.data.api

import com.example.finance.data.api.models.QuoteResponse
import com.example.finance.data.api.models.StockResponse
import com.example.finance.utils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FinanceApi {

    @GET("quote")
    suspend fun getQuoteByStockName(
        @Query("symbol") symbol : String,
        @Query("token") token : String = API_KEY,
    ): Response<QuoteResponse>

    @GET("stock/symbol")
    suspend fun getStockList(
        @Query("exchange") exchange : String,
        @Query("token") token : String = API_KEY,
    ): Response<List<StockResponse>>
}