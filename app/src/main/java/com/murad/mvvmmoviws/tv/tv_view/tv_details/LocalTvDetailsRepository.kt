package com.murad.mvvmmoviws.tv.tv_view.tv_details

import androidx.lifecycle.LiveData
import com.murad.mvvmmoviws.data.repository.NetworkState
import com.murad.mvvmmoviws.tv.data.repository.Tv_Details_Repo_Main
import com.murad.mvvmmoviws.tv.data.vo.Tv_show

class LocalTvDetailsRepository(val Tv_Details_Repo_main:Tv_Details_Repo_Main) {


    fun getTv_Show_Details():LiveData<Tv_show>{
        Tv_Details_Repo_main.fetchTvShowDetails()

        return Tv_Details_Repo_main.Tv_Details
    }

    fun getNetworkState():LiveData<NetworkState>{
        return Tv_Details_Repo_main.NetworkStatePublic
    }



}