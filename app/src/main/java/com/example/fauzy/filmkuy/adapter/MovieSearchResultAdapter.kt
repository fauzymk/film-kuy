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
class MovieSearchResultHolder(view: View, var movie: Movie? = null) : RecyclerView.ViewHolder(view) {
    var title: TextView = view.findViewById(R.id.movie_title)
    var rating: TextView = view.findViewById(R.id.movie_rating)
    var releaseDate: TextView = view.findViewById(R.id.movie_release_date)
    var overview: TextView = view.findViewById(R.id.movie_overview)
    var image: ImageView = view.findViewById(R.id.movie_poster)

    init {
        view.setOnClickListener {
            val intent: Intent = Intent(view.context, MovieDetails::class.java)
            Log.d("data", "data $movie")
            intent.putExtra(MOVIE_DATA, movie)
            view.context.startActivity(intent)
        }
    }
}

class MovieSearchResultAdapter(var movies: ArrayList<Movie>) : RecyclerView.Adapter<MovieSearchResultHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSearchResultHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_search_item, parent, false)

        return MovieSearchResultHolder(view)
    }

    override fun getItemCount() = movies.size

    fun refreshAdapter(movies: ArrayList<Movie>) {
        this.movies.addAll(movies)
        notifyItemRangeChanged(0, this.movies.size)
    }

    override fun onBindViewHolder(holder: MovieSearchResultHolder, position: Int) {
        val movieItem: Movie = movies[position]

        Picasso
                .get()
                .load("https://image.tmdb.org/t/p/w500${movieItem.poster}")
                .error(R.mipmap.ic_launcher)
                .placeholder(R.color.material_grey_300)
                .into(holder.image)

        holder.title.text = movieItem.title
        holder.rating.text = movieItem.vote_average.toString()
        holder.releaseDate.text = movieItem.release_date
        holder.overview.text = movieItem.overview
        holder.movie = movieItem
    }
}