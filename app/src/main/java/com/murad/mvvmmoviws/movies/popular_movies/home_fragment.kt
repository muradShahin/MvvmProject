package com.murad.mvvmmoviws.movies.popular_movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.murad.mvvmmoviws.R
import com.murad.mvvmmoviws.movies.data.api.MovieDbInterface
import com.murad.mvvmmoviws.movies.popular_movies.trending.TrendingMoviesRepository
import com.murad.mvvmmoviws.movies.popular_movies.trending.TrendingRecyclerView
import com.murad.mvvmmoviws.movies.popular_movies.trending.TrendingViewModel
import com.murad.mvvmmoviws.movies.popular_movies.upcoming.UpComingPagedListAdapter
import com.murad.mvvmmoviws.movies.popular_movies.upcoming.UpComingPagedListRepository
import com.murad.mvvmmoviws.movies.popular_movies.upcoming.UpComingViewModel
import com.murad.mvvmmoviws.movies.data.repository.NetworkState
import com.murad.mvvmmoviws.movies.data.vo.ResultXX
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.view.*
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class  home_fragment : Fragment() {
    private lateinit var viewModel:PopularViewModel
    private lateinit var pagedListRepository: MoviePagedListRepository

    private lateinit var upcomingViewModel:UpComingViewModel
    private lateinit var upComingPagedListAdapter: UpComingPagedListAdapter
    private lateinit var upComingRepository: UpComingPagedListRepository

    private lateinit var trendingRepository:TrendingMoviesRepository
    private lateinit var trendingViewModel:TrendingViewModel
    private var MOVIES_SIZE=10

    private  val TAG = "home_fragment"


    @Inject
    @Named("MovieDbInterface")
    lateinit var apiService:MovieDbInterface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.home_fragment, container, false)

      //  val apiService=TheMovieDbClient.getClient()

        pagedListRepository=MoviePagedListRepository(apiService)
        upComingRepository= UpComingPagedListRepository(apiService)
        trendingRepository= TrendingMoviesRepository((apiService))





        viewModel=getViewModel()
        upcomingViewModel=getViewModel2()
        trendingViewModel=getViewModel3()



        val movieAdapter=MoviePagedListAdapter(requireContext(),this)
        upComingPagedListAdapter= UpComingPagedListAdapter(requireContext(),this)


//        var gridLayout=GridLayoutManager(requireContext(),3)
//        gridLayout.spanSizeLookup=object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                var viewType=movieAdapter.getItemViewType(position)
//                if(viewType==movieAdapter.MOVIE_VIEW_TYPE)
//                    return 1
//                else
//                    return 3
//            }
//
//        }


        val layoutManagerCus=CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL)
        layoutManagerCus.setPostLayoutListener(CarouselZoomPostLayoutListener())

        viewModel.moviesList.observe(requireActivity(), Observer {
            movieAdapter.submitList(it)
            MOVIES_SIZE=it.size

        })


        var randomNumber=(4..20).random()
        layoutManagerCus.scrollToPosition(randomNumber)
        view.rec_popular.apply {
            layoutManager=layoutManagerCus
            setHasFixedSize(false)
            adapter=movieAdapter


        }
        view.rec_popular.addOnScrollListener(CenterScrollListener())

        val layoutManager2=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

        view.upComing_rec.apply {
            layoutManager=layoutManager2
            setHasFixedSize(true)
            adapter=upComingPagedListAdapter
        }


        viewModel.networkState.observe(requireActivity(), Observer {

                if (viewModel.listIsEmpty() && it == NetworkState.LOADING){
                    view.progressBar2.visibility=View.VISIBLE


                }else{
                    view.progressBar2.visibility=View.GONE
                }

            if (!viewModel.listIsEmpty())
                movieAdapter.serNetWorkState(it)
        })


        upcomingViewModel.upComingMovies.observe(requireActivity(),{

            upComingPagedListAdapter.submitList(it)

        })

        upcomingViewModel.netWorkState.observe(requireActivity(),{

            if(it ==NetworkState.LOADING)
                view.progressBar4.visibility=View.VISIBLE
            else
                view.progressBar4.visibility=View.GONE



        })


        val trendingAdapter=TrendingRecyclerView(this)

        view.trending.apply {
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter=trendingAdapter
        }



        trendingViewModel.trendingMovies.observe(requireActivity(), {


            trendingAdapter.submitList(it.results as ArrayList<ResultXX>)

           
        })

        trendingViewModel.networkState.observe(requireActivity(),{

            if(it==NetworkState.LOADING)
                view.progressBar5.visibility=View.VISIBLE
            else
                view.progressBar5.visibility=View.GONE

        })

        return view
    }



    fun getViewModel():PopularViewModel{
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {

                return PopularViewModel(pagedListRepository) as T
            }

        })[PopularViewModel::class.java]
    }

    private fun getViewModel2(): UpComingViewModel {

        return ViewModelProviders.of(this,object :ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return UpComingViewModel(upComingRepository) as T
            }


        })[UpComingViewModel::class.java]



    }

    private fun getViewModel3():TrendingViewModel{



        return ViewModelProviders.of(this,object :ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {

                return TrendingViewModel(trendingRepository) as T
            }

        })[TrendingViewModel::class.java]
    }


    fun goToMovieDetails(movieId:Int){
        view?.findNavController()?.navigate(home_fragmentDirections.actionHomeFragmentToSingleMovie("${movieId}"))
    }


}