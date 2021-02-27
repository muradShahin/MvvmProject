package com.murad.mvvmmoviws.movies.login.loginFragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murad.mvvmmoviws.movies.auth.vo.UserInfo
import com.murad.mvvmmoviws.movies.data.repository.NetworkState

class LoginViewModel(val localLoginRepository: LoginLocalRepository):ViewModel() {

    private val userInfoResponse =MutableLiveData<UserInfo>()
    private  val TAG = "LoginViewModel"

    val _userInfoResponse:LiveData<UserInfo> by lazy {
        localLoginRepository.initLoginProcess()
    }

    val getNetWorkState:LiveData<NetworkState> by lazy {
        localLoginRepository.getNetWorkState()
    }


    fun loginUser(){

        Log.d(TAG, "loginUser: ${localLoginRepository.loginRequest.email}")
        userInfoResponse.postValue(localLoginRepository.initLoginProcess().value)

    }

    fun clearData(){
        userInfoResponse.postValue(null)
    }
}