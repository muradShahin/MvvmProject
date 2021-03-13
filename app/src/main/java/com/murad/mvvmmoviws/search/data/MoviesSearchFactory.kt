package com.murad.mvvmmoviws.search.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.murad.mvvmmoviws.data.vo.Result
import com.murad.mvvmmoviws.search.api.searchServices

class MoviesSearchFactory(val searchServices: searchServices,val query:String):DataSource.Factory<Int,Result>(){

     var moviesSearchMainInstanceLD=MutableLiveData<MoviesSearchMainRepository>()
    override fun create(): DataSource<Int, Result> {

        var moviesSearchMainRepo=MoviesSearchMainRepository(searchServices,query)

        moviesSearchMainInstanceLD.postValue(moviesSearchMainRepo)


        return moviesSearchMainRepo

    }

}