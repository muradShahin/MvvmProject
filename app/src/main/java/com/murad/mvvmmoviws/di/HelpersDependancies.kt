package com.murad.mvvmmoviws.di

import android.content.Context
import android.content.SharedPreferences

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class HelpersDependancies {


    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context):SharedPreferences{

        return context.getSharedPreferences("USER_PREF", Context.MODE_PRIVATE)
    }







}