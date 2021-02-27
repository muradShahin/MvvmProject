package com.murad.mvvmmoviws.movies.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.murad.mvvmmoviws.movies.data.api.MovieDbInterface
import com.murad.mvvmmoviws.movies.data.vo.Trailers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrailersDataSource(val apiService:MovieDbInterface,val movie_id:Int) {

   private val trailersList=MutableLiveData<Trailers>()

    private  val TAG = "TrailersDataSource"
    val _trailersList:LiveData<Trailers>
      get() = trailersList


    private val network_state=MutableLiveData<NetworkState>()

    val _network_state:LiveData<NetworkState>
     get() = network_state


    fun fetchTrailersFromServer(){
        network_state.postValue(NetworkState.LOADING)
        apiService.getTrailers(movie_id).enqueue(object : Callback<Trailers> {
            override fun onResponse(call: Call<Trailers>, response: Response<Trailers>) {

                trailersList.postValue(response.body())
                network_state.postValue(NetworkState.LOADED)
            }

            override fun onFailure(call: Call<Trailers>, t: Throwable) {

                Log.d(TAG, "onFailure: ${t.message}")
                network_state.postValue(NetworkState.ERROR)

            }


        })

    }






}