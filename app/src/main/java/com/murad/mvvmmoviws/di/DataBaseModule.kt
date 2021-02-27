package com.murad.mvvmmoviws.di

import com.murad.mvvmmoviws.movies.main.App
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class DataBaseModule {


    @Singleton
    @Provides
    fun provideInterceptor() :Interceptor{
        val requestInterceptor= Interceptor{ chain ->

            val url=chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", App().API_KEY)
                .build()

            val request=chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)

        }

        return requestInterceptor
    }


    @Singleton
    @Provides
    fun provideOkHttp(requestInterceptor:Interceptor) :OkHttpClient{

        return  OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()


    }

}