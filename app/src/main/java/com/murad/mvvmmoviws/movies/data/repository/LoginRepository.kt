package com.murad.mvvmmoviws.movies.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.murad.mvvmmoviws.movies.auth.api.LoginApi
import com.murad.mvvmmoviws.movies.auth.vo.UserInfo
import com.murad.mvvmmoviws.movies.auth.vo.loginRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginRepository(val authApi:LoginApi,val loginRequest: loginRequest) {

    private  val TAG = "LoginRepository"

    private val _networkState=MutableLiveData<NetworkState>()
    private val _userinfoResponse=MutableLiveData<UserInfo>()


    val networkState:LiveData<NetworkState>
    get() = _networkState

    val userinfoResponse:LiveData<UserInfo>
    get() = _userinfoResponse



    fun loginUser() {
        _networkState.postValue(NetworkState.LOADING)
        val disposable: CompositeDisposable = CompositeDisposable()
        val observableRequest=authApi.login(loginRequest)
        disposable.add(
        observableRequest.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                if (it.userInfo != null) {
                    Log.d(TAG, "loginUser: ${it.userInfo.email}")
                    _networkState.postValue(NetworkState.LOADED)
                    _userinfoResponse.postValue(it)
                    disposable.dispose()

                } else {
                    Log.d(TAG, "loginUser: password or username are incorrect")
                    _networkState.postValue(NetworkState.LOADED)
                    _userinfoResponse.postValue(it)
                    disposable.dispose()

                }

            }, {
                Log.d(TAG, "loginUser: ${it.message}")
                _networkState.postValue(NetworkState.ERROR)
                disposable.dispose()

            })


      )


    }

}