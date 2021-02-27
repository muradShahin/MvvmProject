package com.murad.mvvmmoviws.di

import com.murad.mvvmmoviws.movies.auth.api.LoginApi
import com.murad.mvvmmoviws.movies.data.api.MovieDbInterface
import com.murad.mvvmmoviws.movies.main.App
import com.murad.mvvmmoviws.tv.data.api.tvApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RetrofitModule {




    @Singleton
    @Provides
    @Named("MovieDbInterface")
    fun provideRetrofit(okkHttpClient:OkHttpClient):MovieDbInterface{
        return Retrofit.Builder()
            .client(okkHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(App().URL).build()
            .create(MovieDbInterface::class.java)
    }

    @Singleton
    @Provides
    @Named("TvApi")
    fun provideRetrofitTv(okkHttpClient:OkHttpClient):tvApi{
        return Retrofit.Builder()
            .client(okkHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(App().URL)
            .build()
            .create(tvApi::class.java)
    }


    @Singleton
    @Provides
    @Named("loginApi")
    fun provideLoginApi():LoginApi{
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(App().BASE_URI)
            .build()
            .create(LoginApi::class.java)
    }

}