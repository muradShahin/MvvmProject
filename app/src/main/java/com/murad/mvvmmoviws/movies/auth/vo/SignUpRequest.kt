package com.murad.mvvmmoviws.movies.auth.vo

data class SignUpRequest(
     var email:String,
     var password:String,
     var nickname:String,
     var gender:String,
     var dob:String
)