package com.murad.mvvmmoviws.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.murad.mvvmmoviws.localDb.DaoDb
import com.murad.mvvmmoviws.localDb.MainDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class LocalDbModule {


    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context) : MainDb{
        return Room.databaseBuilder(
            context,
            MainDb::class.java,
            "MoviesDb"

        ).build()
    }

    @Provides
    fun provideDao(database: MainDb) : DaoDb{


        return  database.dbDao()

    }

}