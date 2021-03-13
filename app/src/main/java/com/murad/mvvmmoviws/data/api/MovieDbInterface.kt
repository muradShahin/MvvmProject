package com.murad.mvvmmoviws.data.api

import com.murad.mvvmmoviws.data.vo.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbInterface {



    @GET("movie/{movie_id}")
      fun getMovieDetails(@Path("movie_id") id:Int) : Call<movie_details>


    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page:Int) :Call<Popular_Movies>


    @GET("movie/{movie_id}/videos")
    fun getTrailers(@Path("movie_id") id:Int):Call<Trailers>

    @GET("movie/upcoming")
    fun getUpComing(@Query("page") page:Int):Call<upComingMovies>

    @GET("trending/movie/day")
    fun getTrendingMovies():Call<Trending_Movies>

}