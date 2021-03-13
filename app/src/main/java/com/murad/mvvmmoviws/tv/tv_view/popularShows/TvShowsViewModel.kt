package com.murad.mvvmmoviws.tv.tv_view.popularShows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.murad.mvvmmoviws.data.repository.NetworkState
import com.murad.mvvmmoviws.tv.data.vo.Result

class TvShowsViewModel(val localTvRepository: LocalTvRepository):ViewModel() {


    val tvShowsList:LiveData<PagedList<Result>> by lazy {

        localTvRepository.fetchTvMovies()
    }

    val networkStateList:LiveData<NetworkState> by lazy {

        localTvRepository.getNetWorkState()
    }

    fun isListEmpty():Boolean?{

        return tvShowsList.value?.isEmpty() ?:true
    }
}