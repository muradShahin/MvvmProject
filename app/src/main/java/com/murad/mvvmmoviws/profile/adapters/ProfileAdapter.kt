package com.murad.mvvmmoviws.profile.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.murad.mvvmmoviws.R
import com.murad.mvvmmoviws.localDb.entites.Fav
import com.murad.mvvmmoviws.profile.Profile_View
import com.murad.mvvmmoviws.search.api.ApiDbClient.POSTER_IMG
import kotlinx.android.synthetic.main.fav_list.view.*
import kotlinx.android.synthetic.main.tv_details_show.view.*

class ProfileAdapter(val fragment:Profile_View) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {
   private var list = ArrayList<Fav>()

    class ViewHolder(val layout:ConstraintLayout) : RecyclerView.ViewHolder(layout){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fav_list,parent,false) as ConstraintLayout)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item= list[position]

        holder.itemView.imageView8.setOnClickListener {

            fragment.deleteFromList(item)

        }

        val imagePoster=POSTER_IMG+item.show_img
        Glide.with(fragment.requireContext())
            .load(imagePoster)
            .into(holder.itemView.imageView7)

        holder.itemView.setOnClickListener {


        }



    }

    fun updateList(newList: List<Fav>){

         list=newList as ArrayList<Fav>
        notifyDataSetChanged()
    }
}