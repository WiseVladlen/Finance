package com.example.finance.presentation.di.modules

import com.example.finance.data.api.FinanceApi
import com.example.finance.data.api.RetrofitService
import com.example.finance.utils.BASE_URL
import dagger.Module
import dagger.Provides

@Module
class RemoteModule {

    @Provides
    fun provideWeatherApi(): FinanceApi {
        return RetrofitService()
            .getRetrofit(BASE_URL)
            .create(FinanceApi::class.java)
    }
}