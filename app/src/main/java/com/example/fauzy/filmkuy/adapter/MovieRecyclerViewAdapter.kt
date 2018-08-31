package com.example.fauzy.filmkuy.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.fauzy.filmkuy.MOVIE_DATA
import com.example.fauzy.filmkuy.MovieDetails
import com.example.fauzy.filmkuy.R
import com.example.fauzy.filmkuy.model.Movie
import com.squareup.picasso.Picasso

/**
 * Created by fauzy on 17/07/18.
 */
class MovieRecyclerViewHolder(view: View, var movie: Movie? = null) : RecyclerView.ViewHolder(view) {
    var title: TextView = view.findViewById(R.id.movie_title)
    var rating: TextView = view.findViewById(R.id.movie_rating)
    var image: ImageView = view.findViewById(R.id.movie_thumbnail)

    init {
        view.setOnClickListener {
            val intent: Intent = Intent(view.context, MovieDetails::class.java)
            Log.d("data", "data $movie")
            intent.putExtra(MOVIE_DATA, movie)
            view.context.startActivity(intent)
        }
    }
}

class MovieRecyclerViewAdapter(var movies: ArrayList<Movie>) : RecyclerView.Adapter<MovieRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)

        return MovieRecyclerViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieRecyclerViewHolder, position: Int) {
        val movieItem: Movie = movies[position]

        Picasso
                .get()
                .load("https://image.tmdb.org/t/p/w500${movieItem.poster}")
                .error(R.mipmap.ic_launcher)
                .placeholder(R.color.material_grey_300)
                .into(holder.image)

        holder.title.text = movieItem.title
        holder.rating.text = movieItem.vote_average.toString()
        holder.movie = movieItem
    }
}