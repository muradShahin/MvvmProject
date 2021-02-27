package com.murad.mvvmmoviws.movies.single_movie.trailers

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.murad.mvvmmoviws.R
import com.murad.mvvmmoviws.movies.data.vo.Trailer
import kotlinx.android.synthetic.main.trailers_row.view.*

class TrailerAdapter(val trailersActivity: Trailer_Activity,val trailers:List<Trailer>):RecyclerView.Adapter<TrailerAdapter.ViewHolder>() {

    private  val TAG = "TrailerAdapter"
    class ViewHolder(val view:View) :RecyclerView.ViewHolder(view){



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).
                 inflate(R.layout.trailers_row,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val trailer=trailers[position]


         holder.view.name_trailer.text=trailer.name

        val trailerImage="https://img.youtube.com/vi/${trailer.key}/hqdefault.jpg"

        Glide.with(trailersActivity.requireContext())
            .load(trailerImage)
            .into(holder.view.imageView4)

        Log.d(TAG, "onBindViewHolder: $trailerImage")

        holder.view.setOnClickListener{
            val intent=Intent(trailersActivity.requireActivity(),play_trailer::class.java)
            intent.putExtra("trailer_key",trailer.key)
            trailersActivity.startActivity(intent)

        }


    }

    override fun getItemCount(): Int {

        return trailers.size

    }
}