package com.murad.mvvmmoviws.search.moviesSearch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.murad.mvvmmoviws.R
import com.murad.mvvmmoviws.movies.data.repository.NetworkState
import com.murad.mvvmmoviws.movies.data.vo.Result
import com.murad.mvvmmoviws.movies.main.App
import com.murad.mvvmmoviws.movies.popular_movies.upcoming.UpComingPagedListAdapter
import kotlinx.android.synthetic.main.activity_single_movie.view.*
import kotlinx.android.synthetic.main.movie_row_search.view.*
import kotlinx.android.synthetic.main.movie_row_search.view.movie_title

class SearchAdapter(val context:Context,val fragment:Search_View) :PagedListAdapter<Result,RecyclerView.ViewHolder>(DiffUtilClass()) {
    
    val MOVIE_ROW=1
    val LOADING_ROW=2
    
    var networkState :NetworkState?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if(viewType == MOVIE_ROW){

            MovieViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.movie_row_search,parent,false) as View
            )
        }else{

            LoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.network_search_row,parent,false) as View
            )
        }
        
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        
        if(getItemViewType(position)==MOVIE_ROW){
            
            (holder as MovieViewHolder).binds(context,getItem(position))
        }else{
            (holder as LoadingViewHolder).binds(context)

        }
        
    }

    override fun getItemCount(): Int {
        return super.getItemCount() +(if(hasExtraRow()) 1 else 0)
    }

    override fun getItemViewType(position: Int): Int {

        return if(hasExtraRow() && position==itemCount-1)
            LOADING_ROW
        else
            MOVIE_ROW
        
    }
    
    private fun hasExtraRow():Boolean{
        return networkState !=null && networkState != NetworkState.LOADING
    }
    
    
    inner class MovieViewHolder(val view:View) :RecyclerView.ViewHolder(view){
        fun binds(context: Context, item: Result?) {

            itemView.movie_title.text=item?.title

            val posterUrlImage=App().POSTER_IMG+item?.poster_path

            Glide.with(context)
                .load(posterUrlImage)
                .into(itemView.imageView)

            itemView.popularity.text="Popularity : "+item?.popularity.toString()
            itemView.language.text=item?.original_language
            itemView.vote.text="Rate : "+item?.vote_average.toString()


            itemView.setOnClickListener {

                fragment.goToMovieDetails(item?.id!!)
            }


        }


    }
    
    inner class LoadingViewHolder(val view: View) :RecyclerView.ViewHolder(view){
        fun binds(context: Context) {

            if (networkState == NetworkState.LOADING) {

                itemView.visibility = View.VISIBLE
            } else{
                itemView.visibility=View.GONE
            }
        }

    }
    
    class DiffUtilClass:DiffUtil.ItemCallback<Result>(){
       override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
           return oldItem == newItem
       }

       override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
           return oldItem == newItem
       }

   }

    fun setNetWorkState(networkState: NetworkState){

        val prevNetWorkState=this.networkState
        val hadExtraRow=hasExtraRow()
        val hasExtraRow=hasExtraRow()

        if(hadExtraRow != hasExtraRow){

              if(hadExtraRow){
                   notifyItemRemoved(super.getItemCount())
              }else{
                  notifyItemInserted(super.getItemCount())
              }

        }else if(hasExtraRow && prevNetWorkState !=this.networkState){

               notifyItemChanged(super.getItemCount()-1)
        }

    }

}