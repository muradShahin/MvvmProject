package com.murad.mvvmmoviws.tv.tv_view.tv_details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.murad.mvvmmoviws.R
import com.murad.mvvmmoviws.data.repository.NetworkState
import com.murad.mvvmmoviws.movies.main.App
import com.murad.mvvmmoviws.tv.data.api.tvApi
import com.murad.mvvmmoviws.tv.data.repository.Tv_Details_Repo_Main
import com.murad.mvvmmoviws.tv.data.vo.Tv_show
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.tv_details_show.view.*
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class Tv_Show_Details : Fragment() {


    private lateinit var localRepository:LocalTvDetailsRepository
    private var viewModel:Tv_Shows_ViewModel?=null

    private lateinit var  compositeDisposable:CompositeDisposable
    private var tv_show_id:Int?=null
    private  val TAG = "Tv_Show_Details"

    @Inject
    @Named("TvApi")
    lateinit var apiService:tvApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tv_show_id=arguments?.getInt("show_id")
     
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.tv_details_show, container, false)
        
        
        init(view)
        
        
        return view
    }
    
   private fun init(view:View){

        compositeDisposable= CompositeDisposable()

       val mainRepository=Tv_Details_Repo_Main(compositeDisposable,apiService,tv_show_id!!)
       
        localRepository=LocalTvDetailsRepository(mainRepository)
        viewModel=getViewModel()
       
       viewModel!!.tvDetailsData.observe(requireActivity(), {

           loadData(view,it)


       })
       
       viewModel!!.tvNetworkState.observe(requireActivity(), {

           if(it == NetworkState.LOADED){
               view.progress_bar.visibility=View.GONE
               view.scrol.visibility=View.VISIBLE

           }else{
               view.progress_bar.visibility=View.VISIBLE
               view.scrol.visibility=View.GONE

           }
       })
       
       
       
       
   }

    private fun loadData(view: View, tvShow: Tv_show,) {

        Glide.with(this)
            .load(App().POSTER_IMG+tvShow.poster_path)
            .into(view.iv_movie_poster)

        Glide.with(this)
            .load(App().POSTER_IMG+tvShow.poster_path)
            .into(view.imageView3)

        view.movie_title.text=tvShow.name
        view.movie_tagline.text=tvShow.original_name
        view.showRate.text=tvShow.vote_average.toString()
        view.movie_overview.text=tvShow.overview
        view.show_seasons.text=tvShow.number_of_seasons.toString()
        view.show_type.text=tvShow.type
        view.show_runtime.text=tvShow.episode_run_time.toString()

        view.sesons.apply {
            layoutManager=LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        }
        val adapter=SeasonsRecyclerAdapter(this,tvShow.seasons)
        view.sesons.adapter=adapter

    }

     fun showSeason(seasonId:Int){

         view?.findNavController()?.navigate(Tv_Show_DetailsDirections.actionTvShowDetailsSelf(seasonId))

    }


    private fun getViewModel():Tv_Shows_ViewModel{
        
        return ViewModelProviders.of(this,object :ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
               
                return Tv_Shows_ViewModel(localRepository) as T
            }

        })[Tv_Shows_ViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: onDestroy Composite")
        compositeDisposable.clear()

    }
   
}