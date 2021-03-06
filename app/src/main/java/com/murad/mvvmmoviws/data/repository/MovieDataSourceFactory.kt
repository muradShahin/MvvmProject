package com.murad.mvvmmoviws.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.murad.mvvmmoviws.data.api.MovieDbInterface
import com.murad.mvvmmoviws.data.vo.Result

class MovieDataSourceFactory(val apiService:MovieDbInterface) : DataSource.Factory<Int,Result>() {

    var _moviesDataSourseList=MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, Result> {

        val moviesDataSource=MoviesDataSource(apiService)

        _moviesDataSourseList.postValue(moviesDataSource)

        return moviesDataSource
    }
}