package com.murad.mvvmmoviws.movies.login.signUpFragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.murad.mvvmmoviws.movies.auth.vo.signUpResult
import com.murad.mvvmmoviws.data.repository.NetworkState

class SignUpViewModel(val localRepository:SignUpLocalRepository):ViewModel() {


    val signUpResponse:LiveData<signUpResult> by lazy {
        localRepository.createUserFromMainRepo()
    }

    val networkStateResponse:LiveData<NetworkState> by lazy {
        localRepository.getNetWorkState()
    }


}