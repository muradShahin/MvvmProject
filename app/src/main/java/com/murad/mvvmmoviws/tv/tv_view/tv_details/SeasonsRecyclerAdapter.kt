package com.murad.mvvmmoviws.tv.tv_view.tv_details

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.murad.mvvmmoviws.R
import com.murad.mvvmmoviws.movies.main.App
import com.murad.mvvmmoviws.tv.data.vo.Season

class SeasonsRecyclerAdapter(val fragment :Tv_Show_Details,val seasonsList:List<Season>) :RecyclerView.Adapter<SeasonsRecyclerAdapter.SeasonsViewHolder>() {



    inner class SeasonsViewHolder(val lay:View):RecyclerView.ViewHolder(lay){

        val seasonImage=lay.findViewById<ImageView>(R.id.imageView5)
        val seasonName=lay.findViewById<TextView>(R.id.season_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonsViewHolder {

        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.season_row,parent,false)

        return SeasonsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeasonsViewHolder, position: Int) {

        val posterUrl= App().POSTER_IMG+seasonsList[position].poster_path
        Glide.with(fragment.requireActivity())
            .load(posterUrl)
            .into(holder.seasonImage)

        holder.seasonName.text=seasonsList[position].name +"(${seasonsList[position].season_number})"

        Log.d("seasonId", "onBindViewHolder: ${seasonsList[position].id}")


        holder.itemView.setOnClickListener {
            //fragment.showSeason(seasonsList[position].id)
        }


    }

    override fun getItemCount(): Int {
       return seasonsList.size
    }
}