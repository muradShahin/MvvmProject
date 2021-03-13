package com.murad.mvvmmoviws.profile.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.murad.mvvmmoviws.localDb.DaoDb
import com.murad.mvvmmoviws.localDb.entites.Fav
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileRepository(val context: Context) {


    private val favList=MutableLiveData<List<Fav>>()

    val _favList : LiveData<List<Fav>> get() = favList

    private val deletedResult = MutableLiveData<Boolean>()

    val _deletedResult :LiveData<Boolean> get() = deletedResult


    @InstallIn(ApplicationComponent::class)
    @EntryPoint
    interface GetDaoDependancy{

        fun getDao():DaoDb
    }

    fun _getDao() : DaoDb{
        return EntryPointAccessors.fromApplication(
            context,
            GetDaoDependancy::class.java
        ).getDao()
    }

    fun getFavFromDb(){

        GlobalScope.launch (Dispatchers.IO){

            favList.postValue(_getDao().getFavList())

        }

    }

    fun deleteFromDb(fav: Fav){

        GlobalScope.launch (Dispatchers.IO) {

           var result= _getDao().deleteFav(fav.show_id)

             if(result != null){

                 deletedResult.postValue(true)

             }
        }
    }
}