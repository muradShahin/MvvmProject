package com.murad.mvvmmoviws.localDb.entites

import androidx.room.*

@Entity
data class Fav (
    @PrimaryKey(autoGenerate = false)
    val show_id : Int,
    val show_img : String,
    val show_name : String

)