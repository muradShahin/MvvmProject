package com.murad.mvvmmoviws.tv.data.vo

data class TvShowResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)