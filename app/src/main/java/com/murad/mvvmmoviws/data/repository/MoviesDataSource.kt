package com.murad.mvvmmoviws.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.murad.mvvmmoviws.data.api.MovieDbInterface
import com.murad.mvvmmoviws.data.vo.Popular_Movies
import com.murad.mvvmmoviws.data.vo.Result
import com.murad.mvvmmoviws.movies.main.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesDataSource(val apiService:MovieDbInterface) :PageKeyedDataSource<Int,Result>() {

    private var page=App().FIRST_PAGE

    var _networkState=MutableLiveData<NetworkState>()



    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Result>
    ) {

        _networkState.postValue(NetworkState.LOADING)
        apiService.getPopularMovies(page).enqueue(object : Callback<Popular_Movies> {
            override fun onResponse(
                call: Call<Popular_Movies>,
                response: Response<Popular_Movies>
            ) {

                callback.onResult(response.body()!!.results, null, page + 1)
                _networkState.postValue(NetworkState.LOADED)
            }

            override fun onFailure(call: Call<Popular_Movies>, t: Throwable) {

                _networkState.postValue(NetworkState.ERROR)
                Log.d("retrofitOnError", "onFailure: ${t.message}")


            }


        })

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        _networkState.postValue(NetworkState.LOADING)
        apiService.getPopularMovies(params.key).enqueue(object :Callback<Popular_Movies>{
            override fun onResponse(
                call: Call<Popular_Movies>,
                response: Response<Popular_Movies>
            ) {
                if(response.body()!!.total_pages >= params.key) {
                    callback.onResult(response.body()!!.results, params.key+1)
                    _networkState.postValue(NetworkState.LOADED)
                }

            }

            override fun onFailure(call: Call<Popular_Movies>, t: Throwable) {
                _networkState.postValue(NetworkState.ERROR)
                Log.d("retrofitOnError", "onFailure: ${t.message}")

            }

        })

    }
}