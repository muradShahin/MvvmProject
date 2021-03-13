package com.murad.mvvmmoviws.movies.single_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.murad.mvvmmoviws.localDb.DaoDb
import com.murad.mvvmmoviws.localDb.entites.Fav
import dagger.hilt.EntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDbRepository (val fav: Fav, val daoDb: DaoDb) {

    private val resultMutableliveData=MutableLiveData<Boolean>()

    val result : LiveData<Boolean> get() = resultMutableliveData


    fun addToLocalDb(){

        GlobalScope.launch (Dispatchers.IO){

                daoDb.addToList(fav)

                resultMutableliveData.postValue(true)

        }

    }
}