package com.murad.mvvmmoviws.movies.popular_movies.upcoming

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
import com.murad.mvvmmoviws.movies.popular_movies.home_fragment
import com.murad.mvvmmoviws.data.repository.NetworkState
import com.murad.mvvmmoviws.data.vo.ResultX
import com.murad.mvvmmoviws.movies.main.App
import kotlinx.android.synthetic.main.network_state.view.*
import kotlinx.android.synthetic.main.up_coming_movie_row.view.*

class UpComingPagedListAdapter(val context: Context,val fragment: Fragment):PagedListAdapter<ResultX,RecyclerView.ViewHolder>(movieDeffCallBack()) {

    val MOVIE_VIEW_TYPE=1
    val NETWORK_VIEW_TYPE=2
    private var networkState:NetworkState?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater=LayoutInflater.from(parent.context)
        val view:View

        if(viewType==MOVIE_VIEW_TYPE){

            view=layoutInflater.inflate(R.layout.up_coming_movie_row,parent,false)

            return UpComingMovie(view)


        }else{

            view=layoutInflater.inflate(R.layout.network_state,parent,false)

            return NetWorkViewHolder(view)

        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(getItemViewType(position)==MOVIE_VIEW_TYPE){

            (holder as UpComingMovie).binds(getItem(position),context)
        }else{

            (holder as NetWorkViewHolder).binds(networkState,context)
        }
    }


    class movieDeffCallBack:DiffUtil.ItemCallback<ResultX>(){
        override fun areItemsTheSame(oldItem: ResultX, newItem: ResultX): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: ResultX, newItem: ResultX): Boolean {
            return oldItem==newItem
        }

    }

    fun hasExtraRow():Boolean{
        return networkState !=null && networkState !=NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if(hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if(hasExtraRow() && position==itemCount-1){
                NETWORK_VIEW_TYPE
           }else{
            MOVIE_VIEW_TYPE
        }
    }

    class NetWorkViewHolder(val view:View):RecyclerView.ViewHolder(view){

        fun binds(networkState: NetworkState?, context: Context) {

            if(networkState ==NetworkState.LOADING ){

                view.progressBar.visibility=View.VISIBLE
            }else{

                view.progressBar.visibility=View.GONE
            }
        }

    }

    inner class UpComingMovie(val view:View):RecyclerView.ViewHolder(view){

        fun binds(item: ResultX?, context: Context) {

            view.movie_title.text=item?.title

            val imag_path= App().POSTER_IMG+item?.poster_path
            Glide.with(context)
                .load(imag_path)
                .into(view.imageView)


            itemView.animation=AnimationUtils.loadAnimation(context,R.anim.up_from_bottom)

            itemView.setOnClickListener{

//                val intent= Intent(context, Single_movie::class.java)
//                intent.putExtra("movie_id",item?.id)
//                context.startActivity(intent)
                (fragment as home_fragment).goToMovieDetails(item!!.id)
            }

        }

    }

    fun serNetWorkState(newNetWorkState: NetworkState){

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