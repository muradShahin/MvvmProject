package com.murad.mvvmmoviws.tv.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.murad.mvvmmoviws.data.repository.NetworkState
import com.murad.mvvmmoviws.movies.main.App
import com.murad.mvvmmoviws.tv.data.api.tvApi
import com.murad.mvvmmoviws.tv.data.vo.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TvMainRepository (val apiService:tvApi,val compositeDisposable: CompositeDisposable):PageKeyedDataSource<Int,Result>(){

    private var page= App().FIRST_PAGE
    var _networkState= MutableLiveData<NetworkState>()
    private  val TAG = "TvMainRepository"

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Result>
    ) {

        _networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getPopularTvs(page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    Log.d(TAG, "loadInitial: ${it.results.size}")
                    callback.onResult(it.results, null, page + 1)
                    _networkState.postValue(NetworkState.LOADED)


                }, {
                    _networkState.postValue(NetworkState.ERROR)
                    Log.d(TAG, "loadInitial: ${it.message}")


                })
        )

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {

    }

    override fun loadAfter(params: LoadParams<Int>,
                           callback: LoadCallback<Int, Result>) {

        _networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularTvs(params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    callback.onResult(it.results,params.key+1)
                    _networkState.postValue(NetworkState.LOADED)

                }, {
                    _networkState.postValue(NetworkState.ERROR)
                    Log.d(TAG, "loadAfter: ${it.message}")
                })


        )


    }
}