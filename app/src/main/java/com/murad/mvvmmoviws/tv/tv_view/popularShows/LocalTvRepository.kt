package com.murad.mvvmmoviws.tv.tv_view.popularShows

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.murad.mvvmmoviws.movies.data.repository.NetworkState
import com.murad.mvvmmoviws.movies.main.App
import com.murad.mvvmmoviws.tv.data.api.tvApi
import com.murad.mvvmmoviws.tv.data.repository.TvMainRepository
import com.murad.mvvmmoviws.tv.data.repository.TvRepoFactory
import com.murad.mvvmmoviws.tv.data.vo.Result
import io.reactivex.disposables.CompositeDisposable

class LocalTvRepository(val apiService:tvApi,val compositeDisposable: CompositeDisposable) {

    lateinit var tvPagedList:LiveData<PagedList<Result>>
    lateinit var tvRepoFactory: TvRepoFactory

    fun fetchTvMovies():LiveData<PagedList<Result>>{

        tvRepoFactory= TvRepoFactory(apiService,compositeDisposable)

        val config=PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(App().PER_PAGE)
            .build()

        tvPagedList=LivePagedListBuilder(tvRepoFactory,config).build()

        return tvPagedList

    }

    fun getNetWorkState():LiveData<NetworkState>{

        return Transformations.switchMap(tvRepoFactory.TvMainRepositoryList,TvMainRepository::_networkState)
    }
}