package com.murad.mvvmmoviws.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.murad.mvvmmoviws.data.api.MovieDbInterface
import com.murad.mvvmmoviws.data.vo.ResultX

class UpComingFactory(val apiService:MovieDbInterface) :DataSource.Factory<Int,ResultX>(){

    var upcomingMovies=MutableLiveData<UpCommingRepository>()
    override fun create(): DataSource<Int, ResultX> {

        var upCommingRepository=UpCommingRepository(apiService)

        upcomingMovies.postValue(upCommingRepository)

        return upCommingRepository
    }


}