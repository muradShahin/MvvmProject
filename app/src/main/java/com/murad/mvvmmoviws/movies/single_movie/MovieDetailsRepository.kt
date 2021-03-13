package com.murad.mvvmmoviws.movies.single_movie

import androidx.lifecycle.LiveData
import com.murad.mvvmmoviws.data.api.MovieDbInterface
import com.murad.mvvmmoviws.data.repository.DataSource
import com.murad.mvvmmoviws.data.repository.NetworkState
import com.murad.mvvmmoviws.data.vo.movie_details
import com.murad.mvvmmoviws.localDb.DaoDb
import com.murad.mvvmmoviws.localDb.entites.Fav
import com.murad.mvvmmoviws.movies.user_actions.api.api
import com.murad.mvvmmoviws.movies.user_actions.vo.UserList
import dagger.hilt.EntryPoint
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class MovieDetailsRepository(val apiService: MovieDbInterface,val apiListService:api) {

    lateinit var networkDataSource:DataSource



    fun fetchMovieDetails(moveId:Int,compositeDisposable: CompositeDisposable):LiveData<movie_details>{

        networkDataSource= DataSource(apiService,apiListService,compositeDisposable)
        networkDataSource.fetchMovieDetails(moveId)

        return networkDataSource.movieDetailsResponse

    }

    fun getNetworkState():LiveData<NetworkState>{

       return networkDataSource.networkState

    }

    fun addMovieToList( userlistRequest: UserList){

        networkDataSource.addToUserList(userlistRequest)
    }

    fun getAddToListResult():LiveData<Boolean>{

        return networkDataSource.movieAdded
    }





}