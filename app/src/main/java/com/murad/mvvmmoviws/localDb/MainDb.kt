package com.murad.mvvmmoviws.localDb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.murad.mvvmmoviws.localDb.entites.Fav
import javax.inject.Inject

@Database(entities = [Fav::class]
,version = 1,exportSchema = false)
abstract class MainDb : RoomDatabase() {
    
    abstract fun dbDao() :DaoDb
}