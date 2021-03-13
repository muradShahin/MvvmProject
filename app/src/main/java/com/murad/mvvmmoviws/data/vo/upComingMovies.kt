package com.murad.mvvmmoviws.data.vo

data class upComingMovies(
    val dates: Dates,
    val page: Int,
    val results: List<ResultX>,
    val total_pages: Int,
    val total_results: Int
)