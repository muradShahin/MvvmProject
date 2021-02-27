package com.murad.mvvmmoviws.movies.data.repository


enum class Status{

    RUNNING,
    SUCCESS,
    FAILED

}


class NetworkState (val status:Status, val mess:String){


    companion object{

        val LOADING:NetworkState
        val LOADED:NetworkState
        val ERROR:NetworkState

        init {
            LOADING= NetworkState(Status.RUNNING,"Running")
            LOADED= NetworkState(Status.SUCCESS,"Loaded")
            ERROR= NetworkState(Status.FAILED,"error")
        }


    }


}