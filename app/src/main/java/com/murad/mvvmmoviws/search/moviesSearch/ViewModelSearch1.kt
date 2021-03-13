package com.murad.mvvmmoviws.search.moviesSearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.murad.mvvmmoviws.data.repository.NetworkState
import com.murad.mvvmmoviws.data.vo.Result
import com.murad.mvvmmoviws.search.data.moviesSearchLocalRepository

class ViewModelSearch1(val localRepository: moviesSearchLocalRepository):ViewModel() {


    val searchMoviesResponse : LiveData<PagedList<Result>> by lazy {

         localRepository.loadSearchResult()
    }

    val networkStateResponse :LiveData<NetworkState> by lazy {
        localRepository.getNetWorkStates()
    }






}