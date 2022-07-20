package com.example.finance.presentation

import android.app.Application
import com.example.finance.presentation.di.AppComponent
import com.example.finance.presentation.di.DaggerAppComponent

class FinanceApplication : Application() {

    var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE : FinanceApplication
            private set
    }
}