package com.murad.mvvmmoviws.movies.data.vo

data class Popular_Movies(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)