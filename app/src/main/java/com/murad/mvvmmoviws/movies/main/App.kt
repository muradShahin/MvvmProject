package com.murad.mvvmmoviws.movies.main

import android.app.Application
import com.murad.mvvmmoviws.movies.auth.vo.UserInfoX
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App :Application() {
    val API_KEY="5230f4936184d98e9a14a89cb361db70"
    val URL="https://api.themoviedb.org/3/"

    val POSTER_IMG="https://image.tmdb.org/t/p/w342"

    //murad api
    val BASE_URI="http://172.20.10.2:3000/action/"

    val FIRST_PAGE=1
    val PER_PAGE=20



    val USER_SHARED_PREF="USER_PREF"

    private lateinit var userInfo:UserInfoX
    override fun onCreate() {
        super.onCreate()


    }

    fun setUserData(userInfo: UserInfoX){
        this.userInfo=userInfo
    }

    fun getUserData():UserInfoX{
        return  userInfo
    }
}
