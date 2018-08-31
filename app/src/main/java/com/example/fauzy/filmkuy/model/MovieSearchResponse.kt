package com.example.fauzy.filmkuy.model

import com.google.gson.annotations.SerializedName

data class MovieSearchResponse (val page: Int, val total_results: Int, val total_pages: Int, @SerializedName("results") val movies: MutableList<Movie>)