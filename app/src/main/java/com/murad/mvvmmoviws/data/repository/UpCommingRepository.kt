package com.murad.mvvmmoviws.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.murad.mvvmmoviws.data.api.MovieDbInterface
import com.murad.mvvmmoviws.data.vo.ResultX
import com.murad.mvvmmoviws.data.vo.upComingMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpCommingRepository(val apiService:MovieDbInterface) :PageKeyedDataSource<Int,ResultX>() {


    var networkState=MutableLiveData<NetworkState>()

    var page= 1

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResultX>
    ) {
        networkState.postValue(NetworkState.LOADING)
        apiService.getUpComing(page).enqueue(object :Callback<upComingMovies>{
            override fun onResponse(
                call: Call<upComingMovies>,
                response: Response<upComingMovies>
            ) {

                networkState.postValue(NetworkState.LOADED)

                callback.onResult(response.body()!!.results,null,page+1)

            }

            override fun onFailure(call: Call<upComingMovies>, t: Throwable) {
                networkState.postValue(NetworkState.ERROR)
            }


        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ResultX>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ResultX>) {

        networkState.postValue(NetworkState.LOADING)
        apiService.getUpComing(params.key).enqueue(object :Callback<upComingMovies>{
            override fun onResponse(
                call: Call<upComingMovies>,
                response: Response<upComingMovies>
            ) {

                networkState.postValue(NetworkState.LOADED)

                callback.onResult(response.body()!!.results,params.key+1)

            }

            override fun onFailure(call: Call<upComingMovies>, t: Throwable) {

                networkState.postValue(NetworkState.ERROR)
            }


        })
    }
}