package com.murad.mvvmmoviws.movies.login.loginFragments

import android.util.Log
import androidx.lifecycle.LiveData
import com.murad.mvvmmoviws.movies.auth.api.LoginApi
import com.murad.mvvmmoviws.movies.auth.vo.UserInfo
import com.murad.mvvmmoviws.movies.auth.vo.loginRequest
import com.murad.mvvmmoviws.movies.data.repository.LoginRepository
import com.murad.mvvmmoviws.movies.data.repository.NetworkState

class LoginLocalRepository(val loginApi:LoginApi,val loginRequest: loginRequest) {


    private lateinit var  mainLoginRepo:LoginRepository
    private  val TAG = "LoginLocalRepository"
    fun initLoginProcess():LiveData<UserInfo>{

        Log.d(TAG, "loginUser: ${loginRequest.email}")

        mainLoginRepo=LoginRepository(loginApi,loginRequest)
         mainLoginRepo.loginUser()

        return mainLoginRepo.userinfoResponse

    }

    fun getNetWorkState():LiveData<NetworkState>{

        return mainLoginRepo.networkState

    }
}