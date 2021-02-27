package com.murad.mvvmmoviws.movies.popular_movies.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.murad.mvvmmoviws.movies.data.repository.NetworkState
import com.murad.mvvmmoviws.movies.data.vo.ResultX

class UpComingViewModel(val upComingPagedListRepository: UpComingPagedListRepository):ViewModel() {


    val upComingMovies:LiveData<PagedList<ResultX>> by lazy {

           upComingPagedListRepository.fetchUpComingMovies()
    }

    val netWorkState:LiveData<NetworkState> by lazy {

        upComingPagedListRepository.getNetworkState()
    }
}