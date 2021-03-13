package com.murad.mvvmmoviws.profile.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murad.mvvmmoviws.localDb.entites.Fav
import com.murad.mvvmmoviws.profile.repository.ProfileRepository

class ProfileViewModel(val profileRepository: ProfileRepository) :ViewModel() {




    val favouriteList : LiveData<List<Fav>> by lazy {

        profileRepository._favList
    }

    val deletedResult : LiveData<Boolean> by lazy {
        profileRepository._deletedResult
    }


     fun getFavList(){
        profileRepository.getFavFromDb()

    }

    fun deleteFromList(fav: Fav){

        profileRepository.deleteFromDb(fav)

    }




}