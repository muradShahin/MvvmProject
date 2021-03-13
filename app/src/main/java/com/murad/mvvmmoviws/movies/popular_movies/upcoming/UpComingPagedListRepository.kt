package com.murad.mvvmmoviws.movies.popular_movies.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.murad.mvvmmoviws.data.api.MovieDbInterface
import com.murad.mvvmmoviws.data.repository.NetworkState
import com.murad.mvvmmoviws.data.repository.UpComingFactory
import com.murad.mvvmmoviws.data.repository.UpCommingRepository
import com.murad.mvvmmoviws.data.vo.ResultX
import com.murad.mvvmmoviws.movies.main.App

class UpComingPagedListRepository(val apiService:MovieDbInterface) {

    lateinit var upComingFactory:UpComingFactory
    lateinit var upComingPagedList:LiveData<PagedList<ResultX>>
       fun fetchUpComingMovies():LiveData<PagedList<ResultX>>{

           upComingFactory= UpComingFactory(apiService)

           val config=PagedList.Config.Builder()
               .setEnablePlaceholders(false)
               .setPageSize(App().PER_PAGE)
               .build()

           upComingPagedList=LivePagedListBuilder(upComingFactory,config).build()

           return upComingPagedList

       }

    fun getNetworkState():LiveData<NetworkState>{
        return Transformations.switchMap<UpCommingRepository,NetworkState>(
            upComingFactory.upcomingMovies,UpCommingRepository::networkState
        )

    }



}