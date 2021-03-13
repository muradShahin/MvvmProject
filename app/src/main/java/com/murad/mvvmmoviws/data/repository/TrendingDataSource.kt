package com.murad.mvvmmoviws.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.murad.mvvmmoviws.data.api.MovieDbInterface
import com.murad.mvvmmoviws.data.vo.Trending_Movies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrendingDataSource(val apiService:MovieDbInterface){


    private  val TAG = "TrendingDataSource"
    var networkState=MutableLiveData<NetworkState>()


    private var _trendingMovies=MutableLiveData<Trending_Movies>()

    val trendingMovies:LiveData<Trending_Movies>
    get() = _trendingMovies



    fun fetchTrendingMovies(){
        apiService.getTrendingMovies().enqueue(object : Callback<Trending_Movies> {
            override fun onResponse(
                call: Call<Trending_Movies>,
                response: Response<Trending_Movies>
            ) {

                networkState.postValue(NetworkState.LOADED)

                _trendingMovies.postValue(response.body())


            }

            override fun onFailure(call: Call<Trending_Movies>, t: Throwable) {

                networkState.postValue(NetworkState.ERROR)
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }




}