package com.murad.mvvmmoviws.movies.single_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.murad.mvvmmoviws.data.repository.NetworkState
import com.murad.mvvmmoviws.data.vo.movie_details
import com.murad.mvvmmoviws.localDb.entites.Fav
import com.murad.mvvmmoviws.movies.user_actions.vo.UserList
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(private val movieRepository: MovieDetailsRepository,private val movieId:Int,private val userListRequest:UserList) :ViewModel(){



    val compositeDisposable= CompositeDisposable()
    val movieDetails:LiveData<movie_details> by lazy {

        movieRepository.fetchMovieDetails(movieId,compositeDisposable)
    }

    val networkState:LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }



    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}