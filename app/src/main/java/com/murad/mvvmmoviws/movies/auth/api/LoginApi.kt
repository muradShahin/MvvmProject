package com.murad.mvvmmoviws.movies.auth.api

import com.murad.mvvmmoviws.movies.auth.vo.SignUpRequest
import com.murad.mvvmmoviws.movies.auth.vo.UserInfo
import com.murad.mvvmmoviws.movies.auth.vo.loginRequest
import com.murad.mvvmmoviws.movies.auth.vo.signUpResult
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {



    @POST("auth/login")
    fun login(@Body body:loginRequest):Observable<UserInfo>

    @POST("auth/addUser")
    fun createAccount(@Body body: SignUpRequest):Observable<signUpResult>
}