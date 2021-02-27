package com.murad.mvvmmoviws.movies.popular_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.murad.mvvmmoviws.movies.data.api.MovieDbInterface
import com.murad.mvvmmoviws.movies.data.repository.MovieDataSourceFactory
import com.murad.mvvmmoviws.movies.data.repository.MoviesDataSource
import com.murad.mvvmmoviws.movies.data.repository.NetworkState
import com.murad.mvvmmoviws.movies.data.vo.Result
import com.murad.mvvmmoviws.movies.main.App

class MoviePagedListRepository(val apiService:MovieDbInterface) {

    lateinit var movePagedList:LiveData<PagedList<Result>>
    lateinit var movieDataSourceFactory:MovieDataSourceFactory

    fun fetchLiveMoviesData():LiveData<PagedList<Result>>{

        movieDataSourceFactory= MovieDataSourceFactory(apiService)

        val config=PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(App().PER_PAGE)
            .build()

        movePagedList=LivePagedListBuilder(movieDataSourceFactory,config).build()


        return movePagedList


    }

    fun getNetworkState():LiveData<NetworkState>{

        return Transformations.switchMap<MoviesDataSource,NetworkState>(
            movieDataSourceFactory._moviesDataSourseList,MoviesDataSource::_networkState)
    }
}