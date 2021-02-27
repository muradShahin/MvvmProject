package com.murad.mvvmmoviws.movies.popular_movies.trending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.murad.mvvmmoviws.R
import com.murad.mvvmmoviws.movies.popular_movies.home_fragment
import com.murad.mvvmmoviws.movies.data.vo.ResultXX
import com.murad.mvvmmoviws.movies.main.App
import kotlinx.android.synthetic.main.trending_row.view.*

class TrendingRecyclerView(val fragment:home_fragment):RecyclerView.Adapter<TrendingRecyclerView.MovieViewHolder>() {

    var movies=ArrayList<ResultXX>()


    class MovieViewHolder(val view: View):RecyclerView.ViewHolder(view){




    }

    fun submitList(list:ArrayList<ResultXX>){
        movies=list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.trending_row,parent,false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie=movies[position]

        holder.view.movie_title.text=movie.title

        val posterUrl= App().POSTER_IMG+movie.poster_path
        Glide.with(fragment)
            .load(posterUrl)
            .into(holder.view.imageView)


        holder.itemView.setOnClickListener{

//            val intent=Intent(fragment.context,Single_movie::class.java)
//            intent.putExtra("movie_id",movie.id)
//            fragment.context?.startActivity(intent)

            fragment.goToMovieDetails(movie.id)
        }

    }

    override fun getItemCount(): Int {
        return movies.size
    }
}
