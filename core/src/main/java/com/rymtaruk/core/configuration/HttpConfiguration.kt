package com.rymtaruk.core.configuration

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.rymtaruk.core.BuildConfig
import okhttp3.OkHttpClient

class HttpConfiguration {
    companion object {
        fun getClient(applicationContext: Context?): OkHttpClient.Builder {
            val builder = OkHttpClient.Builder()

            if (BuildConfig.DEBUG){
                val chuckInterceptor = ChuckerInterceptor.Builder(applicationContext!!).build()
                builder.addInterceptor(chuckInterceptor)
            }

            return builder
        }
    }
}