package com.murad.mvvmmoviws.data.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.murad.mvvmmoviws.data.api.MovieDbInterface
import com.murad.mvvmmoviws.data.vo.movie_details
import com.murad.mvvmmoviws.localDb.DaoDb
import com.murad.mvvmmoviws.localDb.entites.Fav
import com.murad.mvvmmoviws.movies.user_actions.api.api
import com.murad.mvvmmoviws.movies.user_actions.vo.UserList
import dagger.hilt.EntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

/*

here in this class
this class is a repository to fetch data from api
 note: we made this repository and other repository called MovieDetailsRepository
 so the second repo will get the data from this , so we can cash our data
 */

class DataSource(private val apiService:MovieDbInterface,private val apiServiceList:api,private val compositeDisposable: CompositeDisposable) {



    private val _networkState=MutableLiveData<NetworkState>()

    val networkState:LiveData<NetworkState>
        get() = _networkState

   private val _movieDetailsResponse=MutableLiveData<movie_details>()

    private val _movieAdded=MutableLiveData<Boolean>()

    val movieAdded:LiveData<Boolean>
    get() = _movieAdded

   val movieDetailsResponse:LiveData<movie_details>
    get() = _movieDetailsResponse


    fun fetchMovieDetails(moveId:Int){

        _networkState.postValue(NetworkState.LOADING)
        try {

//            compositeDisposable.add(
//                apiService.getMovieDetails(moveId)
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(
//                        {
//                            _movieDetailsResponse.postValue(it)
//                            _networkState.postValue(NetworkState.LOADED)
//
//                        },
//                        {
//
//                            _networkState.postValue(NetworkState.ERROR)
//                            Log.d("errApi", "fetchMovieDetails: ${it.message}")
//                        }
//                    )
//            )

            apiService.getMovieDetails(moveId).enqueue(object : Callback<movie_details> {
                override fun onResponse(
                    call: Call<movie_details>,
                    response: Response<movie_details>
                ) {

                    _movieDetailsResponse.postValue(response.body())
                    _networkState.postValue(NetworkState.LOADED)

                }

                override fun onFailure(call: Call<movie_details>, t: Throwable) {
                    Log.d("onResponseErrr", "onFailure: ${t.message} ")
                }

            })


        }catch (e:Exception){

            Log.d("error Api", "fetchMovieDetails: ${e.message}")
        }


    }

    @SuppressLint("CheckResult")
    fun addToUserList(userlistRequest:UserList){

        _networkState.postValue(NetworkState.LOADING)


        apiServiceList.addToList(userlistRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({

                _networkState.postValue(NetworkState.LOADED)
                _movieAdded.postValue(true)

            },{
                _networkState.postValue(NetworkState.ERROR)
                _movieAdded.postValue(false)

            })

    }








}