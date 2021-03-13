package com.murad.mvvmmoviws.tv.tv_view.popularShows

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.murad.mvvmmoviws.R
import com.murad.mvvmmoviws.data.repository.NetworkState
import com.murad.mvvmmoviws.movies.main.App
import com.murad.mvvmmoviws.tv.data.vo.Result
import kotlinx.android.synthetic.main.movie_row.view.*
import kotlinx.android.synthetic.main.network_state.view.*

class TvShowsAdapter(val context: Context,val fragment: Fragment):PagedListAdapter<Result,RecyclerView.ViewHolder>(TvDiffCallBack()) {

    val TV_SHOW=1
    val LOADING=2

    private var networkState:NetworkState?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var view :View
            if(viewType==TV_SHOW){
               view= LayoutInflater.from(parent.context).inflate(R.layout.tv_show_row,parent,false)
                return TvShowViewHolder(view)
            }else{
                view= LayoutInflater.from(parent.context).inflate(R.layout.network_state,parent,false)
                return LoadingViewHolder(view)
            }



    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(getItemViewType(position) == TV_SHOW){
            (holder as TvShowViewHolder).binds(context, getItem(position)!!)

        }else{
            (holder as LoadingViewHolder).binds(networkState!!)

        }


    }

    fun hasExtraRow():Boolean{
        return networkState !=null && networkState != NetworkState.LOADING
    }

    override fun getItemCount(): Int {
        return super.getItemCount() +(if(hasExtraRow())1 else 0)
    }

    override fun getItemViewType(position: Int): Int {

        if(hasExtraRow() && position==itemCount-1){
            return LOADING
        }else{
            return TV_SHOW
        }

    }




     class TvDiffCallBack:DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem==newItem
        }

    }


    inner class TvShowViewHolder(val view:View):RecyclerView.ViewHolder(view){

        fun binds(context: Context,tvShow:Result){

            itemView.movie_title.text=tvShow.name

            val imagePoster= App().POSTER_IMG+tvShow.poster_path
            Glide.with(context)
                .load(imagePoster)
                .into(itemView.imageView)

            itemView.animation=AnimationUtils.loadAnimation(context,R.anim.up_from_bottom)

            itemView.setOnClickListener {
                (fragment as Tv_Shows_Fragment).showTvDetails(tvShow.id)
            }


        }

    }

    class LoadingViewHolder(val view:View):RecyclerView.ViewHolder(view){

        fun binds(networkState: NetworkState){

            if(networkState ==NetworkState.LOADING){
                itemView.progressBar.visibility=View.VISIBLE
            }else{
                itemView.progressBar.visibility=View.GONE

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