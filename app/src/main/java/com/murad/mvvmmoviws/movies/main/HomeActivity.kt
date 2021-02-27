package com.murad.mvvmmoviws.movies.main

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.murad.mvvmmoviws.R
import com.murad.mvvmmoviws.movies.auth.vo.UserInfoX
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private  val TAG = "HomeActivity"

    @Inject
    lateinit var sharedPreferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navView=findViewById<BottomNavigationView>(R.id.BottomNavigation)

        val navController=findNavController(R.id.fragment)

        val appBarConfig= AppBarConfiguration(
            setOf(
                R.id.home_fragment,R.id.tv_Shows_Fragment,R.id.search_View,R.id.profile_View
            )
        )

       // setupActionBarWithNavController(navController,appBarConfig)

        navView.setupWithNavController(navController)

        setUserName()



    }

    fun setUserName(){

       //  sharedPreferences=this.getSharedPreferences("USER_PREF",Context.MODE_PRIVATE)


            val gson=Gson()

            val userObject=gson.fromJson(sharedPreferences.getString("userinfo",""),UserInfoX::class.java)

        Log.d(TAG, "setUserName: ${userObject}")
           userName.text=userObject.nickName


    }

}