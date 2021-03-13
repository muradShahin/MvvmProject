package com.murad.mvvmmoviws.movies.login.signUpFragments

import androidx.lifecycle.LiveData
import com.murad.mvvmmoviws.movies.auth.api.LoginApi
import com.murad.mvvmmoviws.movies.auth.vo.SignUpRequest
import com.murad.mvvmmoviws.movies.auth.vo.signUpResult
import com.murad.mvvmmoviws.data.repository.NetworkState
import com.murad.mvvmmoviws.data.repository.SignUpRepository

class SignUpLocalRepository(val apiService:LoginApi,val signUpRequest:SignUpRequest) {

    private lateinit var signUpRepo:SignUpRepository
    fun createUserFromMainRepo():LiveData<signUpResult>{

         signUpRepo=SignUpRepository(apiService,signUpRequest)
        signUpRepo.createUser()
        return signUpRepo.signUpRequestResponse

    }

    fun getNetWorkState():LiveData<NetworkState>{
        return signUpRepo.networkState
    }

}