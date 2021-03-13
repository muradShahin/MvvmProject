package com.murad.mvvmmoviws.movies.single_movie.trailers

import androidx.lifecycle.LiveData
import com.murad.mvvmmoviws.data.api.MovieDbInterface
import com.murad.mvvmmoviws.data.repository.NetworkState
import com.murad.mvvmmoviws.data.repository.TrailersDataSource
import com.murad.mvvmmoviws.data.vo.Trailers

class Repository_Trailers(val apiService:MovieDbInterface,val movie_id:Int) {

    lateinit var trailersDataSource:TrailersDataSource

   fun fetchTrailers() :LiveData<Trailers>{

       trailersDataSource=TrailersDataSource(apiService,movie_id)

       trailersDataSource.fetchTrailersFromServer()

       return trailersDataSource._trailersList

   }


    fun getNetWorkState():LiveData<NetworkState>{

        return trailersDataSource._network_state
    }

}