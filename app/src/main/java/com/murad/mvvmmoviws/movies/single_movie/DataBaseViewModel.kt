package com.murad.mvvmmoviws.movies.single_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.murad.mvvmmoviws.localDb.entites.Fav

class DataBaseViewModel(val localDbRepository: LocalDbRepository) :ViewModel() {


    val addResult : LiveData<Boolean> by lazy {
        localDbRepository.result
    }

    fun addShowToDb(){

        localDbRepository.addToLocalDb()
    }


}