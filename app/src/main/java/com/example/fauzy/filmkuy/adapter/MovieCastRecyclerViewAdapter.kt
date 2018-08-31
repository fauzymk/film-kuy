package com.example.fauzy.filmkuy.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.fauzy.filmkuy.R
import com.example.fauzy.filmkuy.model.MovieCast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cast_item.view.*

class MovieCastRecyclerViewHolder(view: View, var cast: MovieCast? = null) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.cast_name
    val role: TextView = view.cast_role
    val castPicture: ImageView = view.cast_picture
}

class MovieCastRecyclerViewAdapter(val casts: MutableList<MovieCast>) : RecyclerView.Adapter<MovieCastRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCastRecyclerViewHolder {
        return MovieCastRecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cast_item, parent, false))
    }

    override fun getItemCount(): Int = casts.size

    override fun onBindViewHolder(holder: MovieCastRecyclerViewHolder, position: Int) {
        val cast: MovieCast = casts[position]

        Picasso
                .get()
                .load("https://image.tmdb.org/t/p/w500${cast.prof_pic}")
                .error(R.mipmap.ic_launcher)
                .placeholder(R.color.material_blue_grey_800)
                .into(holder.castPicture)

        holder.name.text = cast.name
        holder.role.text = cast.character
    }
}