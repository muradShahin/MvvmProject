package com.murad.mvvmmoviws.movies.popular_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.murad.mvvmmoviws.movies.data.repository.NetworkState
import com.murad.mvvmmoviws.movies.data.vo.Result

class PopularViewModel(val pagedListRepository: MoviePagedListRepository) :ViewModel() {


     val moviesList:LiveData<PagedList<Result>> by lazy {
        pagedListRepository.fetchLiveMoviesData()
    }

    val networkState:LiveData<NetworkState> by lazy {
        pagedListRepository.getNetworkState()
    }

    fun listIsEmpty():Boolean{
        return moviesList.value?.isEmpty() ?:true
    }
}