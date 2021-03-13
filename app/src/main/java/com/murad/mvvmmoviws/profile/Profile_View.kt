package com.murad.mvvmmoviws.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.murad.mvvmmoviws.R
import com.murad.mvvmmoviws.localDb.entites.Fav
import com.murad.mvvmmoviws.profile.adapters.ProfileAdapter
import com.murad.mvvmmoviws.profile.repository.ProfileRepository
import com.murad.mvvmmoviws.profile.viewModels.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile__view.*
import kotlinx.android.synthetic.main.fragment_profile__view.view.*


class Profile_View : Fragment() {


    private lateinit var viewModel: ProfileViewModel
    private lateinit var profieAdapter : ProfileAdapter
    private  val TAG = "Profile_View"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {



        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_profile__view, container, false)


        viewModel=getViewModel()
        profieAdapter= ProfileAdapter(this)


        view.fav_list.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter=profieAdapter
        }


        viewModel.getFavList()
        viewModel.favouriteList.observe(requireActivity()) {

            profieAdapter.updateList(it)
        }


        return view
    }

    private fun getViewModel():ProfileViewModel{

        return ViewModelProviders.of(this,object :ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {

                return ProfileViewModel(ProfileRepository(requireContext())) as T
            }


        })[ProfileViewModel::class.java]
    }


    fun deleteFromList(fav: Fav){

        viewModel.deleteFromList(fav)

        viewModel.deletedResult.observe(requireActivity()) {

            if(it){
                viewModel.getFavList()
            }else{
                Toast.makeText(requireContext()," Could not be deleted",Toast.LENGTH_SHORT).show()
            }

        }

    }


}