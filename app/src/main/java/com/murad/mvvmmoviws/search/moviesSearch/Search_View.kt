package com.murad.mvvmmoviws.search.moviesSearch

import android.app.Dialog
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.murad.mvvmmoviws.R
import com.murad.mvvmmoviws.movies.data.repository.NetworkState
import com.murad.mvvmmoviws.search.api.ApiDbClient
import com.murad.mvvmmoviws.search.data.moviesSearchLocalRepository
import kotlinx.android.synthetic.main.fragment_search__view.view.*
import kotlinx.android.synthetic.main.fragment_tv__shows_.*
import kotlinx.android.synthetic.main.old_query_dialog.*
import kotlinx.android.synthetic.main.search_dialog.*


class Search_View : Fragment() {

    lateinit var moviesSearchViewModel:ViewModelSearch1

    private  val TAG = "Search_View"

    private var searchQuery=""
    private var MOVIE_FLAG=true



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_search__view, container, false)



        val sharedPreferences=requireActivity().getSharedPreferences("QUERY", MODE_PRIVATE)
        var oldQuery=sharedPreferences.getString("query","")

        if(oldQuery?.isEmpty()!!)
             showSearchDialog(view)
        else{
            showOldQueryDialog(view,oldQuery)

        }

        return view
    }

    private fun showOldQueryDialog(view: View?,oldQuery:String) {

        val dialog=Dialog(requireContext())
        dialog.setContentView(R.layout.old_query_dialog)

        dialog.newBtn.setOnClickListener {

            dialog.dismiss()
            showSearchDialog(requireView())

        }

        dialog.stayBtn.setOnClickListener {

            searchQuery=oldQuery
            dialog.dismiss()
            init(requireView())

        }

        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog.show()

    }


    private fun showSearchDialog(view:View){

        val dialog=Dialog(requireActivity())
        dialog.setContentView(R.layout.search_dialog)

        dialog.movieQuery.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                  var query=p0.toString()
                if(query.isNotEmpty()){
                    dialog.TvQuery.isEnabled=false
                    searchQuery=query
                    MOVIE_FLAG=true
                }else{
                    dialog.TvQuery.isEnabled=true
                    searchQuery=""
                    MOVIE_FLAG=false

                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })


        dialog.TvQuery.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                var query=p0.toString()
                if(query.isNotEmpty()){
                    dialog.movieQuery.isEnabled=false
                    searchQuery=query
                    MOVIE_FLAG=false
                }else{
                    dialog.movieQuery.isEnabled=true
                    searchQuery=""
                    MOVIE_FLAG=true

                }


            }

            override fun afterTextChanged(p0: Editable?) {
            }


        })



        dialog.searchBtn.setOnClickListener {

            val sharedPreferences=requireActivity().getSharedPreferences("QUERY",MODE_PRIVATE).edit()
            sharedPreferences.putString("query",searchQuery)
                .apply()

            dialog.dismiss()
            init(view)
        }

        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog.show()




    }


    private fun init(view: View){



        val searchServiceApi=ApiDbClient.getApi()

if(MOVIE_FLAG) {
    Toast.makeText(requireContext(), searchQuery, Toast.LENGTH_SHORT).show()
    val localRepoistoryForMovies =
        moviesSearchLocalRepository(searchServiceApi, searchQuery)

    val searchAdapter = SearchAdapter(requireContext(), this@Search_View)

    moviesSearchViewModel = getViewModel(localRepoistoryForMovies)

    view.recSearch.apply {
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = searchAdapter
    }

    moviesSearchViewModel.searchMoviesResponse.observe(requireActivity(), {

        searchAdapter.submitList(it)

    })

    moviesSearchViewModel.networkStateResponse.observe(requireActivity(), {

        Log.d(TAG, "init: ${it.status}")
        searchAdapter.setNetWorkState(it)

        if (it == NetworkState.LOADING) {
            view.progressBar7.visibility = View.VISIBLE
        } else {
            view.progressBar7.visibility = View.GONE


        }
    })


}



    }

    fun goToMovieDetails(movieId:Int){

        view?.findNavController()?.navigate(Search_ViewDirections.actionSearchViewToSingleMovie(movieId.toString()))
    }

    private fun getViewModel(localRepository: moviesSearchLocalRepository):ViewModelSearch1{

        return ViewModelProviders.of(this,object :ViewModelProvider.Factory{

            override fun <T : ViewModel?> create(modelClass: Class<T>): T {

                return ViewModelSearch1(localRepository) as T
            }

        })[ViewModelSearch1::class.java]
    }


}