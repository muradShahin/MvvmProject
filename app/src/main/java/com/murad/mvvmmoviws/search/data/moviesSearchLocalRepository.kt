package com.murad.mvvmmoviws.search.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.murad.mvvmmoviws.data.repository.NetworkState
import com.murad.mvvmmoviws.data.vo.Result
import com.murad.mvvmmoviws.search.api.searchServices

class moviesSearchLocalRepository(val searchServices: searchServices,val query:String) {



    lateinit var listOfSearchResult:LiveData<PagedList<Result>>
    lateinit var moviesSearchFactory: MoviesSearchFactory


    fun loadSearchResult():LiveData<PagedList<Result>>{

        moviesSearchFactory= MoviesSearchFactory(searchServices,query)

        val config=PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .build()

        listOfSearchResult=LivePagedListBuilder(moviesSearchFactory,config).build()

        return listOfSearchResult



    }


    fun getNetWorkStates():LiveData<NetworkState>{

        return Transformations.switchMap(moviesSearchFactory.moviesSearchMainInstanceLD,MoviesSearchMainRepository ::networkState)
    }








}