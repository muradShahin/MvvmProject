package com.murad.mvvmmoviws.search.api

import com.murad.mvvmmoviws.movies.data.vo.Popular_Movies
import com.murad.mvvmmoviws.tv.data.vo.TvShowResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface searchServices {


    @GET("search/movie")
     fun searchForMovies(@Query("query") query:String,@Query("page") page:Int) : Call<Popular_Movies>


    @GET("search/tv")
     fun searchForTv(@Query("query") query: String ,@Query("page") page:Int):Call<TvShowResponse>


}