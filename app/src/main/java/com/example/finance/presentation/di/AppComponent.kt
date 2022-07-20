package com.example.finance.presentation.di

import com.example.finance.presentation.di.modules.AppModule
import com.example.finance.presentation.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun inject(activity: MainActivity)
}