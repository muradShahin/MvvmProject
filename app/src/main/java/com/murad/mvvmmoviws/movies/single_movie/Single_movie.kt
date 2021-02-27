package com.murad.mvvmmoviws.movies.single_movie

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.murad.mvvmmoviws.R
import com.murad.mvvmmoviws.movies.auth.vo.UserInfoX
import com.murad.mvvmmoviws.movies.data.api.MovieDbInterface
import com.murad.mvvmmoviws.movies.data.repository.NetworkState
import com.murad.mvvmmoviws.movies.data.vo.movie_details
import com.murad.mvvmmoviws.movies.main.App
import com.murad.mvvmmoviws.movies.user_actions.api.api
import com.murad.mvvmmoviws.movies.user_actions.vo.UserList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_single_movie.view.*
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class Single_movie : Fragment() {

    private  val TAG = "Single_movie"

    private lateinit var singleMovieViewModel: SingleMovieViewModel
    private lateinit var movieDetailsRepository: MovieDetailsRepository

    @Inject
    @Named("MovieDbInterface")
    lateinit var apiService:MovieDbInterface

    @Inject
    lateinit var apiListService:api

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.activity_single_movie,container,false)

        //movie id (test)
        var movieId=arguments?.getString("movie_id")?.toInt()

        val sharedPref=requireActivity().getSharedPreferences(App().USER_SHARED_PREF,Context.MODE_PRIVATE)
        val gson=Gson()
        val userObject=gson.fromJson(sharedPref.getString("userinfo",""),UserInfoX::class.java)

        val userId=userObject._id

        val userlistRequest=UserList(userId,movieId.toString())



        Log.d(TAG, "onCreate: ${movieId}")



       //  apiService=TheMovieDbClient.getClient()
        movieDetailsRepository= MovieDetailsRepository(apiService,apiListService)

        singleMovieViewModel=getViewModel(movieId!!,userlistRequest)

        singleMovieViewModel.movieDetails.observe(requireActivity(),
            {
                Log.d(TAG, "onCreateView: ${it}")
                if (it != null)
                    bindUi(it,view)
            })

        singleMovieViewModel.networkState.observe(requireActivity(), Observer {
            view.progress_bar.visibility=if(it== NetworkState.LOADING) View.VISIBLE else View.GONE
            view.txt_error.visibility=if(it==NetworkState.ERROR)View.VISIBLE else View.GONE
        })

        view.trailerBtn.setOnClickListener {

            getView()?.findNavController()?.navigate(Single_movieDirections.actionSingleMovieToTrailerActivity(movieId))

        }

        view.addToMyList.setOnClickListener {


            singleMovieViewModel.addToUserList()

            singleMovieViewModel.movieAdded.observe(requireActivity(),{
                if(it)
                     Toast.makeText(requireContext(),"Added To Your List",Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(requireContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show()

            })


        }

        return view
    }



    fun bindUi(movieDetails: movie_details,view: View){
        view.movie_title.text=movieDetails.title
        view.movie_tagline.text=movieDetails.tagline
        view.movie_release_date.text=movieDetails.release_date
        view.movie_rating.text=movieDetails.vote_average.toString()
        view.movie_overview.text=movieDetails.overview
        view.movie_runtime.text=movieDetails.runtime.toString()
        view.movie_budget.text=movieDetails.budget.toString()+" USD"
        view.movie_revenue.text=movieDetails.revenue.toString() +" USD"



        val moviePoster= App().POSTER_IMG +movieDetails.poster_path
        Glide.with(this)
            .load(moviePoster)
            .into(view.iv_movie_poster)


        Glide.with(this)
            .load(moviePoster)
            .into(view.imageView3)

    }

    //this method to pass data to ViewModel, we can not send data to view model using normal providers method so we had to use ViewModelProvider.Factory
    fun getViewModel(movieId: Int, userlistRequest: UserList): SingleMovieViewModel {


        return ViewModelProviders.of(this,object :ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {

                return SingleMovieViewModel(movieDetailsRepository,movieId,userlistRequest) as T

            }

        })[SingleMovieViewModel::class.java] // to cast from viewModelProvider to SingleViewModel

    }
}