package com.murad.mvvmmoviws.tv.tv_view.popularShows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.murad.mvvmmoviws.R
import com.murad.mvvmmoviws.movies.data.repository.NetworkState
import com.murad.mvvmmoviws.tv.data.api.tvApi
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_tv__shows_.view.*
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class Tv_Shows_Fragment : Fragment() {


    private  val TAG = "Tv_Shows_Fragment"
    private lateinit var viewModel:TvShowsViewModel

    @Inject
    @Named("TvApi")
    lateinit var apiTvService:tvApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_tv__shows_, container, false)



        //rx java disposable
        val compositeDisposable=CompositeDisposable()

        //init repository
        val localTvRepository=LocalTvRepository(apiTvService,compositeDisposable)


        viewModel=getViewModel(localTvRepository)


        //init pagedList Adapter
        val tvShowsAdapter=TvShowsAdapter(requireContext(),this)
        view.recTv.apply {
            layoutManager=GridLayoutManager(requireActivity(),2)
            adapter=tvShowsAdapter
        }



        viewModel.tvShowsList.observe(requireActivity(), {

            tvShowsAdapter.submitList(it)
        })

        viewModel.networkStateList.observe(requireActivity(),{

            if(viewModel.isListEmpty()!! && it == NetworkState.LOADING){

                view.progressBar6.visibility=View.VISIBLE

            }else{
                view.progressBar6.visibility=View.GONE

            }

            if(!viewModel.isListEmpty()!!){
                tvShowsAdapter.serNetWorkState(it)
            }

        })





        return view

    }

    private fun getViewModel( localTvRepository: LocalTvRepository):TvShowsViewModel{
        return ViewModelProviders.of(this,object:ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return  TvShowsViewModel(localTvRepository) as T
            }

        })[TvShowsViewModel::class.java]
    }

    fun showTvDetails(movieId:Int){
        view?.findNavController()?.navigate(Tv_Shows_FragmentDirections.actionTvShowsFragmentToTvShowDetails(movieId))
    }


}