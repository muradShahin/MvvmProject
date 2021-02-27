package com.murad.mvvmmoviws.tv.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.murad.mvvmmoviws.tv.data.api.tvApi
import com.murad.mvvmmoviws.tv.data.vo.Result
import io.reactivex.disposables.CompositeDisposable

class TvRepoFactory(val apiService:tvApi,val compositeDisposable: CompositeDisposable):DataSource.Factory<Int,Result>() {

    val TvMainRepositoryList=MutableLiveData<TvMainRepository>()

    override fun create(): DataSource<Int, Result> {

        val tvMainRepository=TvMainRepository(apiService,compositeDisposable)
        TvMainRepositoryList.postValue(tvMainRepository)

        return tvMainRepository
    }
}