package com.murad.mvvmmoviws.movies.login.loginFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.murad.mvvmmoviws.R
import com.murad.mvvmmoviws.movies.auth.api.LoginApi
import com.murad.mvvmmoviws.movies.auth.vo.UserInfoX
import com.murad.mvvmmoviws.movies.auth.vo.loginRequest
import com.murad.mvvmmoviws.movies.data.repository.NetworkState
import com.murad.mvvmmoviws.movies.main.App
import com.murad.mvvmmoviws.movies.main.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login_.view.*
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class Login_Fragment : Fragment() {

    private lateinit var email:String
    private lateinit var password:String

    private lateinit var loginRepository: LoginLocalRepository
    private lateinit var viewModel: LoginViewModel

    @Inject
    @Named("loginApi")
    lateinit var loginApi:LoginApi
    private  val TAG = "Login_Fragment"


    private var emailFromSign:String?=null
    private var passFromSign:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        emailFromSign=arguments?.getString("email")
        passFromSign=arguments?.getString("password")


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_login_, container, false)


        init(view)


        return view
    }

    fun init(view: View){


        checkIfFromSignUp(view)
        checkForCurrentUser()


        view.login.setOnClickListener {


            if(validateEmail(view)){

                email=view.email.text.toString()
                password=view.password.text.toString()

                val loginRequest = loginRequest(email, password)


                loginRepository= LoginLocalRepository(loginApi, loginRequest)


                viewModel=LoginViewModel(loginRepository)



                viewModel._userInfoResponse.observe(requireActivity(), {

                    if (it.userInfo != null) {

                        saveCurrentUser(it.userInfo)
                        
                    } else {
                        Toast.makeText(requireContext(), "incorrect creditals", Toast.LENGTH_SHORT)
                            .show()
                    }

                })

                viewModel.getNetWorkState.observe(requireActivity(), {

                    if (it == NetworkState.LOADING) {

                    } else {

                    }
                })



            }else{
                Toast.makeText(requireActivity(), "Incorrect credintals ", Toast.LENGTH_SHORT).show()
            }


        }

        view.signUp.setOnClickListener {

            view.findNavController().navigate(Login_FragmentDirections.actionLoginFragmentToSignUp())


        }



    }

    private fun checkForCurrentUser() {

        //loading should start

        val sharedPreferences=requireActivity().getSharedPreferences(App().USER_SHARED_PREF,Context.MODE_PRIVATE)
        val gson=Gson()
        val userObject=gson.fromJson(sharedPreferences.getString("userinfo",null),UserInfoX::class.java)

        if(userObject!=null){

            val intent =Intent(requireActivity(),HomeActivity::class.java)
            startActivity(intent)
            requireActivity().finish()

        }


    }

    private fun saveCurrentUser(userInfo: UserInfoX) {

        val gson=Gson()
        var json=gson.toJson(userInfo)


        val sharedPreferences=requireActivity().getSharedPreferences(App().USER_SHARED_PREF,Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString("userinfo", json)
            .apply()

        Log.d(TAG, "saveCurrentUser: ${sharedPreferences.getString("userinfo","")}")

        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()

        //to retrive
//        val gson = Gson()
//        val json: String = mPrefs.getString("MyObject", "")
//        val obj: MyObject = gson.fromJson(json, MyObject::class.java)
    }

    private fun validateEmail(view: View):Boolean{

        return  view.email.text.toString().isNotEmpty()
                   && view.email.text.toString().contains("@")
                && view.email.text.toString().contains(".com")

    }

    //this method caused an error in login because when data is incorrect it takes the old data over and over
    private fun getViewModel():LoginViewModel{
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {

                return LoginViewModel(loginRepository) as T
            }


        })[LoginViewModel::class.java]
    }


    private fun checkIfFromSignUp(view: View){


        if(emailFromSign !=null){



            view.email.setText(emailFromSign)
            view.password.setText(passFromSign)
        }

    }


}