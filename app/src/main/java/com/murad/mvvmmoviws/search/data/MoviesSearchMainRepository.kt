package com.murad.mvvmmoviws.search.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.murad.mvvmmoviws.data.repository.NetworkState
import com.murad.mvvmmoviws.data.vo.Result
import com.murad.mvvmmoviws.movies.main.App
import com.murad.mvvmmoviws.search.api.searchServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class MoviesSearchMainRepository(val searchServices: searchServices,val query:String) :PageKeyedDataSource<Int, Result>() {


    private val _networkStatus=MutableLiveData<NetworkState>()

    val networkState:LiveData<NetworkState>
    get() = _networkStatus

    private  val TAG = "MoviesSearchMainReposit"

    private var page=App().FIRST_PAGE

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Result>
    ) {
        _networkStatus.postValue(NetworkState.LOADING)
        GlobalScope.launch(Dispatchers.IO) {


            try {
                val searchResponse=searchServices.searchForMovies(query,page).awaitResponse()

                if(searchResponse.isSuccessful){

                    _networkStatus.postValue(NetworkState.LOADED)
                    callback.onResult(searchResponse.body()?.results as MutableList<Result>,null,page+1)

                }else{

                    _networkStatus.postValue(NetworkState.ERROR)


                }

            }catch (e:Exception){

                Log.d(TAG, "loadInitial: ${e.message}")

            }





        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {

        _networkStatus.postValue(NetworkState.LOADING)

        GlobalScope.launch(Dispatchers.IO) {

            try {

                val searchResponse= searchServices.searchForMovies(query,params.key).awaitResponse()
                if(searchResponse.isSuccessful){
                    _networkStatus.postValue(NetworkState.LOADED)
                    callback.onResult(searchResponse.body()!!.results,params.key+1)
                }else{
                    _networkStatus.postValue(NetworkState.ERROR)
                }
                
            }catch (e:java.lang.Exception){

                Log.d(TAG, "loadAfter: ${e.message}")
            }
        

        }

    }


}