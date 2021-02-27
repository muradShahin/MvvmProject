package com.murad.mvvmmoviws.movies.login.signUpFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.observe
import com.murad.mvvmmoviws.R
import com.murad.mvvmmoviws.movies.auth.api.LoginApi
import com.murad.mvvmmoviws.movies.auth.vo.SignUpRequest
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import javax.inject.Inject
import javax.inject.Named


class signUp : Fragment() {


    private lateinit var viewModel:SignUpViewModel
    private  val TAG = "signUp"

    @Inject
    @Named("loginApi")
    lateinit var apiService:LoginApi

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
        val view= inflater.inflate(R.layout.fragment_sign_up, container, false)


        init(view)

        return view
    }

    private fun init(view:View){





        view.button.setOnClickListener {

            val signUpRequest=SignUpRequest(
                view.email.text.toString(),
                view.password.text.toString(),
                view.nickname.text.toString(),
                view.gender.text.toString(),
                view.date.text.toString()
            )

            val signUpLocalRepository=SignUpLocalRepository(apiService,signUpRequest)


            viewModel= SignUpViewModel(signUpLocalRepository)


            viewModel.signUpResponse.observe(requireActivity(), {

                Log.d(TAG, "init: ${it.result}")
                if(it.result =="1"){
                    Toast.makeText(requireActivity(),"Created Successfully !",Toast.LENGTH_SHORT).show()
                    view.email.text.clear()
                    view.password.text.clear()
                    view.nickname.text.clear()
                    view.gender.text.clear()
                    view.date.text.clear()
                }else{
                    Toast.makeText(requireActivity(),"Failed !",Toast.LENGTH_SHORT).show()

                }

            })


            viewModel.networkStateResponse.observe(requireActivity(),{

            })
        }

    }


}