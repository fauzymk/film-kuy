package com.example.fauzy.filmkuy.model

import com.example.fauzy.filmkuy.model.Movie

/**
 * Created by fauzy on 16/07/18.
 */
data class MovieResponse(var page: Int, val results: ArrayList<Movie>, val total_result: Int, val total_page: Int)