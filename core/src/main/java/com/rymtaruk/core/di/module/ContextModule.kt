package com.rymtaruk.core.di.module

import android.content.Context
import dagger.Binds
import dagger.Module

@Module
abstract class ContextModule {

    @Binds
    abstract fun bindContext(applicationContext: Context) : Context
}