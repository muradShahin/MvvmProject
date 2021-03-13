package com.murad.mvvmmoviws.tv.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.murad.mvvmmoviws.data.repository.NetworkState
import com.murad.mvvmmoviws.tv.data.api.tvApi
import com.murad.mvvmmoviws.tv.data.vo.Tv_show
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class Tv_Details_Repo_Main(val compositeDisposable: CompositeDisposable,val apiTvService:tvApi,val tvId:Int) {


    private val _TvDetails=MutableLiveData<Tv_show>()
    val Tv_Details : LiveData<Tv_show>
      get() = _TvDetails

    private val _NetWorkState=MutableLiveData<NetworkState>()

    val  NetworkStatePublic : LiveData<NetworkState>
    get() = _NetWorkState


    fun fetchTvShowDetails(){

        _NetWorkState.postValue(NetworkState.LOADING)




        compositeDisposable.add(
            apiTvService.getTvShowId(tvId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _NetWorkState.postValue(NetworkState.LOADED)
                    _TvDetails.postValue(it)



                },{
                    _NetWorkState.postValue(NetworkState.ERROR)

                })
        )





    }



}