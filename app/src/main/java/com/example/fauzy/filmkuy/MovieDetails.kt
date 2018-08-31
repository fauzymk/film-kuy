package com.example.fauzy.filmkuy

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.example.fauzy.filmkuy.adapter.MovieCastRecyclerViewAdapter
import com.example.fauzy.filmkuy.model.Movie
import com.example.fauzy.filmkuy.model.MovieCast
import com.google.android.youtube.player.YouTubePlayerFragment
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.picasso.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_movie_details.*
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer



class MovieDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val movieApi: MovieApi = MovieApiClient.getClient().create(MovieApi::class.java)

        cast_list.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)

        val movie = intent.extras.getParcelable<Movie>(MOVIE_DATA) as Movie

        setSupportActionBar(movie_detail_toolbar)
        supportActionBar?.title = movie.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Picasso
                .get()
                .load("https://image.tmdb.org/t/p/w500${movie.backdrop}")
                .transform(BlurTransformation(application, 20, 1))
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.color.material_grey_300)
                .into(movie_backdrop)

        Picasso
                .get()
                .load("https://image.tmdb.org/t/p/w500${movie.poster}")
                .error(R.mipmap.ic_launcher)
                .placeholder(R.color.material_grey_300)
                .into(movie_poster)

        movie_title.text = movie.title
        movie_overview.text = movie.overview

        showMovieCasts(movie.id, movieApi)
    }

    private fun showMovieCasts(movieId: Int, movieApi: MovieApi) {
        movieApi
                .getMovieDetails(movieId, "d4d1ae89afd12d33b74402bc930392f1")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.d("DATA", "VIDEOS: ${it.videos!!.videos[0].key}")
                    var casts: MutableList<MovieCast> = it.credits!!.cast.toMutableList()
                    cast_list.adapter = MovieCastRecyclerViewAdapter(casts.subList(0, 9))

                    val trailerFragment: YouTubePlayerFragment = fragmentManager.findFragmentById(R.id.trailer_fragment) as YouTubePlayerFragment

                    trailerFragment.initialize("AIzaSyBHOHidMCfY-ufdq6nnjo5fRCF2HeDFZRY",
                            object : YouTubePlayer.OnInitializedListener {
                                override fun onInitializationSuccess(provider: YouTubePlayer.Provider,
                                                                     youTubePlayer: YouTubePlayer, b: Boolean) {
                                    // do any work here to cue video, play video, etc.
                                    youTubePlayer.cueVideo(it.videos!!.videos[0].key)
                                }

                                override fun onInitializationFailure(provider: YouTubePlayer.Provider,
                                                                     youTubeInitializationResult: YouTubeInitializationResult) {

                                }
                            })


                    loading_cast.visibility = View.INVISIBLE
                }, {
                    Log.e("ERROR", "Error occurred: ${it.message}")
                })
    }

}
