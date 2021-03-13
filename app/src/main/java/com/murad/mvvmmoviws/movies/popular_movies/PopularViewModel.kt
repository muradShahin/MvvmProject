package com.murad.mvvmmoviws.movies.popular_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.murad.mvvmmoviws.data.repository.NetworkState
import com.murad.mvvmmoviws.data.vo.Result

class PopularViewModel(val pagedListRepository: MoviePagedListRepository) :ViewModel() {

    private var randomNumber = MutableLiveData<Int>()

    init {

        generateRandomNumber()
    }

     val moviesList:LiveData<PagedList<Result>> by lazy {
        pagedListRepository.fetchLiveMoviesData()
    }

    val networkState:LiveData<NetworkState> by lazy {
        pagedListRepository.getNetworkState()
    }

    fun listIsEmpty():Boolean{
        return moviesList.value?.isEmpty() ?:true
    }

    fun generateRandomNumber(){

        var randomNumb=(4..20).random()
        randomNumber.postValue(randomNumb)

    }

    fun getRandomNumber() : LiveData<Int>{

        return randomNumber
    }
}