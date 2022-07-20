package com.example.finance.presentation.di.modules

import dagger.Module

@Module(includes = [BindingModule::class, RemoteModule::class])
class AppModule