package com.murad.mvvmmoviws.tv.tv_view.tv_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.murad.mvvmmoviws.data.repository.NetworkState
import com.murad.mvvmmoviws.tv.data.vo.Tv_show

class Tv_Shows_ViewModel(val LocalRepo:LocalTvDetailsRepository):ViewModel() {



    val tvDetailsData: LiveData<Tv_show> by lazy {
        LocalRepo.getTv_Show_Details()
    }

    val tvNetworkState:LiveData<NetworkState> by lazy {
        LocalRepo.getNetworkState()
    }

}