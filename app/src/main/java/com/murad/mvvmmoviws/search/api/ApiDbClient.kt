package com.murad.mvvmmoviws.search.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Time
import java.util.concurrent.TimeUnit

object ApiDbClient {

    val API_KEY="5230f4936184d98e9a14a89cb361db70"
    val URL="https://api.themoviedb.org/3/"

    val POSTER_IMG="https://image.tmdb.org/t/p/w342"
    val FIRST_PAGE=1
    val PER_PAGE=20


    fun getApi():searchServices{


        var requestInterceptor=Interceptor{chain ->

            val url=chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key",API_KEY)
                .build()

            val request=chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)

        }


        val okHttpClient=OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(3000,TimeUnit.SECONDS)
            .build()

        val retrofit=Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()



        return retrofit.create(searchServices::class.java)


    }

}