package com.example.fauzy.filmkuy

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import com.example.fauzy.filmkuy.adapter.MovieSearchResultAdapter
import com.example.fauzy.filmkuy.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*
import kotlin.properties.Delegates

class SearchActivity : AppCompatActivity() {

    private var searchView: SearchView? = null
    private var page by Delegates.notNull<Int>()
    private var currQuery by Delegates.notNull<String>()
    private var totalPage by Delegates.notNull<Int>()
    private var movieSearchAdapter by Delegates.notNull<MovieSearchResultAdapter>()
    private val movieApi: MovieApi = MovieApiClient.getClient().create(MovieApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        page = 1
        totalPage = 0

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        movie_search_result.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val ly = LinearLayout(this)

        movie_search_result.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

                val lm = movie_search_result.layoutManager as LinearLayoutManager

                val count: Int = lm.itemCount
                val lastVisible = lm.findLastCompletelyVisibleItemPosition()
                val isLast = count.minus(1) === lastVisible

                if (isLast && page < totalPage) {
                    page = page.let { it.plus(1) }
                    Log.d("COUNT", "WOW $page")

                    loadSearchData(movieApi, page, currQuery)
                }

            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        searchView = menu?.findItem(R.id.app_bar_search)?.actionView as SearchView
        searchView?.isIconified = false
        searchView?.queryHint = "Search..."

        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                currQuery = query ?: ""

                searchView?.clearFocus()

                loadSearchData(movieApi, 1, query)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })


        return true
    }

    fun loadSearchData(movieApi: MovieApi, page: Int, query: String?) {
        search_loading.visibility = View.VISIBLE
        movieApi
                .searchMovie("d4d1ae89afd12d33b74402bc930392f1", query, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            var movies = it.movies as ArrayList<Movie>

                            Log.d("DATA", "DATA SEARCH: $movies")

                            if (page == 1) {
                                movieSearchAdapter = MovieSearchResultAdapter(movies)
                                movie_search_result.adapter = movieSearchAdapter
                            } else {
                                movieSearchAdapter.refreshAdapter(movies)
                            }
                            totalPage = it.total_pages
                        },
                        {
                            Log.e("ERROR", "Error occurred: ${it.message}")
                        },
                        {
                            search_loading.visibility = View.GONE
                        }
                )
    }
}
