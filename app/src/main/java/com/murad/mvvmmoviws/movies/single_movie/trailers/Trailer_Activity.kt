package com.murad.mvvmmoviws.movies.single_movie.trailers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.murad.mvvmmoviws.R
import com.murad.mvvmmoviws.data.api.MovieDbInterface
import com.murad.mvvmmoviws.data.repository.NetworkState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_trailer_.*
import kotlinx.android.synthetic.main.activity_trailer_.view.*
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class Trailer_Activity : Fragment() {


    private  var movie_id:Int=0

    private lateinit var viewModel:TrailersViewModel

    private  val TAG = "Trailer_Activity"

    private lateinit var TrailersAdapter:TrailerAdapter

    @Inject
    @Named("MovieDbInterface")
    lateinit var apiService:MovieDbInterface

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.activity_trailer_,container,false)

        init(view)
        return view
    }

    private fun init(view: View){

        movie_id=requireArguments().getInt("movie_id")


        val repository_trailers=Repository_Trailers(apiService,movie_id)

        view.trailersRec.apply {
            layoutManager=LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
        }


      viewModel=getViewModel(repository_trailers)

        viewModel.getTrailers.observe(requireActivity(), {


            Log.d(TAG, "init: ${it}")

            TrailersAdapter=TrailerAdapter(this,it.results)
            trailersRec.adapter=TrailersAdapter


        })

        viewModel.getNetworkState.observe(requireActivity(),{

            if(it==(NetworkState.LOADED)){

                progressBar3.visibility=View.GONE
            }else{
                progressBar3.visibility=View.VISIBLE

            }

        })


    }

    fun getViewModel(repositoryTrailers: Repository_Trailers):TrailersViewModel{
        return ViewModelProviders.of(this,object :ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return TrailersViewModel(repositoryTrailers) as T
            }

        })[TrailersViewModel::class.java]
    }
}