package com.murad.mvvmmoviws.movies.popular_movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.murad.mvvmmoviws.R
import com.murad.mvvmmoviws.movies.data.repository.NetworkState
import com.murad.mvvmmoviws.movies.data.vo.Result
import com.murad.mvvmmoviws.movies.main.App
import kotlinx.android.synthetic.main.movie_row.view.*
import kotlinx.android.synthetic.main.movie_row.view.movie_title

class MoviePagedListAdapter( val context: Context,val fragment: Fragment):PagedListAdapter<Result,RecyclerView.ViewHolder>(movieDeffCallBack()) {

    val MOVIE_VIEW_TYPE=1
    val NETWORK_VIEW_TYPE=2

    private var networkState:NetworkState?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        var view:View

        if(viewType==MOVIE_VIEW_TYPE){
            view=layoutInflater.inflate(R.layout.movie_row,parent,false)
            return MovieViewHolder(view)
        }else{
            view=LayoutInflater.from(parent.context).inflate(R.layout.network_state,parent,false)
            return NetworkViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(getItemViewType(position)==MOVIE_VIEW_TYPE){

            (holder as MovieViewHolder).binds(getItem(position)!!,context)
        }else{
            (holder as NetworkViewHolder).binds(networkState!!,context)
        }
    }

    fun hasExtraRow():Boolean{

        return networkState !=null && networkState!=NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if(hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if(hasExtraRow() && position==itemCount-1) {
           NETWORK_VIEW_TYPE
        }else{
            MOVIE_VIEW_TYPE
        }

    }

    class movieDeffCallBack:DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {

            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {

            return oldItem.id ==newItem.id
        }

    }

    inner class MovieViewHolder(view:View):RecyclerView.ViewHolder(view){

        fun binds(movie:Result,context: Context){
            itemView.movie_title.text=movie?.title

            val movie_poster=App().POSTER_IMG+movie.poster_path
            Glide.with(context)
                .load(movie_poster)
                .into(itemView.imageView)


            itemView.setOnClickListener{

                (fragment as home_fragment).goToMovieDetails(movie.id)


            }

           // itemView.animation=AnimationUtils.loadAnimation(context,R.anim.up_from_bottom)


        }
    }

    class NetworkViewHolder(view:View):RecyclerView.ViewHolder(view){

        fun binds(network:NetworkState,context: Context) {

            if (network == NetworkState.LOADING) {

                itemView.visibility = View.VISIBLE
            } else{
                itemView.visibility=View.GONE
            }




        }


    }

    fun serNetWorkState(newNetWorkState:NetworkState){

        val previousState=this.networkState
        val hadExtraRow=hasExtraRow()
        val hasExtraRow=hasExtraRow()
        this.networkState=newNetWorkState

        if(hadExtraRow != hasExtraRow){
            if(hadExtraRow){
                notifyItemRemoved(super.getItemCount())
            }else{
                notifyItemInserted(super.getItemCount())
            }
        }else if(hasExtraRow && previousState!=networkState){
            notifyItemChanged(super.getItemCount()-1)
        }
    }
}