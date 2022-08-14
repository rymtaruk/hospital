package com.rymtaruk.core.di.component

import androidx.annotation.Keep
import com.rymtaruk.core.di.module.ContextModule
import com.rymtaruk.core.di.module.ProviderModule
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Keep
@Singleton
@Component(modules = [ProviderModule::class, ContextModule::class])
interface CoreComponent {
    fun provideRetrofit() : Retrofit
}