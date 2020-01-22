package com.bagus.moviecataloguev4.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bagus.moviecataloguev4.R
import com.bagus.moviecataloguev4.model.movie.MovieGenres
import com.bagus.moviecataloguev4.ui.movies.category.CardViewMovieCategorysAdapter



class MovieFragment : Fragment() {
    var mProgressBar: ProgressBar? = null
    private var rvMovies: RecyclerView? = null
    lateinit var movieViewMovdel: MovieViewModel
    var swipeLayout: SwipeRefreshLayout? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movie, container, false)
        super.onViewCreated(view, savedInstanceState)
        getActivity()?.setTitle(R.string.title_movie);
        mProgressBar = view.findViewById(R.id.progressBar)
        rvMovies = view.findViewById(R.id.rv_movies)
        rvMovies?.setHasFixedSize(true)
        rvMovies?.layoutManager = LinearLayoutManager(this.activity)

        movieViewMovdel = ViewModelProvider(this).get(MovieViewModel::class.java)
        if (savedInstanceState == null) {
            // proses ambil data
            movieViewMovdel.getGenre()
        } else {
            val list = savedInstanceState.getParcelableArrayList<MovieGenres>("EXTRA_STATE")
            if (list != null) {
                rvMovies!!.adapter =
                    CardViewMovieCategorysAdapter(
                        list
                    )

            }
        }
        showRecyclerCardView()
        swipeLayout = view.findViewById(R.id.swipe_container) as? SwipeRefreshLayout
        swipeLayout?.setOnRefreshListener {
            movieViewMovdel.getGenre()
            swipeLayout?.setRefreshing(false);
        }



        return view

    }





    fun showRecyclerCardView() {
        if(::movieViewMovdel.isInitialized){
            showLoading(true)
            movieViewMovdel.getCategorys().observe(this, Observer { items ->
                if (items != null) {
                    rvMovies!!.adapter =
                        CardViewMovieCategorysAdapter(
                            items
                        )
                }
            })
        }


    }

    private fun showLoading(state: Boolean) {
        if (state) {
            mProgressBar?.visibility  = View.VISIBLE
        } else {
            mProgressBar?.visibility = View.GONE
        }
    }





}