package com.murad.mvvmmoviws.movies.user_actions.api

import com.murad.mvvmmoviws.movies.user_actions.vo.UserList
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface api {

    @POST("addToFav")
    fun addToList(@Body body:UserList):Observable<Any>
}