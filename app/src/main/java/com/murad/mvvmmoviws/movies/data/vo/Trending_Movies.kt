package com.murad.mvvmmoviws.movies.data.vo

data class Trending_Movies(
    val page: Int,
    val results: List<ResultXX>,
    val total_pages: Int,
    val total_results: Int
)