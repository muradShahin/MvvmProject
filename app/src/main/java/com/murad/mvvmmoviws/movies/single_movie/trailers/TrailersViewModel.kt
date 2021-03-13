package com.murad.mvvmmoviws.movies.single_movie.trailers

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.murad.mvvmmoviws.data.repository.NetworkState
import com.murad.mvvmmoviws.data.vo.Trailers

class TrailersViewModel(val trailerRepository:Repository_Trailers) :ViewModel() {


    val getTrailers:LiveData<Trailers> by lazy {
        trailerRepository.fetchTrailers()
    }

    val getNetworkState :LiveData<NetworkState> by lazy {

        trailerRepository.getNetWorkState()
    }


}