package com.murad.mvvmmoviws.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.murad.mvvmmoviws.movies.auth.api.LoginApi
import com.murad.mvvmmoviws.movies.auth.vo.SignUpRequest
import com.murad.mvvmmoviws.movies.auth.vo.signUpResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SignUpRepository(val apiService:LoginApi,val signUpRequest:SignUpRequest) {


    private  val TAG = "SignUpRepository"
    private val _networkState=MutableLiveData<NetworkState>()
    val networkState:LiveData<NetworkState>
      get() = _networkState

    private val _signUpResultResponse=MutableLiveData<signUpResult>()
    val signUpRequestResponse:LiveData<signUpResult>
      get() = _signUpResultResponse


    fun createUser(){

        _networkState.postValue(NetworkState.LOADING)
        val disposable=CompositeDisposable()
        val signUpRequestObservable=apiService.createAccount(signUpRequest)

        disposable.add(

            signUpRequestObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _networkState.postValue(NetworkState.LOADED)

                    _signUpResultResponse.postValue(it)
                    Log.d(TAG, "createUser: ${it.result}")


                }, {
                    _networkState.postValue(NetworkState.ERROR)

                    Log.d(TAG, "createUser: ${it.message}")

                })
        )



    }




}