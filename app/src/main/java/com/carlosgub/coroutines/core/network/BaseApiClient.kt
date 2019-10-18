package com.carlosgub.coroutines.core.network

import android.content.Context
import com.carlosgub.coroutines.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class BaseApiClient<T>(private val classT: Class<T>) : KoinComponent {
    private val HEADER_AUTHORIZATION = "Authorization"
    private val HEADER_AUTHORIZATION_TYPE = "Bearer "
    private val context: Context by inject()

    open fun getApiClient(
        CONNECTION_TIMEOUT: Long = 180L,
        READ_TIMEOUT: Long = 180L,
        WRITE_TIMEOUT: Long = 180L
    ): T {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofitBuilder = Retrofit.Builder().apply {
            baseUrl(BuildConfig.URL_SERVER)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        }


        val retrofit = retrofitBuilder.build()
        return retrofit.create(classT)
    }
}