package com.murad.mvvmmoviws.movies.popular_movies.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.murad.mvvmmoviws.data.api.MovieDbInterface
import com.murad.mvvmmoviws.data.repository.NetworkState
import com.murad.mvvmmoviws.data.repository.TrendingDataSource
import com.murad.mvvmmoviws.data.vo.Trending_Movies

class TrendingMoviesRepository(val apiService:MovieDbInterface) {


    private lateinit  var trendingMoviesRepository:TrendingDataSource

    private var _networkState=MutableLiveData<NetworkState>()




    fun fetchTrendingMoviesFromMainRepo():LiveData<Trending_Movies>{

        trendingMoviesRepository=TrendingDataSource(apiService)

        trendingMoviesRepository.fetchTrendingMovies()


        return trendingMoviesRepository.trendingMovies


    }

    fun getNetWorkState():LiveData<NetworkState>{

        return trendingMoviesRepository.networkState
    }


}