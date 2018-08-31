package com.example.fauzy.filmkuy

import com.example.fauzy.filmkuy.model.Movie
import com.example.fauzy.filmkuy.model.MovieCastResponse
import com.example.fauzy.filmkuy.model.MovieResponse
import com.example.fauzy.filmkuy.model.MovieSearchResponse
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by fauzy on 16/07/18.
 */
interface MovieApi {
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String) : Observable<MovieResponse>


    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") id: Int, @Query("api_key") apiKey: String, @Query("append_to_response") append: String = "videos,credits") : Observable<Movie>

    @GET("search/movie")
    fun searchMovie(@Query("api_key") apiKey: String, @Query("query") query: String?, @Query("page") page: Int) : Observable<MovieSearchResponse>

}