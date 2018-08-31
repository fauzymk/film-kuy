package com.example.fauzy.filmkuy

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.example.fauzy.filmkuy.adapter.MovieRecyclerViewAdapter
import com.example.fauzy.filmkuy.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

internal const val MOVIE_DATA = "MOVIE_DATA"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movieApi: MovieApi = MovieApiClient.getClient().create(MovieApi::class.java)

//        recycler_view.layoutManager = GridLayoutManager(this, 2)
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)

        showPopularMovies(movieApi)

        testBtn.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
//            intent.action = Intent.ACTION_VIEW
//            intent.data = Uri.parse("http://api-ukm.xyz")

            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_go_to_search, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.search_button -> {
                startActivity(Intent(this, SearchActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showPopularMovies(movieApi: MovieApi) {

        movieApi.getPopularMovies("d4d1ae89afd12d33b74402bc930392f1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.d("MOVIES", "Popular movies: $result")
                            var movies: ArrayList<Movie> = result.results
                            recycler_view.adapter = MovieRecyclerViewAdapter(movies)
                            loading_cast.visibility = View.INVISIBLE
                        },
                        { error ->
                            Log.e("ERROR", "Error fetching movies")
                        }
                )
    }
}
