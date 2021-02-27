package com.murad.mvvmmoviws.tv.data.api

import com.murad.mvvmmoviws.tv.data.vo.TvShowResponse
import com.murad.mvvmmoviws.tv.data.vo.Tv_show
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface tvApi {




    @GET("tv/popular")
      fun getPopularTvs(@Query("page") page:Int):Observable<TvShowResponse>

    @GET("tv/{tv_id}")
    fun getTvShowId(@Path("tv_id") tvId:Int):Single<Tv_show>
}