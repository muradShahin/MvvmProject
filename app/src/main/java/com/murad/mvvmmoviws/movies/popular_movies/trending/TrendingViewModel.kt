package com.murad.mvvmmoviws.movies.popular_movies.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.murad.mvvmmoviws.movies.data.repository.NetworkState
import com.murad.mvvmmoviws.movies.data.vo.Trending_Movies

class TrendingViewModel(val trendingMoviesRepository: TrendingMoviesRepository):ViewModel() {



    val trendingMovies:LiveData<Trending_Movies> by lazy {
        trendingMoviesRepository.fetchTrendingMoviesFromMainRepo()
    }


    val networkState:LiveData<NetworkState> by lazy {

        trendingMoviesRepository.getNetWorkState()
    }

}