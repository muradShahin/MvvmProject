package com.murad.mvvmmoviws.di

import com.murad.mvvmmoviws.movies.main.App
import com.murad.mvvmmoviws.movies.user_actions.api.api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class MuradsApiModule {


    @Singleton
    @Provides
    fun provideMuradApi():api{

        val okHttpClient=OkHttpClient.Builder()
            .connectTimeout(60,TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(App().BASE_URI)
            .build()
            .create(api::class.java)

    }
}